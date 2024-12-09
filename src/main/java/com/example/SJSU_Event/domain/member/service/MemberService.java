package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.dto.LoginDto;
import com.example.SJSU_Event.domain.member.dto.MemberUpdateInfo;
import com.example.SJSU_Event.domain.member.dto.SignUpDto;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

    Long signUp(SignUpDto signUpDto);

    Member login(LoginDto loginDto);

    Long updateInfo(Long memberId, MemberUpdateInfo memberUpdateInfo);

    void deleteMember(Long memberId);

    Member getByMemberId(Long memberId);
}
