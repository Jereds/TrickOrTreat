package me.jereds.trickortreat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jereds.trickortreat.candies.Candy;

public class CandyListener implements Listener {

	@EventHandler
	public void onInteractWithCandy(PlayerInteractEvent event) {
		var item = event.getItem();
		var candy = Candy.getFromItem(item);
		if(candy == null)
			return;
		event.setCancelled(true);
		item.setAmount(item.getAmount() -1);
		candy.apply(event.getPlayer());
	}
}