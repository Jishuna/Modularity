package me.jishuna.modularity.api.language;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.modularity.Modularity;

public class LanguageRegistry {

	private final Language defaultLanguage;
	private final Map<String, Language> languageMap = new HashMap<>();

	public LanguageRegistry(Modularity plugin) {
		File languageFolder = new File(plugin.getDataFolder(), "/languages");

		loadFromDirectory(languageFolder);
		
		//TODO temp
		this.defaultLanguage = getOrCreateLanguage("en_us");
	}

	public void loadFromDirectory(File directory) {
		for (File file : directory.listFiles((dir, name) -> name.endsWith(".yml"))) {
			String key = file.getName();
			YamlConfiguration languageConfig = YamlConfiguration.loadConfiguration(file);

			Language language = getOrCreateLanguage(key);
			language.loadFromYaml(languageConfig);

			this.languageMap.put(key, language);
		}
	}

	public Language getLanguage(String key) {
		return this.languageMap.getOrDefault(key, this.defaultLanguage);
	}

	public Language getOrCreateLanguage(String key) {
		return this.languageMap.computeIfAbsent(key, mapKey -> new Language(key));
	}
}
