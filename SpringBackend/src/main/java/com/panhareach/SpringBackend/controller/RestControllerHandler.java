package com.panhareach.SpringBackend.controller;


import com.panhareach.SpringBackend.exception.AlreadyExistException;
import com.panhareach.SpringBackend.exception.BadRequestException;
import com.panhareach.SpringBackend.exception.InternalServerErrorException;
import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.infrastructure.model.response.body.BaseBodyResponse;
import com.panhareach.SpringBackend.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), (short) 500);
        return ResponseEntity.status(500).body(err);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), (short) 409);
        return ResponseEntity.status(409).body(err);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), (short) 404);
        return ResponseEntity.status(404).body(err);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), (short) 400);
        return ResponseEntity.status(400).body(err);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<BaseBodyResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        return BaseBodyResponse.failed(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
