package start.ddd.chapter_5.section_5;

import org.springframework.data.jpa.domain.Specification;
import start.ddd.chapter_4.section_3.Order;
import start.ddd.chapter_5.section_3.OrderSpecs;

import java.time.LocalDateTime;

public class Application {

    public static void main(String[] args) {
        Specification<Order> orderSpecification = OrderSpecs.ordererId("user1");
        Specification<Order> orderSpecification1 =
                OrderSpecs.orderDateBetween(LocalDateTime.of(2022, 1, 1, 0, 0, 0,1 ), LocalDateTime.of(2022, 1, 2, 0, 0, 0));

        orderSpecification1.and(orderSpecification1); //조합가능

        Specification.not(OrderSpecs.ordererId("user1")); // not 메서드
    }
}
