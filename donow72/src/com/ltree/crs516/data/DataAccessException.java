package com.ltree.crs516.data;

/**
 * Exception thrown when a data can not be found. Since it does not extend
 * {@link RuntimeException} the calling code will have to catch it and try
 * to recover.
 * 
 * @author crs516 development team
 */
 @SuppressWarnings("serial")
public class DataAccessException extends Exception {

	/**
     * Niladic constructor.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Constructs an instance of this Exception with the given message.
     * @param message A string, the message to put in the Exception.
     */
    public DataAccessException(String message) {
        super(message);
    }

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
    
}
