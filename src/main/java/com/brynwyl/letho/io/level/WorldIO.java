package com.brynwyl.letho.io.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.brynwyl.letho.util.STreeUtils;
import com.brynwyl.letho.world.World;
import com.brynwyl.letho.world.level.LevelList;
import com.brynwyl.letho.world.progress.ProgressList;
import com.kneelawk.stree.core.ListSTreeNode;
import com.kneelawk.stree.core.MapSTreeNode;
import com.kneelawk.stree.core.STreeIO;
import com.kneelawk.stree.core.STreeNode;
import com.kneelawk.stree.core.StringSTreeNode;

public class WorldIO {

	public static final String PROGRESS_LIST_FILE_NAME = "progresslist.st";
	public static final String LEVEL_LIST_FILE_NAME = "levellist.st";
	public static final String WORLD_INFO_FILE_NAME = "world.st";

	private File file;
	private ProgressIO progIO;
	private LevelIO levelIO;

	public WorldIO(File file) {
		this.file = file;
		progIO = new ProgressIO(file);
		levelIO = new LevelIO(file);
	}

	public World load() throws IOException {
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));

		MapSTreeNode root = null;

		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
			String ename = ze.getName();
			if (ename.equalsIgnoreCase(WORLD_INFO_FILE_NAME)
					|| ename.equalsIgnoreCase("/" + WORLD_INFO_FILE_NAME)) {
				root = (MapSTreeNode) STreeIO.readSTreeNodeFromStream(zis);
			}
			zis.closeEntry();
		}
		zis.close();

		if (root == null)
			throw new InvalidWorldException("No world description file!");

		String worldName = STreeUtils.get(root.getString("world-name"),
				"Untitled");

		return new World(this, worldName);
	}

	public ProgressList loadProgressList() throws IOException {
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));

		MapSTreeNode root = null;

		ZipEntry ze;
		while ((ze = zis.getNextEntry()) != null) {
			String ename = ze.getName();
			if (ename.equalsIgnoreCase(PROGRESS_LIST_FILE_NAME)
					|| ename.equalsIgnoreCase("/" + PROGRESS_LIST_FILE_NAME)) {
				root = (MapSTreeNode) STreeIO.readSTreeNodeFromStream(zis);
			}
		}

		if (root == null)
			return new ProgressList(progIO);

		List<String> progNames = new ArrayList<String>();
		ListSTreeNode namesNode = STreeUtils.getList(root, "progress-names",
				true);
		for (STreeNode node : namesNode) {
			if (node != null && node instanceof StringSTreeNode)
				progNames.add(((StringSTreeNode) node).data);
		}

		return new ProgressList(progIO, progNames);
	}

	public LevelList loadLevelList() {
		return null;
	}

	public File getFile() {
		return file;
	}

	public void save(World world, File file) {

	}
}
