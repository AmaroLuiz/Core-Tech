package br.com.coretech.coretech_api.infraestructure.exceptions;

public class ConflictExceptions extends RuntimeException {
    public ConflictExceptions(String message) {
        super(message);
    }
    public ConflictExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }
}
