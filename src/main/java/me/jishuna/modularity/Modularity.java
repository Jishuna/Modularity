package me.jishuna.modularity;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import me.jishuna.modularity.api.FileUtils;
import me.jishuna.modularity.api.ModularityConfig;
import me.jishuna.modularity.api.language.LanguageRegistry;
import me.jishuna.modularity.api.module.ModuleManager;
import me.jishuna.modularity.api.storage.SQLiteStorageAdapter;
import me.jishuna.modularity.api.storage.StorageAdapter;
import me.jishuna.modularity.api.user.UserManager;
import me.jishuna.modularity.listeners.ConnectionListener;
import redempt.redlib.config.ConfigManager;

public class Modularity extends JavaPlugin {
	private UserManager userManager;
	private StorageAdapter storageAdapter;
	private ModuleManager moduleManager;
	private LanguageRegistry languageRegistry;

	private PaperCommandManager commandManager;

	private Path jarPath;

	@Override
	public void onEnable() {
		try {
			jarPath = Path.of(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ConfigManager.create(this).target(ModularityConfig.class).saveDefaults().load();

		this.commandManager = new PaperCommandManager(this);

		setupLanguages();
		this.storageAdapter = new SQLiteStorageAdapter(new File(this.getDataFolder(), "/data"));
		this.userManager = new UserManager(this);

		ReflectionUtils.trySetPrivateField(ModularityAPI.class, null, "plugin", this);

		this.registerEvents();

		this.moduleManager = new ModuleManager(this);
		this.moduleManager.loadModules();
		this.moduleManager.enableModules();
	}

	@Override
	public void onDisable() {
		this.moduleManager.disableModules();

		this.storageAdapter.close();
	}

	private void setupLanguages() {
		File languageFolder = new File(getDataFolder(), "/languages");

		if (!languageFolder.exists()) {
			try {
				FileUtils.extractFiles(jarPath, Path.of("languages/"), getDataFolder().toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.languageRegistry = new LanguageRegistry(this);
	}

	private void registerEvents() {
		PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new ConnectionListener(this.userManager, this.storageAdapter), this);
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public StorageAdapter getStorageAdapter() {
		return storageAdapter;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public PaperCommandManager getCommandManager() {
		return commandManager;
	}

	public LanguageRegistry getLanguageRegistry() {
		return languageRegistry;
	}
}
