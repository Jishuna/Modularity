package me.jishuna.modularity.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import me.jishuna.modularity.api.storage.StorageAdapter;
import me.jishuna.modularity.api.user.User;

public class AsyncUserLoadingEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final User user;
	private final StorageAdapter adapter;

	public AsyncUserLoadingEvent(@NotNull User user, @NotNull StorageAdapter adapter) {
		super(true);
		this.user = user;
		this.adapter = adapter;
	}

	@NotNull
	public User getUser() {
		return user;
	}

	public StorageAdapter getAdapter() {
		return adapter;
	}

	@Override
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
