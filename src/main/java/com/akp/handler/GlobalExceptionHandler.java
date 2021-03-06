package com.akp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.akp.exception.EmptyShoppingCartException;
import com.akp.exception.InvalidProductIdException;
import com.akp.exception.NoProductFoundByRegionException;
import com.akp.exception.NotEnoughProductsInStockException;
import com.akp.exception.OrderNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Global exception handler
 *
 * @author Aashish Patel
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exception(final Throwable throwable) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        return new ResponseEntity<>(new ErrorResponse(new Exception("Exception during execution of request"), HttpStatus.UNAUTHORIZED.toString()), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = NotEnoughProductsInStockException.class)
    public ResponseEntity<ErrorResponse> notEnoughProductsInStockException(NotEnoughProductsInStockException exception) {
        logger.error("NotEnoughProductsInStockException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.INSUFFICIENT_STORAGE.toString()), HttpStatus.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException exception) {
        logger.error("AccessDeniedException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.UNAUTHORIZED.toString()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException exception) {
        logger.error("UsernameNotFoundException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.UNAUTHORIZED.toString()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidProductIdException.class)
    public ResponseEntity<ErrorResponse> invalidProductIdException(InvalidProductIdException exception) {
        logger.error("InvalidProductIdException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.NOT_FOUND.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> orderNotFoundException(OrderNotFoundException exception) {
        logger.error("OrderNotFoundException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.UNAUTHORIZED.toString()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyShoppingCartException.class)
    public ResponseEntity<ErrorResponse> emptyShoppingCartException(EmptyShoppingCartException exception) {
        logger.error("EmptyShoppingCartException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.BAD_REQUEST.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoProductFoundByRegionException.class)
    public ResponseEntity<ErrorResponse> noProductFoundByRegionException(NoProductFoundByRegionException exception) {
        logger.error("NoProductFoundByRegionException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.NO_CONTENT.toString()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> authenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception) {
        logger.error("AuthenticationCredentialsNotFoundException", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception, HttpStatus.UNAUTHORIZED.toString()), HttpStatus.UNAUTHORIZED);
    }

    @AllArgsConstructor
    @Data
    class ErrorResponse {
        String errorcode;
        String errorMessage;
        ErrorResponse(Throwable exception, String errorcode) {
            errorMessage = exception.getMessage();
            this.errorcode = errorcode;
        }
    }
}
