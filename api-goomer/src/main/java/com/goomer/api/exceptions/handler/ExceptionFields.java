package com.goomer.api.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ExceptionFields extends ExceptionResponse {
    private final String fields;
    private final String fieldsMessage;

    public ExceptionFields(String title, int status, String exception, String detail, LocalDateTime timestamp, String fields, String fieldsMessage) {
        super(title, status, exception, detail, timestamp);
        this.fields = fields;
        this.fieldsMessage = fieldsMessage;
    }
}
