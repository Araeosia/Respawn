package com.araeosia.Respawn;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Respawn extends JavaPlugin {

	static final Logger log = Logger.getLogger("Minecraft");
	public boolean debug=false;
	
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		loadConfiguration();
		this.debug("[AraeosiaRespawn] Debug mode enabled!");
		log.info("AraeosiaRespawn Enabled!");
		this.getServer().getPluginManager().registerEvents(new RespawnEventListener(this), this);
	}
	
	/**
	 * 
	 */
	@Override
	public void onDisable(){
		log.info("[AraeosiaRespawn] Good night.");
	}
	
	/**
	 * 
	 */
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("RESPAWN")){
			
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
			saveConfig();
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
