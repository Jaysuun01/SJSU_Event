package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    Long userRegister(String name);

    Long adminRegister(String name);

    Long updateInfo(Long memberId, String name);

    void deleteMember(Long memberId);

    Member getByMemberId(Long memberId);
}
