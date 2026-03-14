package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @Rollback(false) // 테스트 코드에서 Transactional은 테스트가 끝나면 강제롤백하므로 롤백 설정 해제
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertThat(savedId).isEqualTo(member.getId());
    }

    @Test
    @Rollback(false)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () ->
                memberService.join(member2));
    }
}
