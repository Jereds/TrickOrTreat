package me.jereds.trickortreat.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.jereds.pluginprefix.api.GradientManager;
import net.md_5.bungee.api.ChatColor;

public class StringUtil {

	private static String prefix;
	static {
		prefix = ChatColor.BLACK + "[" + doPrefix() + ChatColor.BLACK + "] " + ChatColor.of("#c44800");
	}

	public static String getPrefix() {
		return prefix;
	}

	public static String doPrefix() {
		String prefix = "TrickOrTreat";
		String start = "#793D02";
		String end = "#f57a00";
		return GradientManager.toGradient(prefix, start, end);
	}

	public static String capitalizeFirstChar(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String toColor(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static List<String> toColor(String... str) {
		return Arrays.stream(str).map(StringUtil::toColor).collect(Collectors.toList());
	}
}
