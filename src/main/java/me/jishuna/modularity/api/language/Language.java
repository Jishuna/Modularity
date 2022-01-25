package me.jishuna.modularity.api.language;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Language {

	private final String key;
	private final Map<String, String> conversionMap = new HashMap<>();

	public Language(String key) {
		this.key = key;
	}

	public void loadFromYaml(YamlConfiguration yaml) {
		Map<String, Object> replacementMap = yaml.getValues(false);
		
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