package me.jishuna.modularity.api.user;

import java.util.UUID;

import me.jishuna.modularity.api.language.Language;

public class User {
	private final UUID uuid;

	private final UserSettings settings = new UserSettings();
	private final UserData data = new UserData();
	private Language language;

	public User(UUID uuid) {
		this.uuid = uuid;
	}

	public UserData getUserData() {
		return this.data;
	}

	public UserSettings getUserSettings() {
		return this.settings;
	}

	public UUID getUniqueId() {
		return uuid;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
