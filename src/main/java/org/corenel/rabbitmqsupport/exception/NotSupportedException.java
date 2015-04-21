package org.corenel.rabbitmqsupport.exception;

@SuppressWarnings("serial")
public class NotSupportedException extends RuntimeException {
	
    public NotSupportedException() {
    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }
}
