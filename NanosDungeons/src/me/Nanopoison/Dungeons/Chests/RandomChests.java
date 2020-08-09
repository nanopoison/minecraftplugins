package me.Nanopoison.Dungeons.Chests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import me.Nanopoison.Dungeons.Main;
import me.Nanopoison.Dungeons.Chests.CustomInventory.CustomInventoryType;
import net.md_5.bungee.api.ChatColor;

public class RandomChests implements Listener {

	private Main plugin;
	private World world;
	
	public List<Location> dungeonChestLocations = new ArrayList<Location>();
	
	public ItemStack[] possibleDrops = {													// TODO add more loot to the table
			new ItemStack(Material.WOODEN_AXE),
			new ItemStack(Material.WOODEN_AXE)
	};
	public Map<ItemStack, Integer> lootTable = new HashMap<ItemStack, Integer>();			// TODO save the chest locations in between reloads, so people don't need to
																							// manually reset them ever reload
	public RandomChests(Main plugin, World world) {
		
		this.world = world;
		this.plugin = plugin;
		
		SetPossibleDrops(possibleDrops);
	}
	
	// COMMANDS
	
	// FUNCTIONS
	
	public void SetDungeonChest(Block chest) {
		if (!(dungeonChestLocations.contains(chest.getLocation())))
			dungeonChestLocations.add(chest.getLocation());
		
		CustomInventory dungeonChestInv = new CustomInventory(9, ChatColor.YELLOW + "" + ChatColor.BOLD + "Dungeon Chest", CustomInventoryType.DungeonChest);
		
		SetPossibleDrops(possibleDrops);
		dungeonChestInv.GetInventory().setItem(new Random().nextInt(8), possibleDrops[0]);
		dungeonChestInv.GetInventory().setItem(new Random().nextInt(8), possibleDrops[1]);
		
		chest.setMetadata("dungeonChest", new FixedMetadataValue(plugin, dungeonChestInv));
		chest.setType(Material.CHEST);
	}
	
	public void SetPossibleDrops(ItemStack[] possibleTable) {							// TODO add the drops
		
		ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 5);
		
		ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
		diamondSword.setItemMeta(RandomizeWeaponEnchants(diamondSword));
		
		possibleTable[0] = diamondSword;
		possibleTable[1] = gapple;
		
	}
	
	public ItemMeta RandomizeWeaponEnchants(ItemStack item) {
		
		ItemMeta meta = item.getItemMeta();
		
		if (new Random().nextInt(2)%2 == 0)
			meta.addEnchant(Enchantment.DAMAGE_ALL, new Random().nextInt(4) + 1, true);
		if (new Random().nextInt(2)%2 == 0)
			meta.addEnchant(Enchantment.DURABILITY, new Random().nextInt(2) + 1, true);
		if (new Random().nextInt(5)%5 == 0)
			meta.addEnchant(Enchantment.FIRE_ASPECT, new Random().nextInt(1) + 1, true);
		if (new Random().nextInt(3)%3 == 0)
			meta.addEnchant(Enchantment.KNOCKBACK, new Random().nextInt(2) + 1, true);
		if (new Random().nextInt(10)%10 == 0)
			meta.addEnchant(Enchantment.MENDING, 1, true);
		
		return meta;
	}
	
	public ItemMeta RandomizeArmorEnchants(ItemStack item) {
		
		ItemMeta meta = item.getItemMeta();
		
		if (new Random().nextInt(2)%2 == 0)
			meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, new Random().nextInt(4) + 1, true);
		if (new Random().nextInt(2)%2 == 0)
			meta.addEnchant(Enchantment.DURABILITY, new Random().nextInt(2) + 1, true);
		if (new Random().nextInt(3)%3 == 0)
			meta.addEnchant(Enchantment.KNOCKBACK, new Random().nextInt(2) + 1, true);
		if (new Random().nextInt(10)%10 == 0)
			meta.addEnchant(Enchantment.MENDING, 1, true);
		
		return meta;
	}
	
	// EVENTS
	
	@EventHandler
	public void onChestRightClick(PlayerInteractEvent event) {
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
			return;
		if (!(event.getClickedBlock().hasMetadata("dungeonChest")))
			return;
		
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		List<MetadataValue> dungeonChestInvMetadata = event.getClickedBlock().getMetadata("dungeonChest");
		CustomInventory inv = new CustomInventory(9, "l", CustomInventoryType.DungeonChest);
		for (MetadataValue invMeta : dungeonChestInvMetadata) {
			inv = (CustomInventory) invMeta.value();
		}
		player.openInventory(inv.GetInventory());
		player.setMetadata("dungeonChestInv", new FixedMetadataValue(plugin, inv));
		player.setMetadata("dungeonChestBlock", new FixedMetadataValue(plugin, event.getClickedBlock().getState()));
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		if (!(player.hasMetadata("dungeonChestInv")))
			return;
		if (!(player.hasMetadata("dungeonChestBlock")))
			return;
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
			
		CustomInventory inv = (CustomInventory) player.getMetadata("dungeonChestInv").get(0).value();
		BlockState chest = (BlockState) player.getMetadata("dungeonChestBlock").get(0).value();
		
		if (inv.GetLength() == 1) {													
			event.setCancelled(true);																		
			player.getInventory().addItem(event.getCurrentItem());
			player.closeInventory();
			chest.getBlock().setType(Material.AIR);
			player.removeMetadata("dungeonChestInv", plugin);
			player.removeMetadata("dungeonChestBlock", plugin);
			//dungeonChestLocations.remove(chest.getLocation());
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (!(event.getBlock().getType() == Material.CHEST))
			return;
		if (!(event.getBlock().hasMetadata("dungeonChest")))
			return;
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			dungeonChestLocations.remove(event.getBlock().getLocation());
			event.setDropItems(false);
			return;
		}
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "You cannot break a Dungeon Chest block!");
	}
}
