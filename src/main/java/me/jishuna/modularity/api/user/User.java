package me.jishuna.modularity.api.user;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class User {
	private final UUID uuid;
	private final UserSettings settings;
	private final UserData data = new UserData();

	public User(UUID uuid, Gson gson, JsonObject json) {
		this.uuid = uuid;
		this.settings = new UserSettings(gson, json);
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

	public JsonObject toJson(Gson gson) {
		JsonObject jsonObject = new JsonObject();

		jsonObject.add("data", this.data.toJson(gson));
		jsonObject.add("settings", this.settings.toJson(gson));
		return jsonObject;
	}
}
