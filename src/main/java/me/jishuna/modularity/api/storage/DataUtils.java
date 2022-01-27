package me.jishuna.modularity.api.storage;

import java.nio.ByteBuffer;
import java.util.UUID;

import me.jishuna.modularity.api.user.User;

public class DataUtils {

	public static byte[] getBytesFromUUID(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());

		return bb.array();
	}

	public static UUID getUUIDFromBytes(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public static <T> void loadDataEntry(User user, String table, String key, Class<T> type, StorageAdapter adapater) {
		adapater.loadUserData(user.getUniqueId(), table, key, type)
				.ifPresent(data -> user.getUserData().set(key, data));
	}

}
