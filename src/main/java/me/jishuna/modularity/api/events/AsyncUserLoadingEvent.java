package me.jishuna.modularity.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.jishuna.modularity.api.user.User;

public class AsyncUserLoadingEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final User user;
	private final JsonObject jsonData;
	private final Gson gson;

	public AsyncUserLoadingEvent(@NotNull User user, @Nullable JsonObject jsonData, @NotNull Gson gson) {
		super(true);
		this.user = user;
		this.jsonData = jsonData;
		this.gson = gson;
	}

	@NotNull
	public User getUser() {
		return user;
	}

	@Nullable
	public JsonObject getJson() {
		return jsonData;
	}

	@NotNull
	public Gson getGson() {
		return gson;
	}

	@Override
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
