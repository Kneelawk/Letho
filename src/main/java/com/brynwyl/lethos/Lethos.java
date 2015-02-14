package com.brynwyl.lethos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.brynwyl.lethos.classpath.CPControl;

public class Lethos {
	public static Logger log;

	public static void main(String[] args) {
		log = LogManager.getLogger("Lethos");
		log.info("Init Lethos");

		CPControl.init();
	}
}
