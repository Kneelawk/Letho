package com.brynwyl.letho.io.level;

import java.io.File;
import java.io.IOException;

import com.brynwyl.letho.world.progress.Progress;

public class ProgressIO {
	private File file;

	public ProgressIO(File file) {
		this.file = file;
	}

	public Progress load(String name) throws IOException {
		return null;
	}

	public void save(String name) {

	}
}
