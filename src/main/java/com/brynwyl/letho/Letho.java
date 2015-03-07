package com.brynwyl.letho;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brynwyl.letho.classpath.CPControl;
import com.brynwyl.letho.opengl.GLControl;
import com.brynwyl.letho.ui.UIControl;

public class Letho {
	public static Logger log;

	public static void main(String[] args) {
		log = LogManager.getLogger("Lethos");
		log.info("Init Lethos");

		// add natives to classpath
		CPControl.init();
		CPControl.addNativesDir();

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
