package br.com.coretech.coretech_api.infraestructure.exceptions;

public class EmailAlreadyExistsException extends java.lang.RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    public EmailAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
