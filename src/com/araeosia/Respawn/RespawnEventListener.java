package com.araeosia.Respawn;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class RespawnEventListener implements Listener{

	public HashMap<String, Location> deaths = new HashMap<String, Location>();
	public Respawn plugin;
	
	/**
	 * 
	 * @param plugin
	 */
	public RespawnEventListener(Respawn plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * 
	 * @param event
	 */
	@EventHandler
	public void Death(final EntityDeathEvent event){
		if(event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			deaths.put(player.getName(), player.getLocation());
		}
	}

}
