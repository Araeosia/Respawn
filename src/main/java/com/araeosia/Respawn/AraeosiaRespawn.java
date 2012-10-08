package com.araeosia.Respawn;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class AraeosiaRespawn extends JavaPlugin {

	static final Logger log = Logger.getLogger("Minecraft");
	public boolean debug=false;
	public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
	
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		if (!setupEconomy() ) {
            log.info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
		loadConfiguration();
		this.debug("[AraeosiaRespawn] Debug mode enabled!");
		log.info("AraeosiaRespawn Enabled!");
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
	}
	
	/**
	 * 
	 */
	@Override
	public void onDisable(){
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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
	/**
	 * 
	 */
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("SPAWN") || cmd.getName().equalsIgnoreCase("UNSTUCK")){
			// Teleport them to spawn after a 10 second wait without removing money.
		}
		return false;	
	}
	
	/**
	 * 
	 */
	public void loadConfiguration(){
		boolean configIsCurrentVersion = getConfig().getDouble("AraeosiaRespawn.technical.version")==0.1;
		if(!configIsCurrentVersion){
			getConfig().set("AraeosiaRespawn.technical.debug", false);
			getConfig().set("AraeosiaRespawn.technical.version", 0.1);
			for(World world : getServer().getWorlds()){
				getConfig().set("AraeosiaRespawn.spawnLocations."+world.getName(), new ArrayList<String>());
			}
			saveConfig();
		}
		for(World world : getServer().getWorlds()){
			// Load each of the spawn points for the worlds into memory.
		}
		debug = getConfig().getBoolean("AraeosiaRespawn.technical.debug");
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void debug(String msg) {
		if(debug){
			log.info( "[AraeosiaRespawn]:" + msg);
		}	
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public String playerMsg(String msg){
		return ChatColor.GOLD + "[AraeosiaRespawn]: " + ChatColor.YELLOW + msg;
	}
}
