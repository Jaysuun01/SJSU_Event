package com.example.SJSU_Event.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    // Server Errors
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "Server error, please contact administrator"),
    _UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR(INTERNAL_SERVER_ERROR, 5001, "Server error, login is not required for this request"),
    _ASSIGNABLE_PARAMETER(BAD_REQUEST, 5002, "Authentication type is incorrect and cannot be assigned"),

    // General Request Errors
    _BAD_REQUEST(BAD_REQUEST, 4000, "Invalid request"),
    _UNAUTHORIZED(UNAUTHORIZED, 4001, "Login required"),
    _FORBIDDEN(FORBIDDEN, 4002, "Forbidden request"),

    // Member Errors
    MEMBER_NOT_FOUND(NOT_FOUND, 4050, "User not found"),
    MEMBER_WRONG_PASSWORD(BAD_REQUEST, 4051, "Incorrect password"),
    MEMBER_USERNAME_ALREADY_EXISTS(ALREADY_REPORTED, 4052, "Username already exists"),

    // Event Errors
    EVENT_NOT_FOUND(NOT_FOUND, 4100, "Event not found"),
    EVENT_ONLY_TOUCHED_BY_OWNER(BAD_REQUEST, 4101, "Event can only be modified by the host"),

    // Ticket Errors
    TICKET_NOT_FOUND(NOT_FOUND, 4150, "Ticket information not found"),
    TICKET_ONLY_CAN_BE_OPENED_BY_OWNER(BAD_REQUEST, 4151, "Ticket can only be viewed by the event host");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    @Override
    public Reason getReason() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}