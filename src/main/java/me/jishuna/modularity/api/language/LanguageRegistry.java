package me.jishuna.modularity.api.language;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.modularity.Modularity;

public class LanguageRegistry {

	// private final Language defaultLanguage;
	private final Map<String, Language> languageMap = new HashMap<>();

	public LanguageRegistry(Modularity plugin) {
		File languageFolder = new File(plugin.getDataFolder(), "/languages");

		for (File file : languageFolder.listFiles((dir, name) -> name.endsWith(".yml"))) {
			String key = file.getName();
			YamlConfiguration languageConfig = YamlConfiguration.loadConfiguration(file);

			Language language = new Language(key, languageConfig.getValues(false));
			this.languageMap.put(key, language);
		}
	}
}
