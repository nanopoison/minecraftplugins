package me.Nanopoison.Dungeons.Mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.Nanopoison.Dungeons.Main;

public class DungeonMobs {

	private Main plugin;
	private World world;
	
	public List<Location> dungeonMobSpawnLocations = new ArrayList<Location>();
	
	public DungeonMobs(Main plugin, World world) {
		
		this.world = world;
		this.plugin = plugin;
		
	}
	
	public void SetDungeonMobSpawnLocation(Block spawnBlock) {
		
	}
	
}
