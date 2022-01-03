package me.jereds.trickortreat.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.jereds.trickortreat.TrickOrTreat;

public class ItemUtil {
	
	private static NamespacedKey cosmetic;
	
	static {
		cosmetic =  new NamespacedKey(TrickOrTreat.getInst(), "trick-or-treat-no-interact");
	}
	
	public static NamespacedKey getKey() {
		return cosmetic;
	}
	
	public static ItemStack makeCosmetic(ItemStack item) {
		var meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(cosmetic, PersistentDataType.STRING, "cosmetic");
		item.setItemMeta(meta);
		return item;
	}
	
	public static boolean isValid(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static boolean isCosmetic(ItemStack item) {
		return isValid(item) && item.getItemMeta().getPersistentDataContainer().has(cosmetic, PersistentDataType.STRING);
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack makeItem(Material mat, int amount, String displayName, String... lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(StringUtil.toColor(displayName));
		meta.setLore(StringUtil.toColor(lore));
		item.setItemMeta(meta);
		return item;
	}
}