package com.brynwyl.letho;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brynwyl.letho.io.WorldControl;
import com.brynwyl.letho.opengl.GLControl;
import com.brynwyl.letho.ui.UIControl;

public class Letho {
	public static Logger log;

	public static void main(String[] args) {
		log = LogManager.getLogger("Letho");
		log.info("Init Letho");

		// load world data
		WorldControl.init();

		// load opengl
		GLControl.init();

		// start gl loop
		GLControl.startGLLoop();
	}

	public static void initRender() {
		UIControl.init();
	}

	public static void glLoop(short delta) {
		UIControl.glLoop(delta);
	}

	public static void shutdown() {
		UIControl.shutdown();
	}
}
