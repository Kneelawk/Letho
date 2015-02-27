package com.brynwyl.letho.opengl;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.brynwyl.letho.Letho;
import com.brynwyl.letho.util.ImageInfo;

public class GLControl {
	public static final int DISPLAY_WIDTH = 1000;
	public static final int DISPLAY_HEIGHT = 600;
	public static final String DISPLAY_NAME = "Letho";

	public static Logger log;
	public static ByteBuffer[] icons = null;

	public static void init() {
		log = LogManager.getLogger("GLControl");
		try {
			setupDisplay();

			setupGL();
		} catch (LWJGLException e) {
			log.error("LWJGLException: ", e);
		} catch (IOException e) {
			log.error("IOException: ");
		}
	}

	public static void setupDisplay() throws LWJGLException, IOException {
		Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		Display.setTitle(DISPLAY_NAME);
		setupIcons();
		Display.setIcon(icons);
		Display.create();
	}

	public static void setupIcons() throws IOException {
		if (icons != null)
			return;
		switch (LWJGLUtil.getPlatform()) {
		case LWJGLUtil.PLATFORM_LINUX:
			icons = new ByteBuffer[] { ImageInfo
					.loadTextureResource("/textures/icons/icon_32x32.png").buf };
			break;
		case LWJGLUtil.PLATFORM_MACOSX:
			icons = new ByteBuffer[] { ImageInfo
					.loadTextureResource("/textures/icons/icon_128x128.png").buf };
			break;
		case LWJGLUtil.PLATFORM_WINDOWS:
			icons = new ByteBuffer[] {
					ImageInfo
							.loadTextureResource("/textures/icons/icon_16x16.png").buf,
					ImageInfo
							.loadTextureResource("/textures/icons/icon_32x32.png").buf };
			break;
		}
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
