package com.araeosia.Respawn;

import org.bukkit.Location;

public class RespawnShack {
	private Location location;
	private String name;
	
	public void setLocation(Location location){
		this.location = location;
	}
	public Location getLocation(){
		return location;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}