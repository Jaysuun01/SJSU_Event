package com.example.SJSU_Event.domain.member.controller;

import com.example.SJSU_Event.domain.member.dto.LoginDto;
import com.example.SJSU_Event.domain.member.dto.MemberUpdateInfo;
import com.example.SJSU_Event.domain.member.dto.SignUpDto;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.service.MemberService;
import com.example.SJSU_Event.global.annotation.api.ApiErrorCodeExample;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import com.example.SJSU_Event.global.exception.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API", description = "Member API")
@ApiResponse(responseCode = "2000", description = "Success")
@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "SignUp 🔑", description = "Member Register(SignUp)")
    @ApiErrorCodeExample
    @CrossOrigin
    @PostMapping("/register/user")
    public ApiResponseDto<Long> registerUser(@RequestBody SignUpDto dto) {
        return ApiResponseDto.onSuccess(memberService.signUp(dto));
    }

    @Operation(summary = "LogIn", description = "get Member Info By Username & Password")
    @ApiErrorCodeExample
    @CrossOrigin
    @GetMapping("/login/user")
    public ApiResponseDto<Member> login(@RequestParam String username, @RequestParam String password) {
        LoginDto loginDto = LoginDto.builder()
                .username(username)
                .password(password)
                .build();
        return ApiResponseDto.onSuccess(memberService.login(loginDto));
    }

    @Operation(summary = "Update Information 🔑", description = "Update Information")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
    })
    @CrossOrigin
    @PutMapping("/{memberId}")
    public ApiResponseDto<Long> updateInfo(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateInfo memberUpdateInfo) {
        return ApiResponseDto.onSuccess(memberService.updateInfo(memberId, memberUpdateInfo));
    }

    @Operation(summary = "Delete Member 🔑", description = "Delete Member")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
    })
    @CrossOrigin
    @DeleteMapping("/{memberId}")
    public ApiResponseDto<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponseDto.onSuccess(null);
    }

    @Operation(summary = "Get Username 🔑", description = "Get Username")
    @ApiErrorCodeExample(value = {
            ErrorStatus.EVENT_NOT_FOUND,
    })
    @CrossOrigin
    @GetMapping("/{memberId}")
    public ApiResponseDto<Member> getByMemberId(@PathVariable Long memberId) {
        return ApiResponseDto.onSuccess(memberService.getByMemberId(memberId));
    }
}