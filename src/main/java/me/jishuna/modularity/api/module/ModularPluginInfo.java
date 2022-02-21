package me.jishuna.modularity.api.module;

public class ModularPluginInfo {
	
	private final String name;
	private final String version;
	private final String author;
	
	public ModularPluginInfo(String name, String version, String author) {
		this.name = name;
		this.version = version;
		this.author = author;
	}

	public ModularPluginInfo(Module module) {
		this(module.name(), module.version(), module.author());
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

}
