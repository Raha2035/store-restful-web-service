package com.msho.store.rest.sebservice.storerestfulwebservice.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

    private final LocalDateTime timeStamp;

    private final String message;

    private final String details;

    public ErrorDetails(LocalDateTime timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
