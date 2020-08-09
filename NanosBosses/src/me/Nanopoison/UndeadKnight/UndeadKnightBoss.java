package me.Nanopoison.UndeadKnight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.Nanopoison.NanosBosses.Main;
import net.md_5.bungee.api.ChatColor;

public class UndeadKnightBoss implements Listener {

	private ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
	private ItemStack diamondChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
	private ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
	private ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
	private ItemMeta diamondSMeta = diamondSword.getItemMeta();
	private ItemMeta diamondCMeta = diamondChest.getItemMeta();
	private ItemMeta diamondLMeta = diamondLeggings.getItemMeta();
	private ItemMeta diamondBMeta = diamondBoots.getItemMeta();
	
	private ItemStack dragonHelmet = new ItemStack(Material.LEATHER_HELMET);
	private LeatherArmorMeta dragonHMeta = (LeatherArmorMeta) dragonHelmet.getItemMeta();
	
	private ItemStack xpbottle825;
	
	/*
	ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
	BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
	*/
	
	private Main plugin;
	public UndeadKnightBoss(Main plugin) {
		this.plugin = plugin;
		
		// DIAMOND STUFF
		
		diamondSMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		diamondSMeta.addEnchant(Enchantment.DURABILITY, 3, true);
		diamondSMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		diamondCMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
		diamondLMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
		diamondBMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
		
		diamondSMeta.setDisplayName(ChatColor.GREEN + "Sword of the Knight");
		
		diamondSword.setItemMeta(diamondSMeta);
		diamondChest.setItemMeta(diamondCMeta);
		diamondLeggings.setItemMeta(diamondLMeta);
		diamondBoots.setItemMeta(diamondBMeta);
		
		// DRAGON STUFF
		
		AttributeModifier dragonHModifier2 = new AttributeModifier(UUID.randomUUID(), "generic.armortoughness", 3, Operation.ADD_NUMBER, EquipmentSlot.HEAD);
		AttributeModifier dragonHModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 5, Operation.ADD_NUMBER, EquipmentSlot.HEAD);
		
		dragonHMeta.setDisplayName(ChatColor.RED + "" + "Dragonscale Helmet");
		dragonHMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, dragonHModifier);
		dragonHMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, dragonHModifier2);
		dragonHMeta.setColor(Color.RED);
		dragonHMeta.setUnbreakable(true);
		dragonHMeta.setLore(new ArrayList<String>());
		
		dragonHelmet.setItemMeta(dragonHMeta);
		
		// EXP Bottles
		
		xpbottle825 = plugin.createEXPBottle(825, "Undead Knight");
	}
	
	@EventHandler
	public void BlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.SPONGE) {
			
			event.setCancelled(true);
			event.getBlock().setType(Material.AIR);
			
			CreateSkeleton(event.getBlock().getLocation());
		}
	}
	
	@EventHandler
	public void EntityHit(EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof Skeleton && event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			
			if (event.getDamager().hasMetadata("BossArrow")) {
				event.setCancelled(true);
				arrow.setBounce(true);
			} else if (arrow.getShooter() instanceof Player) {
				Player player = (Player) arrow.getShooter();
				if (event.getEntity().hasMetadata("UndeadKnight")) {
					Random r = new Random();
					if ((r.nextInt()%5) == 0) {								// ARROWSTORM!!!
						List<Arrow> arrowStorm = new ArrayList<Arrow>();
						for (int i = -4; i < 4; i++) {
							for (int j = -4; j < 4; j++) {
								Arrow arrow2 = player.getWorld().spawn(player.getLocation().add(i, 15, j), Arrow.class);
								arrow2.setMetadata("BossArrow", new FixedMetadataValue(plugin, "bossarrow"));
								arrowStorm.add(arrow2);
							}
						}
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Arrows start raining...");
						player.playSound(player.getLocation(), Sound.valueOf("ENTITY_ARROW_SHOOT"), 10, 10);
						// insert Arrow storm here!!!
					}
					return;
				}
			}
		}
		
		if (event.getEntity() instanceof Skeleton && event.getDamager() instanceof Player) {
			if (event.getEntity().hasMetadata("UndeadKnight")) {
				Random r = new Random();
				if ((r.nextInt()%5) == 0) {					//BLOCKS YOUR ATTACKS
					event.setCancelled(true);
					Player player = (Player) event.getDamager();
					player.playSound(player.getLocation(), Sound.valueOf("BLOCK_ANVIL_LAND"), 10, 10);
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Your attack was blocked!");
				} else if ((r.nextInt()%5) == 4) {								// ARROWSTORM!!!
					List<Arrow> arrowStorm = new ArrayList<Arrow>();
					Player player = (Player) event.getDamager();
					for (int i = -4; i < 4; i++) {
						for (int j = -4; j < 4; j++) {
							Arrow arrow = player.getWorld().spawn(player.getLocation().add(i, 15, j), Arrow.class);
							arrow.setMetadata("BossArrow", new FixedMetadataValue(plugin, "bossarrow"));
							arrowStorm.add(arrow);
						}
					}
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Arrows start raining...");
					player.playSound(player.getLocation(), Sound.valueOf("ENTITY_ARROW_SHOOT"), 10, 10);
					// insert Arrow storm here!!!
				}
				return;
			}
		}
		
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Skeleton) {
			if (event.getDamager().hasMetadata("UndeadKnight")) {
				
				Player p = (Player) event.getEntity();
				if (p.isBlocking()) {
					p.damage(event.getDamage());
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The Undead Knight reaches around your shield!");
				}
				
				Random r = new Random();
				if ((r.nextInt()%5) == 0) {							// INFLICTS SLOWNESS
					Player player = (Player) event.getEntity();
					PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 100, 1);
					player.addPotionEffect(effect);
					event.getEntity().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The Undead Knight stabs your knees...");
					
					player.playSound(player.getLocation(), Sound.valueOf("ITEM_TRIDENT_HIT"), 10, 10);
				} else if ((r.nextInt()%5) == 4) {						// SENDS A PLAYER FLYING
					Player player = (Player) event.getEntity();
					Integer speed = 25;
					
					Vector direction = event.getDamager().getLocation().getDirection();
					direction.normalize();
					direction.add(new Vector(0, 0.25, 0));
					
					player.setVelocity(direction.multiply(speed));
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The Undead Knight flings you away!");
					
					player.playSound(player.getLocation(), Sound.valueOf("ENTITY_FIREWORK_ROCKET_LAUNCH"), 10, 10);
				}
				return;
			}
		}
		
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
			if (event.getDamager().hasMetadata("BossArrow")) {
				System.out.println("hit");
				event.setCancelled(true);
				Player player = (Player) event.getEntity();
				player.damage(3);
			}
		}
	}
	
	@EventHandler
	public void BossKilled(EntityDeathEvent event) {
		
		if (event.getEntity() instanceof Skeleton) {
			if (event.getEntity().hasMetadata("UndeadKnight")) {
				event.getDrops().clear();
				List<ItemStack> loot;
				if (event.getEntity().getLastDamageCause().getEntity() instanceof Player) {
					Player player = (Player) event.getEntity().getLastDamageCause().getEntity();
					loot = getLootFromLootTable(player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS));
				} else {
					loot = getLootFromLootTable(0);
				}
				
				for (ItemStack item : loot) {
					event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
				}
				event.setDroppedExp(0);
			}
		}

	}
	
	public List<ItemStack> getLootFromLootTable(Integer bonus) {
		
		Integer id = new Random().nextInt(100) - (bonus * 5);
		List<ItemStack> loot = new ArrayList<ItemStack>();
		
		Map<ItemStack, Integer> lootTable = new HashMap<ItemStack, Integer>();
		
		lootTable.put(new ItemStack(Material.DIAMOND, 64), 100);
		lootTable.put(xpbottle825, 100);
		lootTable.put(new ItemStack(Material.EMERALD, 16), 75);
		lootTable.put(new ItemStack(Material.NETHERITE_INGOT, 8), 50);
		lootTable.put(diamondSword, 30);
		lootTable.put(dragonHelmet, 15);
		
		for(Map.Entry<ItemStack, Integer> e : lootTable.entrySet())
		{
		    if (id <= e.getValue()) {
		    	loot.add(e.getKey());
		    }
		}
		
		return loot;
	}
	
	public void CreateSkeleton(Location loc) {
		
		Skeleton skeleton = loc.getBlock().getWorld().spawn(loc.add(0.5, 0, 0.5), Skeleton.class);
		
		skeleton.setCustomName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Undead Knight");
		
		skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		skeleton.setHealth(100);
		skeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(skeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 2);
		skeleton.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(skeleton.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getBaseValue() * 5);
		skeleton.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(skeleton.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue() * 2);
		
		skeleton.getEquipment().setItemInMainHand(diamondSword);
		skeleton.getEquipment().setItem(EquipmentSlot.HEAD, dragonHelmet);
		skeleton.getEquipment().setItem(EquipmentSlot.CHEST, diamondChest);
		skeleton.getEquipment().setItem(EquipmentSlot.LEGS, diamondLeggings);
		skeleton.getEquipment().setItem(EquipmentSlot.FEET, diamondBoots);
	
		skeleton.setMetadata("UndeadKnight", new FixedMetadataValue(plugin, "undeadknight"));
		
	}
	
}
