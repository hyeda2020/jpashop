package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // 신규 아이템이면
            em.persist(item); // 신규 등록
        } else {
            /**
             * 이때, 파라미터 item 은 영속성으로 관리되지는 않으며,
             * 리턴된 mergeItem 가 영속성으로 관리됨.
             * 다만, 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만
             * 병합(merge)을 사용하면 모든 속성이 변경되기 때문에(병합은 모든 필드를 교체)
             * 병합시 값이 없으면 null 로 업데이트 할 위험도 있음.
             * 따라서, 결론적으로 실무에서는 병합을 쓰지 말고 변경 감지 기능을 쓰자!
             */
            Item mergeItem = em.merge(item); // 이미 등록되어 있는 아이템이면 업데이트

            /**
             * 아래가 변경 감지 사용 예시로,
             * 트랜잭션으로 묶여있기 때문에
             * findItem 데이터를 setter로 변경하면
             * JPA가 변경감지를 적용하여 자동으로 해당 필드를 update하고
             * 해당 메서드 호출이 끝나면 commit 해줌.
             * */
            /*
                @Transactional
                void update(Item itemParam) {
                    Item findItem = em.find(Item.class, itemParam.getId());
                    findItem.setPrice(itemParam.getPrice());
                }
             */

            /**
             * 더 좋은 방법은 setter를 호출해서 일일이 필드를 변경하기 보다는
             * 트랜잭션이 있는 서비스 계층에 별도 update 메서드를 만들어서
             * 식별자( id )와 변경할 데이터(파라미터 or dto)를 명확하게 전달하여 수정하는 것이 바람직.
             *
             * 또한, 컨트롤러에서 직접 엔티티를 생성해서 사용하지 않고 서비스 계층에서
             * 직접 레포지토리를 통해 엔티티를 찾아서 트랜잭션 범위 내에서 활용하는게 유지보수 관점에서 바람직.
             * (영속성 컨텍스트를 최대한 유지하면서 변경 감지 기능 사용 가능)
             */
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
