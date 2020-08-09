package me.Nanopoison.Dungeons;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nanopoison.Dungeons.Chests.RandomChests;

public class Main extends JavaPlugin {

	private RandomChests randoChests;
	private World world = getServer().getWorld("DungeonWorld");
	private List<World> worlds = getServer().getWorlds();
	
	private DungeonCommands dungeonCommands;
	
	@Override
	public void onEnable() {
		
		randoChests = new RandomChests(this, world);
		dungeonCommands = new DungeonCommands(this, world, randoChests);
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(randoChests, this);
		
		this.getCommand("getRandomItem").setExecutor(dungeonCommands);
		this.getCommand("dungeons").setExecutor(dungeonCommands);
		this.getCommand("dungeons").setTabCompleter(new DungeonTab());
		
		System.out.println("Nano's Dungeons have been enabled...");
		
		Player justGauss = this.getServer().getPlayer("justGauss");
		justGauss.removeMetadata("dungeonChestInv", this);
		justGauss.removeMetadata("dungeonChestBlock", this);
		
	}
	
	@Override
	public void onDisable() {
		
		System.out.println("Nano's Dungeons have been disabled...");
	}
	
}
