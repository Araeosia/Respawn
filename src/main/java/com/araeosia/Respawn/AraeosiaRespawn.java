package com.araeosia.Respawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class AraeosiaRespawn extends JavaPlugin {

	static final Logger log = Logger.getLogger("Minecraft");
	public boolean debug=false;
	public static Economy econ = null;
	public ArrayList<RespawnShack> respawnLocs;
	public HashMap<String, RespawnShack> deaths = new HashMap<>();
	public HashMap<String, Integer> spawnCommands = new HashMap<>();
	
	@Override
	public void onEnable(){
		if (!setupEconomy() ) {
            log.info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		loadConfiguration();
		this.debug("[AraeosiaRespawn] Debug mode enabled!");
		log.info("AraeosiaRespawn Enabled!");
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
	}
	
	@Override
	public void onDisable(){
		for(String s : deaths.keySet()){
			getConfig().set("AraeosiaRespawn.deaths."+s, deaths.get(s).getName());
		}
		log.info("[AraeosiaRespawn] Good night.");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("SPAWN") || cmd.getName().equalsIgnoreCase("UNSTUCK")){
			final Player p = (Player) sender;
			// Teleport them to spawn after a 10 second wait without removing money.
			getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable(){
				@Override
				public void run(){
					RespawnShack r = getSpawnLocation(p.getLocation());
					p.teleport(r.getLocation());
					p.sendMessage("You were teleported to "+r.getName());
				}
			}, 300L);
		}
		return false;	
	}
	
	public void loadConfiguration(){
		boolean configIsCurrentVersion = getConfig().getDouble("AraeosiaRespawn.technical.version")==0.1;
		if(!configIsCurrentVersion){
			getConfig().set("AraeosiaRespawn.technical.debug", false);
			getConfig().set("AraeosiaRespawn.technical.version", 0.1);
			saveConfig();
		}
		for(World world : getServer().getWorlds()){
			// Load each of the spawn points for the worlds into memory.
			if(getConfig().isConfigurationSection("AraeosiaRespawn.spawns."+world.getName())){
				for(String s : getConfig().getConfigurationSection("AraeosiaRespawn.spawns."+world.getName()).getKeys(false)){
					RespawnShack toAdd = new RespawnShack();
					toAdd.setName(getConfig().getString("AraeosiaRespawn.spawns."+world.getName()+"."+s+".name"));
					toAdd.setLocation(new Location(
							world,
							getConfig().getInt("AraeosiaRespawn.spawns."+world.getName()+"."+s+".x"),
							getConfig().getInt("AraeosiaRespawn.spawns."+world.getName()+"."+s+".y"),
							getConfig().getInt("AraeosiaRespawn.spawns."+world.getName()+"."+s+".z")
							));
					respawnLocs.add(toAdd);
				}
			}
		}
		if(getConfig().isConfigurationSection("AraeosiaRespawn.deaths")){
			for(String s : getConfig().getConfigurationSection("AraeosiaRespawn.deaths").getKeys(false)){
				deaths.put(s, getSpawnLocation(getConfig().getString("AraeosiaRespawn.deaths."+s)));
			}
		}
		debug = getConfig().getBoolean("AraeosiaRespawn.technical.debug");
	}
	
	public void debug(String msg) {
		if(debug){
			log.info( "[AraeosiaRespawn]:" + msg);
		}	
	}
	
	public String playerMsg(String msg){
		return ChatColor.GOLD + "[AraeosiaRespawn]: " + ChatColor.YELLOW + msg;
	}
	public RespawnShack getSpawnLocation(Location l) {
		HashMap<RespawnShack, Double> tempLocs = new HashMap<>();
		for(RespawnShack r : respawnLocs){
			tempLocs.put(r, r.getLocation().distance(l));
		}
		RespawnShack min = null;
		for(RespawnShack r : tempLocs.keySet()){
			if(min==null || tempLocs.get(min)<tempLocs.get(r)){
				min = r;
			}
		}
		return min;
	}
	public RespawnShack getSpawnLocation(String name){
		for(RespawnShack r : respawnLocs){
			if(r.getName().equalsIgnoreCase(name)){
				return r;
			}
		}
		return null;
	}
	
	public double subtractMoney(Player player){
		Random rand = new Random();
		int lostPercent = rand.nextInt(101);
		double lost = econ.getBalance(player.getName())*(lostPercent/100);
		econ.withdrawPlayer(player.getName(), lost);
		return lost;
	}
}