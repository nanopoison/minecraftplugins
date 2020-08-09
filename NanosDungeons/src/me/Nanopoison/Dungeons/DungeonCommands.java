package me.Nanopoison.Dungeons;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nanopoison.Dungeons.Chests.RandomChests;
import net.md_5.bungee.api.ChatColor;

public class DungeonCommands implements CommandExecutor {

	private RandomChests randomChests;
	private World world;
	private Main plugin;
	
	public DungeonCommands(Main plugin, World world, RandomChests randomChests) {
		this.plugin = plugin;
		this.world = world;
		this.randomChests = randomChests;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("getRandomItem")) {
			if (!(sender instanceof Player)) {
				System.out.println("I can't believe you've done this");
				return true;
			}
			Player player = (Player) sender;
			player.getInventory().addItem(randomChests.possibleDrops[new Random().nextInt(2)]);
			return true;
		}
		if (label.equalsIgnoreCase("dungeons")) {
			if (!(sender instanceof Player)) {
				System.out.println("Console can't edit dungeons quite yet :)");
				return true;
			}
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("chest")) {
				if (args[1].equalsIgnoreCase("create")) {
					Location loc = player.getTargetBlockExact(5).getLocation();
					if (randomChests.dungeonChestLocations.contains(loc)) {
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There is already a Dungeon Chest here!");
						return true;
					} else {
						Block chest = loc.getBlock();
						randomChests.SetDungeonChest(chest);
					}
					return true;
					
				}

				if (args[1].equalsIgnoreCase("delete")) {
					if (args.length == 2) {
						Location loc = player.getTargetBlockExact(5).getLocation();
						if (randomChests.dungeonChestLocations.contains(loc)) {
							if (loc.getBlock().hasMetadata("dungeonChest")) {
								loc.getBlock().setType(Material.AIR);
								randomChests.dungeonChestLocations.remove(loc);
								return true;
							}
						}
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There is no Dungeon Chest there!");
						randomChests.dungeonChestLocations.remove(loc);
						return true;
					}
					if (args.length == 5) {
						Location loc = new Location(world, Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
						if (randomChests.dungeonChestLocations.contains(loc)) {
							if (loc.getBlock().hasMetadata("dungeonChest")) {
								loc.getBlock().setType(Material.AIR);
								randomChests.dungeonChestLocations.remove(loc);
								return true;
							}
							player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There is no Dungeon Chest there!");
							randomChests.dungeonChestLocations.remove(loc);
							return true;
						}
					}
				}
				
				if (args[1].equalsIgnoreCase("reset")) {
					if (args.length == 2) {
						Location loc = player.getTargetBlockExact(5).getLocation();
						if (randomChests.dungeonChestLocations.contains(loc)) {
							randomChests.SetDungeonChest(loc.getBlock());
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("all")) {
						for (Location loc : randomChests.dungeonChestLocations) {
							randomChests.SetDungeonChest(loc.getBlock());
						}
						return true;
					}
				}
				
				if (args[1].equalsIgnoreCase("get")) {
					for (Location loc: randomChests.dungeonChestLocations) {
						player.sendMessage("Chest at: " + loc);
					}
					if (args.length == 3) {
						player.sendMessage("Chest at: " +  randomChests.dungeonChestLocations.get(Integer.parseInt(args[2])));
					}
				}
				
			}
			return true;
		}
		
		return false;
	}
	
}
