package com.brynwyl.letho.world.progress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brynwyl.letho.io.level.ProgressIO;

public class ProgressList {

	private ProgressIO io;
	private List<String> progressNames;
	private HashMap<String, Progress> loadedProgresses;

	public ProgressList(ProgressIO io) {
		progressNames = new ArrayList<String>();
		loadedProgresses = new HashMap<String, Progress>();
	}

	public ProgressList(ProgressIO io, List<String> progressNames) {
		this.progressNames = progressNames;
		loadedProgresses = new HashMap<String, Progress>();
	}

	public void addProgress(Progress prog) {
		if (!progressNames.contains(prog.getName())) {
			progressNames.add(prog.getName());
			loadedProgresses.put(prog.getName(), prog);
		}
	}

	public Progress getProgress(String name) throws IOException {
		if (!progressNames.contains(name))
			return null;
		if (loadedProgresses.containsKey(name)) {
			return loadedProgresses.get(name);
		} else {
			return io.load(name);
		}
	}
}
