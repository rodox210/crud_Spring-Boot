package com.rodd331.crud.impl.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@lombok.Data
@Builder
public class ExceptionResponse {

    private String name;
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;


}