package com.brynwyl.letho.io.level;

import java.io.IOException;

public class InvalidWorldException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8032642578220415843L;

	public InvalidWorldException() {
	}

	public InvalidWorldException(String message) {
		super(message);
	}

	public InvalidWorldException(Throwable cause) {
		super(cause);
	}

	public InvalidWorldException(String message, Throwable cause) {
		super(message, cause);
	}

}
