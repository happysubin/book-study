
# chapter 07. 식별자


## 항목 65. MySQL에서 하이버네이트 5 AUTO 생성자 타입을 피해야하는 이유

```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class AuthorBad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
```

MySQL과 하이버네이트 5에서 GenerationType.AUTO 생성자 타입은 테이블 생성자를 사용하고, 이로 인해 성능 저하가 발생한다.
Table 생성자 타입은 확장성이 없으며 단일 데이터베이스 커넥션에서 IDENTITY와 SEQUENCE 생성자 타입보다 훨씬 느리다.

__일반적인 규칙으로는 항상 Table 생성자를 사용하지 말아야 한다.__

MySQL을 사용하면 명시적으로 GenerationType.IDENTITY를 사용하는 것을 추천한다.
아니면 다음과 같이 native 생성자 타입을 적용할 수 있다.

```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
public class AuthorBad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
}
```