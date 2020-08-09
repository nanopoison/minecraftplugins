package me.Nanopoison.Factory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import me.Nanopoison.Factory.Materials.Wire;
import me.Nanopoison.Factory.Materials.Wire.WireType;

public class Events implements Listener {
	
	private Main plugin;
	
	public Events(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void OnBlockPlace(BlockPlaceEvent event) {
		if (!(event.getItemInHand().getType() == Material.REDSTONE))
			return;
		if (!(event.getItemInHand().getItemMeta().hasEnchant(Enchantment.DURABILITY)))
			return;
		
		Player player = (Player) event.getPlayer();
		player.sendMessage("You have placed a wire!");
		if (!(player.hasMetadata("playerWires"))) {
			player.setMetadata("playerWires", new FixedMetadataValue(plugin, new ArrayList<Wire>()));
		}
		
		List<Wire> playerWires = (List<Wire>) player.getMetadata("playerWires").get(0).value();
		
		Wire wire = new Wire(event.getBlock(), WireType.Basic);
		playerWires.add(wire);
		
		player.sendMessage("Wire placed at: " + wire.GetLocation());
		
		for (Wire wired : playerWires) {
			player.sendMessage("Wire at: " + wired.GetLocation());
		}
		
		player.setMetadata("playerWires", new FixedMetadataValue(plugin, playerWires));
		
		Block block = (Block) event.getBlock();
		block.setMetadata("isWire",new FixedMetadataValue(plugin, wire));
	}
	
}
