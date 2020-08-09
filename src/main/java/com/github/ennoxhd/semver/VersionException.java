package com.github.ennoxhd.semver;

public class VersionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	VersionException() {
		super();
	}
	
	VersionException(final String message) {
		super(message);
	}
	
	VersionException(String message, Throwable cause) {
        super(message, cause);
    }
	
	VersionException(Throwable cause) {
        super(cause);
    }
	
	VersionException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
