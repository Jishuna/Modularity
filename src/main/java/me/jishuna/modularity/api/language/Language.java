package me.jishuna.modularity.api.language;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

public class Language {

	private final String key;
	private final Map<String, String> conversionMap = new HashMap<>();

	public Language(String key, Map<String, Object> replacementMap) {
		this.key = key;
		replacementMap.forEach((translationKey, value) -> this.conversionMap
				.put(translationKey, ChatColor.translateAlternateColorCodes('&', value.toString())).toString());
	}

	public String translate(String key) {
		return this.conversionMap.getOrDefault(key, "Missing Translation: " + key);
	}

	public String getKey() {
		return key;
	}

}