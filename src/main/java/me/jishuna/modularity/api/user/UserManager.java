package me.jishuna.modularity.api.user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import me.jishuna.modularity.Modularity;
import me.jishuna.modularity.api.events.AsyncUserLoadingEvent;
import me.jishuna.modularity.api.events.UserLoadedEvent;

public class UserManager {

	private final Modularity plugin;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Map<UUID, User> userMap = new HashMap<>();
	private final File playerDataFolder;

	public UserManager(Modularity plugin) {
		this.plugin = plugin;
		this.playerDataFolder = new File(plugin.getDataFolder(), "/userdata");

		if (!this.playerDataFolder.exists()) {
			this.playerDataFolder.mkdirs();
		}
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

	public void saveUser(Player player) {
		saveUser(player.getUniqueId());
	}

	public void saveUser(UUID uuid) {
		getUser(uuid).ifPresent(user -> saveUserData(user));
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
		File playerFile = new File(this.playerDataFolder, uuid.toString() + ".json");
		JsonObject jsonObject = null;

		if (playerFile.exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(playerFile))) {
				jsonObject = this.gson.fromJson(jsonReader, JsonObject.class);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		User user = new User(uuid, this.gson, jsonObject);
		Bukkit.getPluginManager().callEvent(new AsyncUserLoadingEvent(user, jsonObject, this.gson));

		return user;
	}

	private void saveUserData(User user) {
		File playerFile = new File(this.playerDataFolder, user.getUniqueId().toString() + ".json");
		try (FileWriter fileWriter = new FileWriter(playerFile)) {
			this.gson.toJson(user.toJson(this.gson), fileWriter);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
