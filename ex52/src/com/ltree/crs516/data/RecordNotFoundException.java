package com.ltree.crs516.data;

/**
 * Exception thrown when a record can not be found. Since it does not extend
 * {@link RuntimeException} the calling code will have to catch it and try
 * to recover.
 * 
 * @author crs516 development team
 */
 @SuppressWarnings("serial")
public final class RecordNotFoundException extends DataAccessException {

    public RecordNotFoundException() {
        super();
    }

    /**
     * Constructs an instance of this Exception with the given message.
     * @param message, A string, the message to put in the Exception.
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
}
