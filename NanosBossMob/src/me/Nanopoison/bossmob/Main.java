package me.Nanopoison.bossmob;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Nanopoison.bossmob.events.BlockPlace;
import me.Nanopoison.bossmob.events.EntityDeath;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EntityDeath(), this);
		pm.registerEvents(new BlockPlace(this), this);
		
	}
	
	@Override
	public void onDisable() {
		
		
		
	}
	
}
