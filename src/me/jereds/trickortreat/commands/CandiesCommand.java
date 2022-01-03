package me.jereds.trickortreat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jereds.trickortreat.candies.Candy;
import net.md_5.bungee.api.ChatColor;

public class CandiesCommand implements CommandExecutor {

	// /candies <Player> <Candy> <Amount>
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("trickortreat.candy.give")) {
			sender.sendMessage(ChatColor.RED + "You lack permission to use this command!");
			return true;
		}
//length           1      2       3
//args             0      1       2
		// candy jereds <type> <amount>
		if(args.length < 4) {
			sender.sendMessage(ChatColor.RED + "Please try /candy give <player> <type>");
			return true;
		}
		
		var player = Bukkit.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage(ChatColor.RED + "Could not find player '" + ChatColor.WHITE + args[0] + "'");
			return true;
		}
		Candy candy;
		try {
			candy = Candy.valueOf(args[1].toUpperCase());
		} catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "Could not find type: " + args[1]);
			return true;
		}
		
		int amount = 0;
		try {
			amount = Integer.parseInt(args[2]);
		} catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "Invalid amount!");
			return true;
		}
		
		player.getInventory().addItem(candy.getCandy(amount));
		player.sendMessage("You receieved " + amount + " candy!");
		sender.sendMessage(ChatColor.GREEN + "Successfully gave " + player.getDisplayName() + " " + amount + " candy!");
		return true;
	}
}