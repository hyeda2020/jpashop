package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 로직이 많으므로 전체적으로는 readOnly = true 적용
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional // 단, 이렇게 별개로 Transactional 을 지정할 경우 이 메서드에 한해서는 이게 우선권을 가짐
    public Long join(Member member) {
        validateDuplicatedMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getUsername());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true) // 조회/읽기 에는 가급적 readOnly = true 를 써서 성능 최적화하기
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 하나 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
