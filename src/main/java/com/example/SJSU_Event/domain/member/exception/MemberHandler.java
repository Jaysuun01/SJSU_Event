package com.example.SJSU_Event.domain.member.exception;

import com.example.SJSU_Event.global.exception.GeneralException;
import com.example.SJSU_Event.global.exception.code.BaseCode;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseCode code) {
        super(code);
    }
}
