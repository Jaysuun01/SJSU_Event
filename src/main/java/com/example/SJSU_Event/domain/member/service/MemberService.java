package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    Long userRegister(String name);

    Long adminRegister(String name);

    Long updateInfo(String username, String name);

    void deleteMember(String username);

    Member getByUsername(String username);
}
