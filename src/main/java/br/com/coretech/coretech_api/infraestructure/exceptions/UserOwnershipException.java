package br.com.coretech.coretech_api.infraestructure.exceptions;

public class UserOwnershipException extends java.lang.RuntimeException {
    public UserOwnershipException(String message) {
        super(message);
    }
    public UserOwnershipException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
