package me.jishuna.modularity;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.jishuna.modularity.api.user.User;

public class ModularityAPI {

	private static Modularity plugin;
	
	public static Modularity getPlugin() {
		return plugin;
	}

	public static Optional<User> getUser(Player player) {
		return getUser(player.getUniqueId());
	}

	public static Optional<User> getUser(UUID uuid) {
		return plugin.getUserManager().getUser(uuid);
	}
}
