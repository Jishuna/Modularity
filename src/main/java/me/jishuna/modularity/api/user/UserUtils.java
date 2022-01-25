package me.jishuna.modularity.api.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class UserUtils {

	public static <T> void setUserData(User user, String key, Class<T> type, JsonObject json, Gson gson) {
		T data = gson.fromJson(json.get(key), type);
		if (data != null) {
			user.getUserData().set(key, data);
		}
	}
}
