package com.example.SJSU_Event.domain.member.entity;

import com.example.SJSU_Event.domain.auditing.entity.BaseTimeEntity;
import com.example.SJSU_Event.domain.member.dto.SignUpDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Role role;

    private String name;

    public static Member of(SignUpDto signUpDto) {
        return Member.builder()
                .name(signUpDto.getName())
                .role(signUpDto.getRole())
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .build();
    }

    public void convertRole(Role role) {
        this.role =  role;
    }

    public void modifyInfo(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
}