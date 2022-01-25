package me.jishuna.modularity.api.user.settings;

import java.util.List;

public class BooleanSetting extends Setting {
	private static final List<String> OPTIONS = List.of("true", "false");
	private final String def;

	public BooleanSetting(String name, String def) {
		super(name);
		this.def = def;
	}

	@Override
	public String getDefault() {
		return this.def;
	}

	@Override
	public List<String> getValidValues() {
		return OPTIONS;
	}
}
