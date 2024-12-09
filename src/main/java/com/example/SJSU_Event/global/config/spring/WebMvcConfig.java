package com.example.SJSU_Event.global.config.spring;

import com.example.SJSU_Event.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final MemberService memberService;
    private final long MAX_AGE_SECS = 3600;

    //CORS setting
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}