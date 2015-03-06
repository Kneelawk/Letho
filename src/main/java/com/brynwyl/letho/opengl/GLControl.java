package com.brynwyl.letho.opengl;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

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
		log.info("Init GL Control");

		try {
			setupDisplay();

			setupGL();

			Letho.initRender();
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
		GL11.glClearColor(0.3f, 0.3f, 0.3f, 1f);

		// Ortho
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// Alpha stuf
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Texture stuff
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void startGLLoop() {
		while (!Display.isCloseRequested()) {
			glLoop();
			
			Display.update();
		}

		shutdown();
	}

	public static void glLoop() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		Letho.glLoop();
	}

	public static void shutdown() {
		Letho.shutdown();
		Display.destroy();
	}
}
