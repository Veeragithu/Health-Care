package com.cerner.HealthCare.Exception;

/**
 * Exception class representing a bad request.
 * This exception can be thrown when the request made by the client is invalid or malformed.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a new {@code BadRequestException} with no detail message.
     */
    public BadRequestException() {
        super();
    }

    
}
