package me.Nanopoison.Dungeons;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class Dungeon {

	private World dungeonWorld;
	private List<Location> dungeonChestLocations;
	private List<Location> dungeonMobSpawnLocations;
	
	public Dungeon(World dungeonWorld, List<Location> dungeonChestLocations, List<Location> dungeonMobSpawnLocations) {
		this.dungeonWorld = dungeonWorld;
		this.dungeonChestLocations = dungeonChestLocations;
		this.dungeonMobSpawnLocations = dungeonMobSpawnLocations;
	}
	
	public World GetDungeonWorld() {
		return dungeonWorld;
	}
	
	public List<Location> GetDungeonChestLocations() {
		return dungeonChestLocations;
	}
	
	public Location GetDungeonChestLocation(int index) {
		return dungeonChestLocations.get(index);
	}
	
	public List<Location> GetDungeonMobSpawnLocations() {
		return dungeonMobSpawnLocations;
	}
	
	public Location GetDungeonMobSpawnLocation(int index) {
		return dungeonMobSpawnLocations.get(index);
	}
	
}
