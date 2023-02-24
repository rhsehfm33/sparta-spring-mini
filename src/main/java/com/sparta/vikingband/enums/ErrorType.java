package com.sparta.vikingband.enums;

public enum ErrorType {
    NONE(""),
    EXCEPTION("Exception error"),
    RUNTIME_EXCEPTION("Runtime Exception error"),
    ENTITY_NOT_FOUND_EXCEPTION("entity not found error"),
    VALIDATION_EXCEPTION("validation fail error"),
    JWT_EXCEPTION("token invalid error"),
    ILLEGAL_ARGUMENT_EXCEPTION("wrong argument error"),
    ACCESS_DENIED_EXCEPTION("don't have authority to do so"),
    AUTHENTICATION_EXCEPTION("authentication has failed");

    String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.description;
    }
}
