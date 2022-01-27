package me.jishuna.modularity.api.user;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;

public class UserData {
	private final Map<String, Object> dataMap = new ConcurrentHashMap<>();

	public void set(@NotNull String key, @NotNull Object data) {
		this.dataMap.put(key, data);
	}

	public Optional<Integer> getInt(@NotNull String key) {
		Object value = this.dataMap.get(key);
		if (value instanceof Integer val) {
			return Optional.of(val);
		}
		return Optional.empty();
	}

	public int getInt(@NotNull String key, int def) {
		return getInt(key).orElseGet(() -> def);
	}

	public Optional<Double> getDouble(@NotNull String key) {
		Object value = this.dataMap.get(key);
		if (value instanceof Double val) {
			return Optional.of(val);
		}
		return Optional.empty();
	}

	public Double getDouble(@NotNull String key, double def) {
		return getDouble(key).orElseGet(() -> def);
	}

	public <T> Optional<T> getObject(@NotNull String key, Class<T> type) {
		Object value = this.dataMap.get(key);

		if (type.isInstance(value)) {
			return Optional.ofNullable(type.cast(value));
		}
		return Optional.empty();
	}

	@NotNull
	public <T> T getObject(@NotNull String key, Class<T> type, @NotNull T def) {
		return getObject(key, type).orElseGet(() -> def);
	}
}