package com.brynwyl.letho.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brynwyl.letho.io.level.WorldIO;
import com.brynwyl.letho.ref.LocationRef;
import com.brynwyl.letho.world.World;

public class WorldControl {
	public static Logger log;

	public static File worldsDir = new File(
			LocationRef.PROGRAM_LOCATION_PARENT_FILE, "worlds");
	public static HashMap<String, World> worlds = new HashMap<String, World>();
	public static HashMap<String, WorldIO> ios = new HashMap<String, WorldIO>();

	public static void init() {
		log = LogManager.getLogger("World Manager");

		loadWorlds();
	}

	public static void loadWorlds() {
		File[] childs = worldsDir.listFiles();
		if (childs != null) {
			for (File file : childs) {
				try {
					WorldIO worldio = new WorldIO(file);
					World world = worldio.load();
					ios.put(world.getName(), worldio);
					worlds.put(world.getName(), world);
				} catch (IOException e) {
					log.error("Error while loading world: " + file.getName(), e);
				}
			}
		}
	}

	public static String[] getWorlds() {
		return worlds.keySet().toArray(new String[0]);
	}
}
