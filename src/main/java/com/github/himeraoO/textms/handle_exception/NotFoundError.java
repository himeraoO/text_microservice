package com.github.himeraoO.textms.handle_exception;

import lombok.Data;

@Data
public class NotFoundError {
    private String error;
    private Integer statusCode;
}
