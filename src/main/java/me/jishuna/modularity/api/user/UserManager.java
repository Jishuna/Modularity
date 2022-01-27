package me.jishuna.modularity.api.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.jishuna.modularity.Modularity;
import me.jishuna.modularity.api.events.AsyncUserLoadingEvent;
import me.jishuna.modularity.api.events.UserLoadedEvent;

public class UserManager {

	private final Modularity plugin;
	private final Map<UUID, User> userMap = new HashMap<>();

	public UserManager(Modularity plugin) {
		this.plugin = plugin;
	}

	public Optional<User> getUser(Player player) {
		return getUser(player.getUniqueId());
	}

	public Optional<User> getUser(UUID uuid) {
		return Optional.ofNullable(this.userMap.get(uuid));
	}

	public void removeUser(Player player) {
		removeUser(player.getUniqueId());
	}

	public void removeUser(UUID uuid) {
		this.userMap.remove(uuid);
	}

	public void loadUser(Player player) {
		loadUser(player.getUniqueId());
	}

	public void loadUser(UUID uuid) {
		User user = loadUserData(uuid);

		this.userMap.put(uuid, user);

		Bukkit.getScheduler().runTask(this.plugin,
				() -> Bukkit.getPluginManager().callEvent(new UserLoadedEvent(user)));
	}

	private User loadUserData(UUID uuid) {
		User user = new User(uuid);
		Bukkit.getPluginManager().callEvent(new AsyncUserLoadingEvent(user, this.plugin.getStorageAdapter()));

		return user;
	}
}
