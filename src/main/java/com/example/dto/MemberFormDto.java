package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 화면에서 넘어온 가입정보를 담은 dto
 */
@Getter
@Setter
public class MemberFormDto {
    private String name;
    private String email;
    private String password;
    private String address;
}
