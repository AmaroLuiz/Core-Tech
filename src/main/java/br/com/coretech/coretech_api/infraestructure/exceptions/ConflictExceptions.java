package br.com.coretech.coretech_api.infraestructure.exceptions;

public class ConflictExceptions extends java.lang.RuntimeException {
    public ConflictExceptions(String message) {
        super(message);
    }
    public ConflictExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }
}
