package me.jishuna.modularity.api.module;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ModuleLogger extends Logger {
	private final String prefix;

	public ModuleLogger(ModularPlugin module) {
		super(module.getClass().getCanonicalName(), (String) null);
		this.prefix = "[" + module.getModuleInfo().getName() + "]";
		this.setParent(Bukkit.getServer().getLogger());
		this.setLevel(Level.ALL);
	}

	public void log(@NotNull LogRecord logRecord) {
		logRecord.setMessage(this.prefix
				+ logRecord.getMessage());
		super.log(logRecord);
	}
}