package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
// @Setter // 실무에서는 가급적 @Setter 를 사용하지 말자!
public class Address {

    String city;
    String street;
    String zipcode;

    // JPA 스펙상 엔티티나 임베디드 타입은
    // public/protected 로 선언된 기본 생성자가 필요하여 선언만 해둠.(실제 사용하지 않기에 protected 로 선언)
    protected  Address() {
    }

    // @Setter 대신에 생성자에 값을 초기화해서 변경 불가능 클래스로 만듦.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
