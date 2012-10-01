package com.araeosia;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Respawn extends JavaPlugin implements Listener {

	static final Logger log = Logger.getLogger("Minecraft");
	public boolean debug=false;
	
	
	@Override
	public void onEnable(){
		loadConfiguration();
		this.debug("[AraeosiaRespawn] Debug mode enabled!");
	
	}

	@Override
	public void onDisable(){
		log.info("[AraeosiaRespawn] Good night.");
	}
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("WHO")){
			
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
		debug = getConfig().getBoolean("AraeosiaRespawn.technical.debug");
	}
	
	private void debug(String msg) {
		if(debug){
			log.info( "[AraeosiaWho]:" + msg);
		}	
	}
}
