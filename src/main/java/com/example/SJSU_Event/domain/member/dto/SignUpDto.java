package com.example.SJSU_Event.domain.member.dto;

import com.example.SJSU_Event.domain.member.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDto {
    private String username;
    private String password;
    private String name;
    private Role role;
}
