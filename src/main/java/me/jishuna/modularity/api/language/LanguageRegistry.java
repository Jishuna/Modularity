package me.jishuna.modularity.api.language;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.modularity.Modularity;
import me.jishuna.modularity.api.ModularityConfig;
import me.jishuna.modularity.api.module.ModularPlugin;
import me.jishuna.modularity.api.user.User;

public class LanguageRegistry {

	private final Language defaultLanguage;
	private final Map<String, Language> languageMap = new HashMap<>();

	public LanguageRegistry(Modularity plugin) {
		File languageFolder = new File(plugin.getDataFolder(), "/languages");

		loadFromDirectory(languageFolder);

		this.defaultLanguage = getOrCreateLanguage(ModularityConfig.defaultLanguage);	
	}

	public void loadFromDirectory(File directory) {
		for (File file : directory.listFiles((dir, name) -> name.endsWith(".yml"))) {
			String key = file.getName();
			key = key.substring(0, key.lastIndexOf("."));
			
			YamlConfiguration languageConfig = YamlConfiguration.loadConfiguration(file);

			Language language = getOrCreateLanguage(key);
			language.loadFromYaml(languageConfig);

			this.languageMap.put(key, language);
		}
	}

	public void loadFromModule(ModularPlugin module) {
		module.unpackLanguageFiles();

		loadFromDirectory(new File(module.getDataFolder() + "/languages"));
	}

	public String translate(String text, Optional<User> optional) {
		return optional.isPresent() ? translate(text, optional.get()) : translateDefault(text);
	}

	public String translate(String text, User user) {
		return translate(text, user.getLanguage());
	}

	public String translate(String text, Language language) {
		if (language == null) {
			return translateDefault(text);
		}
		Optional<String> translation = language.translate(text);

		return translation.orElse(translateDefault(text));
	}

	public String translateDefault(String text) {
		Optional<String> translation = this.defaultLanguage.translate(text);

		return translation.isPresent() ? translation.get() : "Missing Translation: " + text;
	}

	public Language getLanguage(String key) {
		return this.languageMap.getOrDefault(key, this.defaultLanguage);
	}

	public Language getOrCreateLanguage(String key) {
		return this.languageMap.computeIfAbsent(key, mapKey -> new Language(key));
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}
}
