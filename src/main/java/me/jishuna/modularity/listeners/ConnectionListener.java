package me.jishuna.modularity.listeners;

import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.jishuna.modularity.api.user.UserManager;

public class ConnectionListener implements Listener {

	private final UserManager userManager;

	public ConnectionListener(UserManager userManager) {
		this.userManager = userManager;
	}

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event) {
		this.userManager.loadUser(event.getUniqueId());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		CompletableFuture.runAsync(() -> this.userManager.saveUser(event.getPlayer()));
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		// Keep track of last name
		this.userManager.getUser(player).ifPresent(user -> user.getUserData().set("username", player.getName()));
	}

}
