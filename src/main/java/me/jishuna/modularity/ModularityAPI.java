package me.jishuna.modularity;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.modularity.api.user.User;
import me.jishuna.modularity.api.user.UserManager;

public class ModularityAPI {

	private static final Modularity PLUGIN = JavaPlugin.getPlugin(Modularity.class);

	public static UserManager getUserManager() {
		return PLUGIN.getUserManager();
	}

	public static Optional<User> getUser(Player player) {
		return getUser(player.getUniqueId());
	}

	public static Optional<User> getUser(UUID uuid) {
		return PLUGIN.getUserManager().getUser(uuid);
	}

}
