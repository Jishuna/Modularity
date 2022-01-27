package me.jishuna.modularity.api.storage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class SQLiteStorageAdapter implements StorageAdapter {

	private final DatabaseConnectionPool connectionPool;
	private final Gson gson = new GsonBuilder().create();

	public SQLiteStorageAdapter(File dataDir) {
		this.connectionPool = new DatabaseConnectionPool("Modularity", "storage", dataDir);

		this.createBasicTable(Tables.MISC);
		this.createBasicTable(Tables.SETTINGS);
	}

	@Override
	public <T> Optional<T> loadUserData(UUID id, String table, String key, Class<T> type) {
		try (Connection connection = this.connectionPool.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("SELECT * FROM '" + table + "' WHERE ID=? AND DataKey=?");) {

			statement.setBytes(1, DataUtils.getBytesFromUUID(id));
			statement.setString(2, key);

			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.next()) {
				return Optional.empty();
			}
			return Optional.ofNullable(this.gson.fromJson(resultSet.getString("Data"), type));
		} catch (SQLException | JsonSyntaxException ex) {
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public void saveUserData(UUID id, String table, String key, Object data) {
		try (Connection connection = this.connectionPool.getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO '" + table + "' VALUES (?, ?, ?);");) {

			statement.setBytes(1, DataUtils.getBytesFromUUID(id));
			statement.setString(2, key);
			statement.setString(3, this.gson.toJson(data));

			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void createBasicTable(String name) {
		try (Connection connection = this.connectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS '" + name
						+ "' (ID BINARY(16), DataKey VARCHAR(50), Data TEXT, PRIMARY KEY(ID, DataKey) ON CONFLICT REPLACE);");) {

			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void close() {
		this.connectionPool.close();
	}
}
