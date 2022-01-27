package me.jishuna.modularity;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.modularity.api.ModularityConfig;
import me.jishuna.modularity.api.storage.SQLiteStorageAdapter;
import me.jishuna.modularity.api.storage.StorageAdapter;
import me.jishuna.modularity.api.user.UserManager;
import me.jishuna.modularity.listeners.ConnectionListener;
import redempt.redlib.config.ConfigManager;

public class Modularity extends JavaPlugin {
	private UserManager userManager;
	private StorageAdapter storageAdapter;

	@Override
	public void onEnable() {
		ConfigManager.create(this).target(ModularityConfig.class).saveDefaults().load();
		
		this.storageAdapter = new SQLiteStorageAdapter(new File(this.getDataFolder(), "/data"));
		this.userManager = new UserManager(this);

		ReflectionUtils.trySetPrivateField(ModularityAPI.class, null, "plugin", this);

		this.registerEvents();
	}

	@Override
	public void onDisable() {
		this.storageAdapter.close();
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
}
