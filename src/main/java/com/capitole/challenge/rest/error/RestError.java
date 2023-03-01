package com.capitole.challenge.rest.error;

import com.capitole.challenge.model.Error;

public class RestError extends Error {
    public RestError(int statusCode, String message) {
        super();
        this.code(statusCode);
        this.message(message);
    }
}
