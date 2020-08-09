package me.Nanopoison.bossmob.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;

import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.EntitySkeleton;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.EnumItemSlot;
import net.minecraft.server.v1_16_R1.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R1.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R1.PathfinderGoalRandomStrollLand;

public class UndeadKnight extends EntitySkeleton {

	public UndeadKnight(Location loc) {
		super(EntityTypes.SKELETON, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setCustomName(new ChatComponentText(ChatColor.BLACK + "" + ChatColor.BOLD + "Undead Knight"));
		this.setCustomNameVisible(true);
		
		this.lootTableSeed = 0;
		
		
		
		
		this.goalSelector.a(0, new PathfinderGoalAvoidTarget<EntityPlayer>(this, EntityPlayer.class, 15, 1.0D, 1.0D));
		this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
		this.goalSelector.a(2, new PathfinderGoalRandomStrollLand(this, 0.6D));
		this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
	}
	
}
