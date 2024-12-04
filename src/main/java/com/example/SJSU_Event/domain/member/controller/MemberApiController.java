package com.example.SJSU_Event.domain.member.controller;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.service.MemberService;
import com.example.SJSU_Event.global.exception.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/register/user")
    public ApiResponseDto<Long> registerUser(@RequestParam String name) {
        return ApiResponseDto.onSuccess(memberService.userRegister(name));
    }

    @PostMapping("/register/admin")
    public ApiResponseDto<Long> registerAdmin(@RequestParam String name) {
        return ApiResponseDto.onSuccess(memberService.adminRegister(name));
    }

    @PutMapping("/{username}")
    public ApiResponseDto<Long> updateInfo(
            @PathVariable Long memberId,
            @RequestParam String name) {
        return ApiResponseDto.onSuccess(memberService.updateInfo(memberId, name));
    }

    @DeleteMapping("/{username}")
    public ApiResponseDto<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponseDto.onSuccess(null);
    }

    @GetMapping("/{username}")
    public ApiResponseDto<Member> getByUsername(@PathVariable Long memberId) {
        return ApiResponseDto.onSuccess(memberService.getByUsername(memberId));
    }
}