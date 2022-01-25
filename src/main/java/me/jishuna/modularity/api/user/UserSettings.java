package me.jishuna.modularity.api.user;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import me.jishuna.modularity.api.user.settings.Setting;

public class UserSettings {
	private static final Type STRING_MAP_TYPE = new TypeToken<ConcurrentHashMap<String, String>>() {
	}.getType();

	private final Map<String, String> settingsMap;

	public UserSettings(Gson gson, JsonObject json) {
		if (json != null) {
			this.settingsMap = gson.fromJson(json.get("settings"), STRING_MAP_TYPE);
		} else {
			this.settingsMap = new ConcurrentHashMap<>();
		}
	}

	public void set(Setting setting, String value) {
		if (setting.getValidValues().contains(value)) {
			this.settingsMap.put(setting.getName(), value);
		}
	}

	public String getSetting(Setting setting) {
		return this.settingsMap.getOrDefault(setting.getName(), setting.getDefault());
	}

	public boolean getBooleanSetting(Setting setting) {
		return Boolean.getBoolean(this.settingsMap.getOrDefault(setting.getName(), setting.getDefault()));
	}

	public JsonElement toJson(Gson gson) {
		return gson.toJsonTree(this.settingsMap);
	}
}
