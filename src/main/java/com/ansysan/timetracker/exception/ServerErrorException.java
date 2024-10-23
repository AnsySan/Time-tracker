package com.ansysan.timetracker.exception;

public class ServerErrorException extends RuntimeException{

    public ServerErrorException(String message) {
        super(message);
    }
}