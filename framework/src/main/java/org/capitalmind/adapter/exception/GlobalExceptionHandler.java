package org.capitalmind.adapter.exception;

import org.capitalmind.dto.response.ErrorResponse;
import org.capitalmind.exception.BadRequest;
import org.capitalmind.exception.InternalServerError;
import org.capitalmind.exception.InvalidData;
import org.capitalmind.exception.NotFound;
import org.capitalmind.exception.RequestTimeout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidData ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Data", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);  // 400 Bad Request
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);  // 404 Not Found
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequest ex) {
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);  // 400 Bad Request
    }

    @ExceptionHandler(RequestTimeout.class)
    public ResponseEntity<ErrorResponse> handleRequestTimeout(RequestTimeout ex) {
        ErrorResponse errorResponse = new ErrorResponse("Request Timeout", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);  // 408 Request Timeout
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerError ex) {
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
    }

   
}

