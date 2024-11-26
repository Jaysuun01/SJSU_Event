package com.example.SJSU_Event.domain.event.exception;

import com.example.SJSU_Event.global.exception.GeneralException;
import com.example.SJSU_Event.global.exception.code.BaseCode;

public class EventHandler extends GeneralException {
    public EventHandler(BaseCode code) {
        super(code);
    }
}
