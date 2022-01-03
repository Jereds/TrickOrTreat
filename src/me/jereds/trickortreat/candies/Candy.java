package me.jereds.trickortreat.candies;

import static org.bukkit.Material.AMETHYST_SHARD;
import static org.bukkit.Material.BRICK;
import static org.bukkit.Material.FERMENTED_SPIDER_EYE;
import static org.bukkit.Material.HEART_OF_THE_SEA;
import static org.bukkit.Material.HONEYCOMB;
import static org.bukkit.Material.MAGMA_CREAM;
import static org.bukkit.Material.NETHERITE_SCRAP;
import static org.bukkit.Material.PHANTOM_MEMBRANE;
import static org.bukkit.Material.POPPED_CHORUS_FRUIT;
import static org.bukkit.Material.SCUTE;
import static org.bukkit.Material.SUGAR;
import static org.bukkit.Material.TROPICAL_FISH;
import static org.bukkit.potion.PotionEffectType.DAMAGE_RESISTANCE;
import static org.bukkit.potion.PotionEffectType.DOLPHINS_GRACE;
import static org.bukkit.potion.PotionEffectType.FAST_DIGGING;
import static org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE;
import static org.bukkit.potion.PotionEffectType.GLOWING;
import static org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE;
import static org.bukkit.potion.PotionEffectType.INVISIBILITY;
import static org.bukkit.potion.PotionEffectType.JUMP;
import static org.bukkit.potion.PotionEffectType.LEVITATION;
import static org.bukkit.potion.PotionEffectType.REGENERATION;
import static org.bukkit.potion.PotionEffectType.SLOW_FALLING;
import static org.bukkit.potion.PotionEffectType.SPEED;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import me.jereds.trickortreat.TrickOrTreat;
import me.jereds.trickortreat.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public enum Candy {	
	PIXIE_DUST(SUGAR, "&f&lPixie Dust", new PotionEffect(SPEED, 60, 2), "&5Makes you hyper"),
	JAWBREAKER(HEART_OF_THE_SEA, "&9&lJawbreaker", new PotionEffect(DAMAGE_RESISTANCE, 100, 2), "&5Tough to crack"),
	TAFFY(BRICK, "&c&lTaffy", new PotionEffect(JUMP, 100, 2), "&5Tastes like rubber"),
	BUBBLE_GUM(POPPED_CHORUS_FRUIT, "&d&lBubble Gum", new PotionEffect(LEVITATION, 100, 0), "&5Blows record-breaking bubbles"),
	CRYSTAL_CANDY(AMETHYST_SHARD, "&d&lCrystal Candy", new PotionEffect(GLOWING, 200, 0), "&5Very shiny… is this edible?"),
	MARSHMALLOW(PHANTOM_MEMBRANE, "&f&lMarshmallow", new PotionEffect(SLOW_FALLING, 140, 0), "&5Light and fluffy"),
	WARHEAD(SCUTE, "&a&lWarhead", new PotionEffect(INCREASE_DAMAGE, 100, 1), "&5Packs a punch"),
	HONEY_DROP(HONEYCOMB, "&6&lHoney Drop", new PotionEffect(FAST_DIGGING, 100, 1), "&5You feel productive"),
	SWEDISH_FISH(TROPICAL_FISH, "&6&lSwedish Fish", new PotionEffect(DOLPHINS_GRACE, 40, 0), "&5Can these things swim?"),
	FIREBALL_CANDY(MAGMA_CREAM, "&4&lFireball Candy", new PotionEffect(FIRE_RESISTANCE, 200, 0), "&5You'll get used to the spice"),
	CHOCOLATE(NETHERITE_SCRAP, "&5&lChocolate", new PotionEffect(REGENERATION, 100, 1), "&5Rich and hearty"),
	GUMMY_EYEBALL(FERMENTED_SPIDER_EYE, "&c&lGummy Eyeball", new PotionEffect(INVISIBILITY, 200, 0), "&5Now you see me, now you don't");
	
	public Material mat;
	public String[] lore;
	public String display;
	public PotionEffect effect;
	private Candy(Material mat, String display, PotionEffect effect, String... lore) {
		this.mat = mat;
		this.lore = lore;
		this.display = ChatColor.translateAlternateColorCodes('&', display);
		this.effect = effect;
	}
	
	public String getId() {
		return ChatColor.stripColor(display).toLowerCase().replace(' ', '_');
	}
	
	public NamespacedKey getKey() {
		return new NamespacedKey(TrickOrTreat.getInst(), getId());
	}
	
	public ItemStack getCandy(int amount) {
		var item = ItemUtil.makeItem(mat, amount, display, lore);
		item = ItemUtil.makeCosmetic(item);
		var meta = item.getItemMeta();
		var container = meta.getPersistentDataContainer();
		container.set(getKey(), PersistentDataType.STRING, "candy");
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}
	
	public boolean isCandy(ItemStack item) {
		return item != null && 
				item.getType() == mat && 
				item.getItemMeta().getPersistentDataContainer().has(getKey(), PersistentDataType.STRING);
	}
	
	public void apply(Player player) {
		player.addPotionEffect(effect);
	}
	
	public static Candy getFromItem(ItemStack item) {
		Candy candy = null;
		if(item == null || item.getType() == Material.AIR)
			return null;
		for(Candy candyVal : values()) {
			if(item.getType() == candyVal.mat) {
				if(item.getItemMeta().getPersistentDataContainer().has(ItemUtil.getKey(), PersistentDataType.STRING)) {
					candy = candyVal;
					break;
				}
			}
		}
		return candy;
	}
}