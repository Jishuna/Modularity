package me.jishuna.modularity.api.command;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import me.jishuna.modularity.api.module.ModularPlugin;

public class ModuleCommand extends Command {
	private final CommandExecutor executor;
	private TabCompleter tabCompleter;
	private final ModularPlugin plugin;

	public ModuleCommand(ModularPlugin plugin, CommandExecutor executor) {
		super(CommandUtils.getName(executor));
		this.plugin = plugin;
		this.executor = executor;

		processAnnotations();
	}

	public ModuleCommand(ModularPlugin plugin, TabExecutor executor) {
		this(plugin, (CommandExecutor) executor);
		this.tabCompleter = executor;
	}

	private void processAnnotations() {
		CommandUtils.getPermission(executor).ifPresent(this::setPermission);
	}

	public void setTabCompleter(TabCompleter tabCompleter) {
		this.tabCompleter = tabCompleter;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		try {
			this.executor.onCommand(sender, this, commandLabel, args);
		} catch (Throwable ex) {
			throw new CommandException("Unhandled exception executing command '" + commandLabel, ex);
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) {
		return tabComplete(sender, alias, args);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (this.tabCompleter != null) {
			return this.tabCompleter.onTabComplete(sender, this, alias, args);
		}
		return super.tabComplete(sender, alias, args);
	}

	public boolean register() {
		return CommandUtils.registerCommand(this, this.plugin.getModuleInfo().getName());
	}
}
