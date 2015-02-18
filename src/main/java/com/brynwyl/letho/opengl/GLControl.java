package com.brynwyl.letho.opengl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.brynwyl.letho.Letho;

public class GLControl {
	public static final int DISPLAY_WIDTH = 1000;
	public static final int DISPLAY_HEIGHT = 600;
	public static final String DISPLAY_NAME = "Letho";

	public static Logger log;

	public static void init() {
		log = LogManager.getLogger("GLControl");
		try {
			setupDisplay();

			setupGL();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void setupDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		Display.setTitle(DISPLAY_NAME);
		Display.create();
	}

	public static void setupGL() {

	}

	public static void startGLLoop() {
		while (!Display.isCloseRequested()) {
			Display.update();

			glLoop();
		}

		shutdown();
	}

	public static void glLoop() {
		Letho.glLoop();
	}

	public static void shutdown() {
		Letho.shutdown();
		Display.destroy();
	}
}
