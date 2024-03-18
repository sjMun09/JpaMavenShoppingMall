package com.example.entity;

import com.example.constant.Role;
import com.example.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Table(name = "member")
@ToString
public class Member extends BaseEntity{ // Auditing 기능 적용.
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true) //1
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) //2
    private Role role;

    // MemberServiceTEST 해볼 예정임.
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) { //3
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword()); //4
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
