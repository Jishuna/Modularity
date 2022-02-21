package me.jishuna.modularity.api.module;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.jishuna.modularity.Modularity;
import me.jishuna.modularity.api.FileUtils;

public abstract class ModularPlugin {
	private ModularPluginInfo moduleInfo;
	private Logger logger;
	private File dataFolder;

	final void setup(Modularity plugin, ModularPluginInfo moduleInfo) {
		this.moduleInfo = moduleInfo;
		this.logger = Logger.getLogger(moduleInfo.getName());
		this.dataFolder = new File(plugin.getModuleManager().getModuleFolder(), moduleInfo.getName());
	}

	public void saveResource(@NotNull String resourcePath, boolean replace) {
		if (resourcePath != null && !resourcePath.equals("")) {
			resourcePath = resourcePath.replace('\\', '/');
			InputStream in = this.getResource(resourcePath);
			if (in == null) {
				throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in "
						+ this.getModuleInfo().getName());
			} else {
				File outFile = new File(this.dataFolder, resourcePath);
				int lastIndex = resourcePath.lastIndexOf(47);
				File outDir = new File(this.dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
				if (!outDir.exists()) {
					outDir.mkdirs();
				}

				try {
					if (outFile.exists() && !replace) {
						this.logger.log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile
								+ " because " + outFile.getName() + " already exists.");
					} else {
						OutputStream out = new FileOutputStream(outFile);
						byte[] buf = new byte[1024];

						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}

						out.close();
						in.close();
					}
				} catch (IOException var10) {
					this.logger.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
				}

			}
		} else {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}
	}

	@Nullable
	public InputStream getResource(@NotNull String filename) {
		try {
			URL url = this.getClass().getClassLoader().getResource(filename);
			if (url == null) {
				return null;
			} else {
				URLConnection connection = url.openConnection();
				connection.setUseCaches(false);
				return connection.getInputStream();
			}
		} catch (IOException var4) {
			return null;
		}
	}

	public void unpackLanguageFiles() {
		File languageFolder = new File(getDataFolder(), "/languages");

		if (!languageFolder.exists()) {
			try {
				Path jarPath = Path.of(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
				FileUtils.extractFiles(jarPath, Path.of("languages/"), getDataFolder().toPath());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public ModularPluginInfo getModuleInfo() {
		return moduleInfo;
	}

	public Logger getLogger() {
		return logger;
	}

	public File getDataFolder() {
		return dataFolder;
	}
}
