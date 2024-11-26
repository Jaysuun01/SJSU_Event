package com.example.SJSU_Event.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode{
    // 서버 오류
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "서버 에러, 관리자에게 문의 바랍니다."),
    _UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR(INTERNAL_SERVER_ERROR, 5001, "서버 에러, 로그인이 필요없는 요청입니다."),
    _ASSIGNABLE_PARAMETER(BAD_REQUEST, 5002, "인증타입이 잘못되어 할당이 불가능합니다."),

    // 일반적인 요청 오류
    _BAD_REQUEST(BAD_REQUEST, 4000, "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, 4001, "로그인이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, 4002, "금지된 요청입니다."),

    // 멤버 오류
    MEMBER_NOT_FOUND(NOT_FOUND, 4050, "찾을 수 없는 유저입니다."),
    // 이벤트 오류
    EVENT_NOT_FOUND(NOT_FOUND, 4100, "찾을 수 없는 이벤트입니다."),
    EVENT_ONLY_TOUCHED_BY_OWNER(BAD_REQUEST, 4101, "이벤트는 주최자만 변경가능합니다."),
    // 티켓 오류
    TICKET_NOT_FOUND(NOT_FOUND, 4150, "찾을 수 없는 티켓 정보입니다."),
    TICKET_ONLY_CAN_BE_OPENED_BY_OWNER(BAD_REQUEST, 4151, "티켓은 이벤트 주최자에 의해서만 열람가능합니다");

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
