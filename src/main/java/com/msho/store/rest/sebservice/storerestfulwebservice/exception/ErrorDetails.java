package com.msho.store.rest.sebservice.storerestfulwebservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorDetails {

    private final LocalDateTime timeStamp;
    private final String message;
    private final String details;

}
