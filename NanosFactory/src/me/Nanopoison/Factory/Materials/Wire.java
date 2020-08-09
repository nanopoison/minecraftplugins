package me.Nanopoison.Factory.Materials;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Wire {
	
	public enum WireType {
		Basic
	}
	
	private Block block;
	private WireType type;
	private int power;
	private int powerCapacity;
	
	public Wire(Block block, WireType type) {
		this.block = block;
		this.type = type;
		
		if (type == WireType.Basic) {
			this.powerCapacity = 100;
		}
		
	}
	
	public Block GetBlock() {
		return block;
	}
	
	public WireType GetType() {
		return type;
	}
	
	public Location GetLocation() {
		return block.getLocation();
	}
	
	public int GetPower() {
		return power;
	}
	
	public int GetPowerCapacity() {
		return powerCapacity;
	}
	
}
