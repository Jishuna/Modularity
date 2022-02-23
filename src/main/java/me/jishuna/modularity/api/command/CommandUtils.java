package me.jishuna.modularity.api.command;

import java.lang.reflect.Field;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.TabExecutor;

import me.jishuna.modularity.api.module.ModularPlugin;

public class CommandUtils {
	private static CommandMap commandMap;

	static {
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);

			commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static String getName(CommandExecutor executor) {
		CommandName command = executor.getClass().getDeclaredAnnotation(CommandName.class);
		if (command == null)
			throw new InvalidCommandException(executor.getClass().getName() + " is missing the @CommandName annotation");

		return command.value();
	}

	public static Optional<String> getPermission(CommandExecutor executor) {
		Permission permission = executor.getClass().getDeclaredAnnotation(Permission.class);
		if (permission == null)
			return Optional.empty();

		return Optional.of(permission.value());
	}

	public static boolean registerCommand(ModuleCommand command, String prefix) {
		return commandMap.register(prefix, command);
	}

	public static boolean registerCommand(ModularPlugin plugin, TabExecutor executor) {
		return new ModuleCommand(plugin, executor).register();
	}

}
