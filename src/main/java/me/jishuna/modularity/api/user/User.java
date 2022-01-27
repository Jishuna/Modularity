package me.jishuna.modularity.api.user;

import java.util.UUID;

public class User {
	private final UUID uuid;
	
	private final UserSettings settings = new UserSettings();
	private final UserData data = new UserData();

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
}
