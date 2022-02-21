package me.jishuna.modularity.api.storage;

import java.io.IOException;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LocationTypeAdapter extends TypeAdapter<Location> {
	private static final DecimalFormat FPRMATTER = new DecimalFormat("#.##");

	@Override
	public void write(JsonWriter writer, Location location) throws IOException {
		writer.beginObject();
		writer.name("world");
		writer.value(location.getWorld().getName());
		writer.name("x");
		writer.value(FPRMATTER.format(location.getX()));
		writer.name("y");
		writer.value(FPRMATTER.format(location.getY()));
		writer.name("z");
		writer.value(FPRMATTER.format(location.getZ()));
		writer.endObject();
	}

	@Override
	public Location read(JsonReader reader) throws IOException {
		String fieldname = null;
		World world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		reader.beginObject();

		while (reader.hasNext()) {
			JsonToken token = reader.peek();

			if (token.equals(JsonToken.NAME)) {
				fieldname = reader.nextName();
			}

			if ("world".equals(fieldname)) {
				token = reader.peek();
				world = Bukkit.getWorld(reader.nextString());
			}

			if ("x".equals(fieldname)) {
				token = reader.peek();
				x = reader.nextDouble();
			}

			if ("y".equals(fieldname)) {
				token = reader.peek();
				y = reader.nextDouble();
			}

			if ("z".equals(fieldname)) {
				token = reader.peek();
				z = reader.nextDouble();
			}
		}
		reader.endObject();

		return new Location(world, x, y, z);
	}

}
