package me.Nanopoison.bossmob.events;

import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.Nanopoison.bossmob.Main;
import me.Nanopoison.bossmob.mobs.UndeadKnight;
import net.minecraft.server.v1_16_R1.WorldServer;

public class BlockPlace implements Listener {

	private Main plugin;
	public BlockPlace(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		UndeadKnight roughKnight = new UndeadKnight(event.getPlayer().getLocation());
		WorldServer world = ((CraftWorld) event.getPlayer().getWorld()).getHandle();
		world.addEntity(roughKnight);
		
		event.setCancelled(true);
		
		
	}
}
