package com.brynwyl.letho.util;

import com.kneelawk.stree.core.FloatSTreeNode;
import com.kneelawk.stree.core.IntSTreeNode;
import com.kneelawk.stree.core.ListSTreeNode;
import com.kneelawk.stree.core.MapSTreeNode;
import com.kneelawk.stree.core.StringSTreeNode;

public class STreeUtils {
	public static int get(IntSTreeNode node, int def) {
		if (node == null)
			return def;
		return node.data;
	}

	public static float get(FloatSTreeNode node, float def) {
		if (node == null)
			return def;
		return node.data;
	}

	public static String get(StringSTreeNode node, String def) {
		if (node == null)
			return def;
		return node.data;
	}

	public static MapSTreeNode getMap(MapSTreeNode parent, String name,
			boolean instantiate) {
		MapSTreeNode child = parent.getMap(name);
		if (child == null && instantiate) {
			child = new MapSTreeNode();
			parent.put(name, child);
		}
		return child;
	}

	public static ListSTreeNode getList(MapSTreeNode parent, String name,
			boolean instantiate) {
		ListSTreeNode child = parent.getList(name);
		if (child == null && instantiate) {
			child = new ListSTreeNode();
			parent.put(name, child);
		}
		return child;
	}
}
