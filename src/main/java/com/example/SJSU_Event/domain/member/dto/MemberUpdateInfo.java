package com.example.SJSU_Event.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdateInfo {
    private String username;
    private String name;
}
