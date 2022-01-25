package me.jishuna.modularity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.modularity.api.user.UserManager;
import me.jishuna.modularity.listeners.ConnectionListener;

public class Modularity extends JavaPlugin {
	private UserManager userManager;

	@Override
	public void onEnable() {
		this.userManager = new UserManager(this);

		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ConnectionListener(this.userManager), this);
	}
	
	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.userManager.saveUser(player);
		}
	}

	public UserManager getUserManager() {
		return userManager;
	}

}
