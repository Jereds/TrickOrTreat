package me.jereds.trickortreat.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

//example for cortex suggestion (ignore)
public class PlayerLib {
	
	private final File file; //file object per player
	private final YamlConfiguration config; //config object per player
	
	public PlayerLib(Player player) {
		file = new File(Example.getInst().getDataFolder(), player.getUniqueId() + ".yml"); //create a new file object at the given path. Don't for the ".yml"
		try {
			file.createNewFile(); //if the file doesn't exist this will create it, if it does already exist this will do nothing. 
								  //(returns true/false based on existence)
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(file);
	}

	public File getFile() {
		return file;
	}
	
	public YamlConfiguration getConfig() {
		return config;
	}
	
	//sets a value in the config and then saves the config.
	public <T> void set(String path, T value) {
		config.set(path, value);
		saveConfig();
	}
	
	//saves the config...
	public void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Example extends JavaPlugin implements Listener {
	
	//this is to get an instance of the main class outside of the main class, so we can use getDataFolder() in our PlayerLib class.
	private static Example plugin;
	public static Example getInst() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	//example of using the PlayerLib, death counter
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		var player = event.getEntity();
		var lib = new PlayerLib(player);
		lib.set("deaths", lib.getConfig().getInt("deaths") + 1);
	}
}