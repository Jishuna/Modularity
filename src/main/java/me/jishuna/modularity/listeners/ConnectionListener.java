package me.jishuna.modularity.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.jishuna.modularity.api.storage.StorageAdapter;
import me.jishuna.modularity.api.storage.Tables;
import me.jishuna.modularity.api.user.UserManager;

public class ConnectionListener implements Listener {

	private final UserManager userManager;
	private final StorageAdapter storageAdapter;

	public ConnectionListener(UserManager userManager, StorageAdapter adapter) {
		this.userManager = userManager;
		this.storageAdapter = adapter;
	}

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event) {
		this.userManager.loadUser(event.getUniqueId());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.userManager.removeUser(event.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		// Keep track of last name
		this.userManager.getUser(player).ifPresent(user -> {
			user.getUserData().set("username", player.getName());
			this.storageAdapter.saveUserData(player.getUniqueId(), Tables.MISC, "username", player.getName());
		});
	}

}
