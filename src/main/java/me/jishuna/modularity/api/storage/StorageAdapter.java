package me.jishuna.modularity.api.storage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageAdapter {

	public <T> List<T> loadAllUserData(UUID id, String table, Class<T> type);

	public <T> Optional<T> loadUserData(UUID id, String table, String key, Class<T> type);

	public void saveUserData(UUID id, String table, String key, Object data);

	public void createBasicTable(String name);

	public void close();
}
