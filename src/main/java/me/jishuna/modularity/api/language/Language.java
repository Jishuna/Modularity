package me.jishuna.modularity.api.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Language {

	private final String key;
	private final Map<String, String> conversionMap = new HashMap<>();

	public Language(String key) {
		this.key = key;
	}

	public void loadFromYaml(YamlConfiguration yaml) {
		for (String key : yaml.getKeys(true)) {
			Object value = yaml.get(key);
			if (value == null || !(value instanceof String))
				continue;

			System.out.println(key + " - " + value);
			this.conversionMap.put(key, ChatColor.translateAlternateColorCodes('&', value.toString()));
		}
	}

	public Optional<String> translate(String key) {
		return Optional.ofNullable(this.conversionMap.get(key));
	}

	public Map<String, String> getMap() {
		return this.conversionMap;
	}

	public String getKey() {
		return key;
	}

}