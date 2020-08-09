package me.Nanopoison.bossmob.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeath implements Listener {

	private ItemStack[] lootTable = {
			new ItemStack(Material.DIAMOND, 10),
			new ItemStack(Material.EMERALD, 10),
			new ItemStack(Material.ELYTRA, 1)
	};
	
	
	@EventHandler
	public void onDeathEntity(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof Skeleton))
			return;
		if (event.getEntity().getCustomName() == null)
			return;
		if (!event.getEntity().getCustomName().contains("Undead Knight"))
			return;
		
		Random r = new Random();
		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), lootTable[r.nextInt(lootTable.length + 0) - 0]);
		
		System.out.println("Entity died by: " + event.getEventName());
		
	}
	
}
