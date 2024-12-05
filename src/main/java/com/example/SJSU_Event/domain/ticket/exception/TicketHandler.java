package com.example.SJSU_Event.domain.ticket.exception;

import com.example.SJSU_Event.global.exception.GeneralException;
import com.example.SJSU_Event.global.exception.code.BaseCode;

public class TicketHandler extends GeneralException {
    public TicketHandler(BaseCode code) {
        super(code);
    }
}
