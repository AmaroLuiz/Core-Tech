package br.com.coretech.coretech_api.infraestructure.exceptions;

public class ResourceNotFoundException extends java.lang.RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
