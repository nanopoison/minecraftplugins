package me.Nanopoison.Factory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private Events events = new Events(this);
	private FactoryCommands fc = new FactoryCommands();
	
	@Override
	public void onEnable() {
		
		List<World> worlds = this.getServer().getWorlds();
		for (World world: worlds) {
			System.out.println("World: " + world.toString());
		}
		
		Bukkit.addRecipe(getWireRecipe());
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(events, this);
		
		this.getCommand("factory").setExecutor(fc);
		
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public ShapedRecipe getWireRecipe() {
		NamespacedKey key = new NamespacedKey(this, "wire");
		ItemStack item = new ItemStack(Material.REDSTONE);
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();

		meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Wire");
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		
		lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "A basic factory wire");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape("  G", " R ", "G  ");
		
		recipe.setIngredient('G', Material.GOLD_INGOT);
		recipe.setIngredient('R', Material.REDSTONE);
		
		return recipe;
	}
	
}
