package com.araeosia.Respawn;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EventListener implements Listener {

	public HashMap<String, Location> deaths = new HashMap<String, Location>();
	public AraeosiaRespawn plugin;

	/**
	 *
	 * @param plugin
	 */
	public EventListener(AraeosiaRespawn plugin) {
		this.plugin = plugin;
	}

	/**
	 *
	 * @param event
	 */
	@EventHandler
	public void Death(final EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			deaths.put(player.getName(), player.getLocation());
		}
	}

	public Location getRespawnLocation(Player player) {
		Location deathLocation = deaths.get(player.getName());
		List possibleLocations = plugin.getConfig().getList("AraeosiaRespawn.spawnLocations"); // Gets a list of all the worlds in the config
		for( : possibleLocations){
			
		}
		deaths.remove(player.getName()); // Clear out that player's location.
	}

	private void saveLocation(Location place, Player player) {
		plugin.getConfig().set("AraeosiaRespawn.locations." + player.getDisplayName() + ".world", place.getWorld().getName());
		plugin.getConfig().set("AraeosiaRespawn.locations." + player.getDisplayName() + ".X", place.getX());
		plugin.getConfig().set("AraeosiaRespawn.locations." + player.getDisplayName() + ".Y", place.getY());
		plugin.getConfig().set("AraeosiaRespawn.locations." + player.getDisplayName() + ".Z", place.getZ());
		plugin.saveConfig();
	}
	private double subtractMoney(Player player){
		Random rand = new Random();
		int lostPercent = rand.nextInt(101);
		double lost = plugin.econ.getBalance(player.getName())*(lostPercent/100);
		plugin.econ.withdrawPlayer(player.getName(), lost);
		return lost;
	}
}
