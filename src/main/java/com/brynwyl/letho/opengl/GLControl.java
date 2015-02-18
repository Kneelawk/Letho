package com.brynwyl.letho.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GLControl {
	public static final int DISPLAY_WIDTH = 1000;
	public static final int DISPLAY_HEIGHT = 600;
	public static final String DISPLAY_NAME = "Letho";
	
	public static void init() {
		
	}
	
	public static void setupDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		Display.create();
	}
	
	public static void setupGL() {
		
	}
}
