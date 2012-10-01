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
	
	public Location getRespawnLocation(Player player){
		world = getConfig().get("AraeosiaRespawn.locations."+player.getName()+".world");
		X = getConfig().get("AraeosiaRespawn.locations."+player.getName()+".X");
		Y = getConfig().get("AraeosiaRespawn.locations."+player.getName()+".Y");
		Z = getConfig().get("AraeosiaRespawn.locations."+player.getName()+".Z");
		return Location(getServer().getWorld(world), X, Y, Z);
	}
	
	private void setRespawnLocation(World deathWorld, double X, double Y, double Z, Player player){
		switch(deathWorld.getName()) {
			case "Araeosia":
				for(){
					// Fetch the location from the player's death point to the currently checked respawn point, then push it into an array
				}
				// Fetch the minimum of above array, resolve to the respawn point. saveLocation to the respawn point.
				break;
			case "Araeosia_tutorial":
				saveLocation(Location(deathWorld, 55.0, 64.0, 128.0), player);
				break;
			case "Araeosia_instance":
				saveLocation(Location(deathWorld, 55.0, 64.0, 128.0), player);
				break;
		}
	}
	private void saveLocation(Location place, Player player){
		getConfig().set("AraeosiaRespawn.locations."+player.getName()+".world", place.getWorld().getName());
		getConfig().set("AraeosiaRespawn.locations."+player.getName()+".X", place.getX());
		getConfig().set("AraeosiaRespawn.locations."+player.getName()+".Y", place.getY());
		getConfig().set("AraeosiaRespawn.locations."+player.getName()+".Z", place.getZ());
		saveConfig();
	}

}
