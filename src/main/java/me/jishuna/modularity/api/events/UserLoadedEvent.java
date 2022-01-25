package me.jishuna.modularity.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import me.jishuna.modularity.api.user.User;

public class UserLoadedEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	private final User user;

	public UserLoadedEvent(@NotNull User user) {
		this.user = user;
	}

	@NotNull
	public User getUser() {
		return user;
	}

	@Override
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
