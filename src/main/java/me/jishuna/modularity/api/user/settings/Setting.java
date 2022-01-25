package me.jishuna.modularity.api.user.settings;

import java.util.List;

public abstract class Setting {
	private final String name;
	
	public Setting(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract String getDefault();
	public abstract List<String> getValidValues();

}
