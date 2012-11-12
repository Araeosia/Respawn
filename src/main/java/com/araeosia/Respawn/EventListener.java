package com.araeosia.Respawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventListener implements Listener {

	private AraeosiaRespawn plugin;

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
			if(plugin.spawnCommands.containsKey(player.getName())){
				plugin.getServer().getScheduler().cancelTask(plugin.spawnCommands.get(player.getName()));
			}
			plugin.deaths.put(player.getName(), plugin.getSpawnLocation(player.getLocation()));
		}
	}
	@EventHandler
	public void Respawn(final PlayerRespawnEvent event){
		Player p = event.getPlayer();
		if(plugin.deaths.containsKey(p.getName())){
			RespawnShack r = plugin.deaths.get(p.getName());
			p.teleport(r.getLocation());
			plugin.deaths.remove(p.getName());
			double lost = plugin.subtractMoney(p);
			p.sendMessage("You died and were respawned at "+r.getName()+".");
			p.sendMessage("You lost $"+((int) lost)+", leaving you with $"+((int)plugin.econ.getBalance(p.getName())));
		}else{
			// Uh.....
		}
	}
}