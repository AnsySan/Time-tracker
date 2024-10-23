package com.ansysan.timetracker.controller;

import com.ansysan.timetracker.entity.ErrorResponse;
import com.ansysan.timetracker.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
@Slf4j
public class ControllerErrorHandler {

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataValidationException(DataValidationException e, HttpServletRequest request) {
        log.error("Data validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerEmailNotFoundException(ServerErrorException e, HttpServletRequest request) {
        log.error("Email validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerEntityNotFoundException(ServerErrorException e, HttpServletRequest request) {
        log.error("Entity validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("Not Found validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerServerErrorException(ServerErrorException e, HttpServletRequest request) {
        log.error("Server validation error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerUsernameAlreadyExistsException(UsernameAlreadyExistsException e, HttpServletRequest request) {
        log.error("Username not hound error: {}", e.getMessage());
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(Exception e, HttpServletRequest request, HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .status(status)
                .message(e.getMessage())
                .build();
    }
}
