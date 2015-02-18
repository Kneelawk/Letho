package com.brynwyl.letho;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brynwyl.letho.classpath.CPControl;
import com.brynwyl.letho.opengl.GLControl;

public class Lethos {
	public static Logger log;

	public static void main(String[] args) {
		log = LogManager.getLogger("Lethos");
		log.info("Init Lethos");

		// add natives to classpath
		CPControl.init();

		// load opengl
		GLControl.init();
	}
}
