package com.tbforward.exception;

public class ServiceException extends RuntimeException {
	
	public ServiceException() {
		super();
	}

	public ServiceException(String string) {
		super(string);
	}
	
	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -9211400036809494927L;
	
}
