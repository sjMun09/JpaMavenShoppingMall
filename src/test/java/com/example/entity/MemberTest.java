package com.example.entity;


import com.example.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    //@Autowired
    // 버전 6.0부터 Hibernate는 javax 대신 jakarta.persistence를 사용합니다. 가져오기를 변경하고 @PersistenceContext 주석을 사용
    @PersistenceContext // https://stackoverflow.com/questions/72956975/could-not-autowire-no-beans-of-entitymanager-type-found
    EntityManager entityManager;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "홍길동", roles = "USER")
    public void auditingTest() {
        Member newMember = new Member();
        memberRepository.save(newMember);

        entityManager.flush();
        entityManager.clear();

        Member member = memberRepository.findById(newMember.getId())
                .orElseThrow(EntityExistsException::new);

        System.out.println("create time : " + member.getCreateBy());
        System.out.println("register time : " + member.getRegTime());
        System.out.println("update time : " + member.getUpdateTime());
        System.out.println("modify time : " + member.getModifiedBy());

    }
}
