package br.com.coretech.coretech_api.infraestructure.exceptions;

import br.com.coretech.coretech_api.service.dto.ErrorResponse;
import br.com.coretech.coretech_api.service.dto.ValidationErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    @ExceptionHandler(ConflictExceptions.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictExceptions ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                409,
                "Conflict",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {


        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        LocalDateTime.now(),
                        400,
                        "Bad Request",
                        errors
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                403,
                "Forbidden",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handlerHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "JSON inválido"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Parâmetro '" +
                        ex.getName() +
                        "' possui formato inválido"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                409,
                "Conflict",
                "Violação de integridade dos dados"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(UserOwnershipException.class)
    public ResponseEntity<ErrorResponse> handleUserOwnership(UserOwnershipException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                409,
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                409,
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleExceptions(
//            Exception ex) {
//        ErrorResponse error = new ErrorResponse(
//                LocalDateTime.now(),
//                500,
//                "Internal Server Error",
//                "Ocorreu um erro inesperado"
//        );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(error);
//    }
}