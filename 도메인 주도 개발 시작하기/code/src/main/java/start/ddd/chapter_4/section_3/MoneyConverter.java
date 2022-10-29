package start.ddd.chapter_4.section_3;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Integer value) {
        return value == null ? null : new Money(value);
    }
}

/**
 * AttributeConverter 인터페이스를 구현한 클래스는 @Converter 애너테이션을 적용한다.
 * autoApply 속성 값을 true로 하면 모델에 출연하는 모든 Money 타입의 프로퍼티에 대해 MoneyConvereter를 자동으로 적용한다.
 * 예를 들어 Order의 totalAmounts 프로퍼티는 Money 타입인데 이 프로퍼티를 DB의 total_amounts 칼럼에 매핑할 때 MoneyConverter를 사용한다
 * autoApply 속성 값은 기본적으로 false 인데 프로퍼티 값을 지정할 때 사용할 컨버터를 직접 정해야한다.
 */