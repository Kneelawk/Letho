package com.brynwyl.letho.world.progress;

public class BadProgressException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1493952915506052452L;

	public BadProgressException() {
	}

	public BadProgressException(String message) {
		super(message);
	}

	public BadProgressException(Throwable cause) {
		super(cause);
	}

	public BadProgressException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadProgressException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
