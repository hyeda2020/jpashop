package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    /**
     * 엔티티는 순수하게 DB와 관련되게끔만 설계해야 됨.
     * 화면과 관련된건 별도 Form 클래스를 생성하여 활용
     * 또한, 엔티티는 절대로 화면에 노출되면 안됨
     * (보안상 이슈 및 엔티티 스펙이 바뀔때마다 화면 필드 및 API 구조도 같이 바뀌어야됨.)
     */

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
