package me.Nanopoison.NanosBosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nanopoison.UndeadKnight.UndeadKnightBoss;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public Inventory bossSpawnInv;
	private UndeadKnightBoss UndeadKnightBossSource;
	
	@Override
	public void onEnable() {
		UndeadKnightBossSource = new UndeadKnightBoss(this);
		
		PluginManager pm = this.getServer().getPluginManager();
		
		
		pm.registerEvents(UndeadKnightBossSource, this);
		pm.registerEvents(this, this);
		
		createBossSpawnInv();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("spawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Thanks console! This might be changed later tho :P");
				return true;
			}
			Player player = (Player) sender;
			
			if (player.isOp()) {
				if (args.length == 0) {
					player.openInventory(bossSpawnInv);
					return true;
				}
				
				if (args.length == 3) {
					UndeadKnightBossSource.CreateSkeleton(new Location(player.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("UndeadKnight")) {
					UndeadKnightBossSource.CreateSkeleton(player.getLocation());
					return true;
				}
			} else {
				player.sendMessage("You do not have permission to send this command.");
				return true;
			}
		}
		
		return false;
	}
	
	public void createBossSpawnInv() {
		bossSpawnInv = Bukkit.createInventory(null, 9,"Spawn Boss");
		
		ItemStack item = new ItemStack(Material.SKELETON_SPAWN_EGG);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Undead Knight");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click to spawn the 'Undead Knight' boss");
		meta.setLore(lore);
		item.setItemMeta(meta);
		bossSpawnInv.setItem(0, item);
		
		}
	
	// bottle function
	
	public ItemStack createEXPBottle(Integer XP, String author) {
		
		ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta expBottleMeta = expBottle.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		lore.add(XP + " XP");					// lore.add(ChatColor.GOLD + "" + XP + " XP");
		lore.add(ChatColor.GOLD + "Bottled by: " + author);
		
		expBottleMeta.setLore(lore);
		expBottle.setItemMeta(expBottleMeta);
		
		System.out.println("Bottle created!");
		
		return expBottle;
	}
	
	// events
	
	@EventHandler
	public void expBottleUsed(ProjectileLaunchEvent event) {
		if (!(event.getEntity() instanceof ThrownExpBottle))
			return;
		ThrownExpBottle bottle = (ThrownExpBottle) event.getEntity();
		if(!(bottle.getItem().getItemMeta().hasLore()))
			return;
		if (!(bottle.getShooter() instanceof Player))
			return;
		Integer xp = Integer.parseInt(bottle.getItem().getItemMeta().getLore().get(0).replaceAll("[^0-9]",""));
		
		String[] ogString = bottle.getItem().getItemMeta().getLore().get(1).split(": ");
		String author = "";
		for (int i = 1; i < ogString.length; i++) {
			author += ogString[i];
		}
		
		Player player = (Player) bottle.getShooter();
		player.sendMessage(ChatColor.GOLD + "You received " + ChatColor.AQUA + "" + xp + " XP" + ChatColor.GOLD + " from " + ChatColor.GREEN + "" + author + ChatColor.GOLD + "!");
		player.giveExp(xp);
		player.playSound(player.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"), 1.0f, new Random().nextFloat()*0.5f + 0.5f);
		event.setCancelled(true);
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent event) {
		if (!event.getInventory().equals(bossSpawnInv))
			return;
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		
		if (event.getSlot() == 0) {
			UndeadKnightBossSource.CreateSkeleton(player.getLocation());
		}
	}
}
