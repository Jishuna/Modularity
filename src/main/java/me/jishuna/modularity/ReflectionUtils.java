package me.jishuna.modularity;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public static <T> boolean trySetPrivateField(Class<T> clazz, T instance, String name, Object value) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(instance, value);
			field.setAccessible(false);
			return true;
		} catch (ReflectiveOperationException ex) {
			return false;
		}
	}

}
