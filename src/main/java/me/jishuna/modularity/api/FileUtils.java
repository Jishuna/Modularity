package me.jishuna.modularity.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import me.jishuna.modularity.api.module.ModularPlugin;

public class FileUtils {
	private static final Class<ModularPlugin> MODULAR_PLUGIN_CLASS = ModularPlugin.class;

	public static Class<? extends ModularPlugin> loadModule(File file, ClassLoader parentLoader) throws IOException {
		if (!file.exists()) {
			return null;
		}

		final URL jar = file.toURI().toURL();
		final URLClassLoader loader = new URLClassLoader(new URL[] { jar }, parentLoader);
		final List<String> classIdentifiers = new ArrayList<>();

		try (final JarInputStream stream = new JarInputStream(jar.openStream())) {
			JarEntry entry;
			while ((entry = stream.getNextJarEntry()) != null) {
				final String name = entry.getName();
				if (name.isEmpty() || !name.endsWith(".class")) {
					continue;
				}

				classIdentifiers.add(name.substring(0, name.lastIndexOf('.')).replace('/', '.'));
			}
		}
		final List<Class<? extends ModularPlugin>> classes = new ArrayList<>();

		for (final String match : classIdentifiers) {
			try {

				final Class<?> loaded = loader.loadClass(match);
				if (MODULAR_PLUGIN_CLASS.isAssignableFrom(loaded)) {
					classes.add(loaded.asSubclass(MODULAR_PLUGIN_CLASS));
				}
			} catch (final ClassNotFoundException ignored) {
			}
		}
		if (classes.isEmpty()) {
			loader.close();
			return null;
		}
		return classes.get(0);
	}

	public static void extractFiles(Path archiveFile, Path sourcePath, Path destPath) throws IOException {

		Files.createDirectories(destPath);

		try (ZipFile archive = new ZipFile(archiveFile.toFile())) {

			List<? extends ZipEntry> entries = archive.stream().sorted(Comparator.comparing(ZipEntry::getName))
					.collect(Collectors.toList());

			for (ZipEntry entry : entries) {
				if (!entry.getName().startsWith(sourcePath.toString()))
					continue;

				Path entryDest = destPath.resolve(entry.getName());

				if (entry.isDirectory()) {
					Files.createDirectory(entryDest);
					continue;
				}

				Files.copy(archive.getInputStream(entry), entryDest);
			}
		}

	}
}
