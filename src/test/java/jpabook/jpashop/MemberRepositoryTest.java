package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional // 테스트 코드에 있으면 테스트가 끝나고 자동으로 롤백함
//    @Rollback(false) // 만약 테스트 코드에서 자동 롤백 옵션을 끄고 싶으면 이 어노테이션에 옵션을 'false'로 지정
    public void testMember() {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 기존 객체와 조회 객체는 동일 객체(같은 트랜잭션 안에서는 식별자가 같으면 같은 엔티티)
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}