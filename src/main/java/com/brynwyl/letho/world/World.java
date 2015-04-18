package com.brynwyl.letho.world;

import com.brynwyl.letho.io.level.WorldIO;
import com.brynwyl.letho.world.level.LevelList;
import com.brynwyl.letho.world.progress.ProgressList;

public class World {

	private String worldName;
	private WorldIO io;
	private ProgressList progList;
	private LevelList levelList;

	public World() {

	}

	public World(WorldIO io, String name) {
		this.io = io;
		this.worldName = name;
	}

	public String getName() {
		return worldName;
	}

	public void setName(String name) {
		worldName = name;
	}
}
