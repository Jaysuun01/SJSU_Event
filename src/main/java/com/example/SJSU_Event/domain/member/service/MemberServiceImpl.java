package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Transactional
    @Override
    public Long register(String username, String name, Role role) {
        Member member = Member.of(username, name, role);
        member.convertRole(Role.USER);
        return memberRepository.save(member).getId();
    }

    @Transactional
    @Override
    public Long updateInfo(String username, String name) {
        return 0L;
    }

    @Transactional
    @Override
    public void deleteMember(String username) {

    }

    @Transactional(readOnly = true)
    @Override
    public Member getByUsername(String username) {
        return null;
    }
}
