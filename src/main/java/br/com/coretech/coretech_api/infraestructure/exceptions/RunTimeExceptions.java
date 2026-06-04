package br.com.coretech.coretech_api.infraestructure.exceptions;

public class RunTimeExceptions extends RuntimeException {
    public RunTimeExceptions(String message) {
        super(message);
    }
    public RunTimeExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }
}
