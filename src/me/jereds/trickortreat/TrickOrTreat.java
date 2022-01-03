package me.jereds.trickortreat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.jereds.trickortreat.commands.CandiesCommand;
import me.jereds.trickortreat.listeners.CandyListener;
import me.jereds.trickortreat.listeners.CosmeticPatchListener;

public class TrickOrTreat extends JavaPlugin {

	private static TrickOrTreat plugin;
	public static TrickOrTreat getInst() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;
		
		var man = Bukkit.getPluginManager();
		man.registerEvents(new CandyListener(), this);
		man.registerEvents(new CosmeticPatchListener(), this);
		
		getCommand("candies").setExecutor(new CandiesCommand());
	}
}
