package me.Nanopoison.Factory;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Nanopoison.Factory.Materials.Wire;

public class FactoryCommands implements CommandExecutor {

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("factory")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You are a console...");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (args[0].equalsIgnoreCase("get")) {
				if (args[1].equalsIgnoreCase("wires")) {
					if (player.hasMetadata("playerWires")) {
						List<Wire> playerWires = (List<Wire>) player.getMetadata("playerWires").get(0).value();
						for (Wire wire : playerWires) {
							player.sendMessage("Wire at: " + wire.GetLocation());
						}
					}
				}
			}
		}
		
		return false;
	}

}
