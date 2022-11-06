package start.ddd.chapter_5.section_3;

import org.springframework.data.jpa.domain.Specification;
import start.ddd.chapter_4.section_3.Order;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class OrderSpecs {

    public static Specification<Order> ordererId(String ordererId){
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.<String>get("ordererId"), ordererId);
    }

    public static Specification<Order> orderDateBetween(
            LocalDateTime from, LocalDateTime to
    ){
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.between(root.get(Order_.orderDate), from, to);
    }

    public static void main(String[] args) {
        LocalDateTime from = LocalDateTime.of(1, 1, 1, 1, 1, 1);
        LocalDateTime to = LocalDateTime.now();

        Specification<Order> orderSpecification = OrderSpecs.orderDateBetween(from, to); //이렇게 사용 가능
    }
}
