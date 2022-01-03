package me.jereds.trickortreat.listeners;

import static org.bukkit.event.inventory.InventoryType.ANVIL;
import static org.bukkit.event.inventory.InventoryType.BEACON;
import static org.bukkit.event.inventory.InventoryType.BLAST_FURNACE;
import static org.bukkit.event.inventory.InventoryType.BREWING;
import static org.bukkit.event.inventory.InventoryType.CARTOGRAPHY;
import static org.bukkit.event.inventory.InventoryType.COMPOSTER;
import static org.bukkit.event.inventory.InventoryType.ENCHANTING;
import static org.bukkit.event.inventory.InventoryType.FURNACE;
import static org.bukkit.event.inventory.InventoryType.GRINDSTONE;
import static org.bukkit.event.inventory.InventoryType.HOPPER;
import static org.bukkit.event.inventory.InventoryType.LECTERN;
import static org.bukkit.event.inventory.InventoryType.LOOM;
import static org.bukkit.event.inventory.InventoryType.MERCHANT;
import static org.bukkit.event.inventory.InventoryType.SMITHING;
import static org.bukkit.event.inventory.InventoryType.SMOKER;
import static org.bukkit.event.inventory.InventoryType.STONECUTTER;
import static org.bukkit.event.inventory.InventoryType.WORKBENCH;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import me.jereds.trickortreat.util.ItemUtil;
import me.jereds.trickortreat.util.StringUtil;

public class CosmeticPatchListener implements Listener {

	private static final InventoryType[] types;
	static {
		types = new InventoryType[] {GRINDSTONE, BREWING, ANVIL, HOPPER, WORKBENCH, SMITHING, SMOKER, STONECUTTER, 
				MERCHANT, LOOM, LECTERN, ENCHANTING, COMPOSTER, CARTOGRAPHY, FURNACE, BLAST_FURNACE, BEACON};
	}
	
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        if(event.getWhoClicked().hasPermission("ntx.admin")) return;
        
        if(!List.of(types).contains(event.getView().getTopInventory().getType()))
        	return;
        
        Player player = (Player) event.getWhoClicked();
        var item = event.getClick() == ClickType.NUMBER_KEY ?
        		event.getView().getBottomInventory().getItem(event.getHotbarButton()) : event.getCurrentItem();
        
        if(event.getView().getTopInventory().getType() == InventoryType.CRAFTING) {
        	if(event.getSlot() >= 0 && event.getSlot() <= 4) {
        		if(ItemUtil.isCosmetic(item)) {
        			event.setCancelled(true);
        			return;
        		}
        	}
        }

        if(ItemUtil.isCosmetic(item)) {
        	event.setCancelled(true);
        	player.sendMessage(StringUtil.getPrefix() + "This item is not allowed in this inventory type.");
        	return;
        }
        
//        //if the click is hotkey, normally the getCurrentItem will return null, because they're technically clicking an empty slot
//        if (event.getClick() == ClickType.NUMBER_KEY) {
//            if(ItemUtil.isCosmetic(item)) {
//            	player.sendMessage(StringUtil.getPrefix() + "This item is not allowed in this inventory type.");
//            }
//        }
    }
    
    @EventHandler
    public void onEntityPickup(EntityPickupItemEvent event) {
    	if(ItemUtil.isCosmetic(event.getItem().getItemStack()) && event.getEntityType() != EntityType.PLAYER)
    		event.setCancelled(true);
    }
    
    @EventHandler
    public void onHopper(InventoryPickupItemEvent event) {
    	if(ItemUtil.isCosmetic(event.getItem().getItemStack()))
    		event.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    	if(ItemUtil.isCosmetic(event.getItemInHand()))
    		event.setCancelled(true);
    }
    
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
    	if(ItemUtil.isCosmetic(event.getItem()))
    		event.setCancelled(true);
    }
}