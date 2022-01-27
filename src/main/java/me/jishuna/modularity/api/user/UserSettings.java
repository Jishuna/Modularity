package me.jishuna.modularity.api.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.jishuna.modularity.api.user.settings.Setting;

public class UserSettings {
	private final Map<String, String> settingsMap = new ConcurrentHashMap<>();

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
}
