package start.ddd.chapter_5.section_3;

import start.ddd.chapter_4.section_3.Order;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@StaticMetamodel(Order.class)
public class Order_ {

    public static volatile SingularAttribute<Order, String > number;
    public static volatile SingularAttribute<Order, String > ordererId;
    public static volatile SingularAttribute<Order, LocalDateTime> orderDate;

}

/**
 * 정적 메타 모델 클래스는 대상 모델의 각 프로퍼티와 동일한 이름을 갖는 정적 필드를 정의한다.
 *
 */