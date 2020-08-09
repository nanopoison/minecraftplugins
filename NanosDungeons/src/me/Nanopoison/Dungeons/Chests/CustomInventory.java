package me.Nanopoison.Dungeons.Chests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomInventory {
	
	
	public enum CustomInventoryType {
		DungeonChest;
	}
	
	private final Inventory inventory;
	private final CustomInventoryType type;
	
	public CustomInventory(Inventory inventory, CustomInventoryType type) {
		this.inventory = inventory;
		this.type = type;
	}
	
	 public CustomInventory(int size, String name, CustomInventoryType type) {
	        this.inventory = Bukkit.createInventory(null, size, name);
	        this.type = type;
	 }
	
	 public Inventory GetInventory() {
		 return inventory;
	 }
	 
	 public CustomInventoryType GetType() {
		 return type;
	 }
	 
	 public int GetLength() {
		 int length = 0;
		 
		 for (ItemStack item : inventory.getContents()) {
			 if (item != null) {
				 length += 1;
			 }
		 }
		 
		 return length;
	 }
}
