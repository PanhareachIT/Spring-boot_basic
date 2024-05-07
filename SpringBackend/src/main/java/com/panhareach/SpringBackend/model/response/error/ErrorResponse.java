package com.panhareach.SpringBackend.model.response.error;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {
    private String message;
    private Short code;

    public ErrorResponse(String message, Short code) {
        this.message = message;
        this.code = code;
    }
}
