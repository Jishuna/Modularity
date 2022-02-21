package me.jishuna.modularity.api.module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import me.jishuna.modularity.Modularity;
import me.jishuna.modularity.api.FileUtils;

public class ModuleManager {
	private static final String MODULE_FOLDER = "/modules";

	private final Modularity plugin;
	private final Map<String, ModularPlugin> modules = new HashMap<>();
	private final File moduleFolder;

	public ModuleManager(Modularity plugin) {
		this.plugin = plugin;
		this.moduleFolder = new File(plugin.getDataFolder() + MODULE_FOLDER);
	}

	public Modularity getPlugin() {
		return plugin;
	}

	public Map<String, ModularPlugin> getModules() {
		return modules;
	}

	public File getModuleFolder() {
		return moduleFolder;
	}

	public void loadModules() {
		long start = System.nanoTime();

		if (!this.moduleFolder.exists()) {
			this.moduleFolder.mkdirs();
		}

		for (File jarFile : moduleFolder.listFiles(file -> file.getName().endsWith(".jar"))) {
			try {
				Class<? extends ModularPlugin> pluginClass = FileUtils.loadModule(jarFile,
						this.getClass().getClassLoader());

				loadModule(pluginClass);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		double time = (System.nanoTime() - start) / 1000000d;
		this.plugin.getLogger().info(String.format("Loaded %d module(s) in %.2fms.", this.modules.size(), time));
	}

	public void enableModules() {
		for (ModularPlugin modularPlugin : this.modules.values()) {
			ModularPluginInfo info = modularPlugin.getModuleInfo();
			this.plugin.getLogger().info(String.format("Enabling Module: %s v%s by %s", info.getName(),
					info.getVersion(), info.getAuthor()));
			modularPlugin.onEnable();
		}
	}

	public void disableModules() {
		for (ModularPlugin modularPlugin : this.modules.values()) {
			ModularPluginInfo info = modularPlugin.getModuleInfo();
			this.plugin.getLogger().info(String.format("Disabling Module: %s", info.getName()));
			modularPlugin.onDisable();
		}
	}

	private void loadModule(Class<? extends ModularPlugin> pluginClass) {
		try {
			Constructor<? extends ModularPlugin> constuctor = pluginClass.getDeclaredConstructor();

			Module module = pluginClass.getDeclaredAnnotation(Module.class);
			if (module == null) {
				this.plugin.getLogger().severe(String
						.format("Error loading module from %s: @Module annotation missing.", pluginClass.toString()));
				return;
			}

			ModularPluginInfo moduleInfo = new ModularPluginInfo(module);

			ModularPlugin modularPlugin = constuctor.newInstance();
			modularPlugin.setup(this.plugin, moduleInfo);

			String name = moduleInfo.getName();

			this.modules.put(name, modularPlugin);
			this.plugin.getLogger().info(String.format("Loading Module: %s v%s", name, moduleInfo.getVersion()));
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}
}
