
## 항목 60. 실제 필요 시점까지 커넥션 획득 지연 방법

하이버네이트 5.2.10부터 데이터베이스 커넥션 획득을 실제 필요할 때까지 지연시킬 수 있다.

Resource-local(단일 데이터소스)의 경우 하이버네이트는 트랜잭션이 시작된 직후 바로 JDBC 트랜잭션의 데이터베이스 커넥션을 획득한다.

```
Resource-local에서는 하이버네이트가 JDB Connection 자동 커밋(AUTO-commit) 상태를 확인해야하므로 데이터베이스 커넥션을 즉시 획득한다.
자동 커밋이 true라면 하이버네이트는 이를 비활성화한다.
```

실제로 데이터베이스 커넥션은 현재 트랜잭션의 첫 번째 JDBC 구문이 실행될때까지 애플리케이션에 쓸모가 없다.
이 시간 동안 사용되지 않는 데이터베이스 커넥션을 유지하면 첫 JDBC 구문 이전에 많은 또는 시간 소모적인 작업이 있는 경우 큰 영향을 미치는 성능 저하가 발생할 수 있다.

이 성능 저하를 방지하고자 하이버네이트에게 자동 커밋이 비활성화됐다고 알릴 수 있으므로 확인 작업이 필요치 않게 된다.
이에 대해서는 다음 2단계를 따른다.

* 자동 커밋을 끈다. 예를 들어 커넥션 풀의 setAutoCommit(boolean commit) 타입 메서드를 확인하고 이를 false로 설정한다.
* 하이버네이트 속성인 hibernate.connection.provider_disables_autocommit을 true로 설정한다.

기본적으로 스프링 부트는 HikariCP를 사용하며 application.properties의 spring.datasource.hikari.auto-commit 속성을 통해 자동 커밋을 끌 수 있다.

따라서 다음과 같은 2개 설정을 application.properties에 추가한다.

```
spring.datasource.hikari.auto-commit=false
spring.jpa.properties.hibernate.connection.provider_disables_autocommit=true
```

일반적인 규칙으로 resource-local JPA 트랜잭션의 경우 커넥션 풀을 구성해 자동 커밋을 비활성화하고 hibernate.connection.provider_disables_autocmmit을 true로
설정하는 것이 좋다. 따라서 모든 resource-local을 사용하는 애플리케이션에서 해당 설정을 사용하자.

## 항목 61. @Transactional(readOnly = true)의 실제 작동 방식

Author 엔티티가 id, age, name, genre 필드를 갖고 있다 가정한다.

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findFirstByGenre(String genre);
}

public void fetchAuthor() {
    Author author = authorRepository.findFirstByGenre("Anthology");
}
```

현재 위 코드에는 트랜잭션 콘텍스트가 없다.

findFirstByGenre() 메서드는 트랜잭션 콘텍스트로 지정돼야 하며, 따라서 @Transactional 적용을 고려해야 한다.

@Transactional을 통해 데이터베이스 트랜잭션 경계를 명시적으로 구분하고 전체 트랜잭션 기간 동안 하나의 데이터베이스 커넥션을 사용해야 한다.
즉, 모든 SQL문이 하나의 격리 커넥션을 사용하고 모두 동일한 영속성 컨텍스트 범위에서 실행돼야 한다.

일반적으로 JPA는 읽기 연산에 트랜잭션을 요구하지 않지만, 이는 다음을 의미한다.

* 자동 커밋 모드가 데이터 액세스 동작을 제어하도록 허용한다.
* 일반적으로 auto-commit이 true로 설정되면 각 SQL문은 분리된 물리적 데이터베이스 트랜잭션으로 실행돼야 하는데, 각 명령문마다 다른 커넥션을 의미할 수 있다. 각 SQL문은 실행 즉시 자동으로 커밋된다.
* 트랜잭션 격리 수준을 명시적으로 설정하면 예기치 않은 동작이 발생할 수 있다.
* auto-commit을 true로 설정하는 것은 단일 읽기 전용 SQL문을 실행하는 경우에만 의미가 있지만 큰 이점은 없다. 따라서 이 경우에도 명시적 트랜잭션에 의존하는 것이 좋다.

그럼 @Transactional을 추가할 때 readOnly를 false로 해야 할까? 아니면 true로 설정해야 할까?

이 설정에 따라 엔티티는 읽기-쓰기 모드 또는 읽기 전용 모드가 된다.

영속성 엔티티를 로드하는 것은 하이드레이티드 상태 또는 로드된 상태라고 하는 방법을 통해 하이버네이트에 의해 수행된다. 
이 하이드레이션은 가져온 데이터베이스 결과 세트를 Object[]로 구체화하는 프로세스이고, 엔티티는 영속성 콘텍스트에서 구체화 된다.
다음에 발생되는 사항은 읽기 모드에 따라 다음과 같이 다르다.

__읽기-쓰는 모드__

이 모드에서 영속성 콘텍스트에서 엔티티와 하이드레이트 상태 모두 사용할 수 있다.
영속성 콘텍스트 수명 동안 또는 엔티티가 분리될 때 까지 사용할 수 있다. 
하이드레이티드 상태는 더티 체킹 메커니즘, 버전 없는 낙관적 잠금 메커니즘, 2차 캐시에 필요하다. 
더티 체킹 메커니즘은 플러시 시점에 하이드레이티드 상태를 활용한다.
단순히 현재 엔티티 상태를 해당 하이드레이티드 상태와 비교하고 동일하지 않으면 하이버네이트는 적절한 UPDATE문을 트리거한다.
버전 없는 낙관적 잠금 메커니즘도 하이드레이티드 상태를 활용해 필터링을 위한 WHERE 절을 구성하고 ,2차 캐시는 분리된 하이드레이티드 상태를 활용해 캐시 항목을 표현한다.
읽기-쓰기 모드에서는 엔티티가 MANAGED 상태를 갖는다.

__읽기-전용 모드__

이 모드에서는 하이드레이티드 상태가 메모리에서 제거되고 엔티티만 영속성 컨텍스트에 유지된다.
분명히 이는 자동 더티 체킹이나 버전 없는 낙관적 잠금 메커니즘이 비활성화됨을 의미한다.
읽기-전용 모드에서 엔티티는 READ_ONLY 상태이며, 스프링 부트가 플러시 모드를 MANUAL로 설정하기 때문에 자동 플러시는 없다.

readOnly가 false라면 하이드레이티드/로드된 상태는 영속성 컨텍스트에 보관되며 더티 체킹 메커니즘은 플러시 시간에 이를 사용해 엔티티를 업데이트한다.
가져온 엔티티 상태는 MANAGED다. 

readOnly가 true라면 엔티티를 가져온 후 하이드레이티드/로드된 상태가 즉시 삭제된다.
가져온 엔티티는 READ_ONLY 상태며 자동 플러시가 비활성화돼 있다.
명시적으로 flush()를 호출해 강제로 플러시하더라도 더티 체킹 메커니즘은 비활성화돼 있어 사용되지 않는다.

```
읽기 전용 데이터에 대한 readOnly=true 설정은 하이드레이티드.로드된 상태가 폐기되기 때문에 좋은 성능 최적화가 된다.
이를 통해 스프링은 데이터 액세스 레이어 작업을 최적화할 수 있따.
그럼에도 데이터 수정 계획이 없다면 DTO(스프링 프로젝션)를 통해 읽기 전용 데이터로 가져오는 것이 더 나은 방법이다.
```


## 항목 62. 스프링이 @Transactional을 무시하는 이유

* @Transactional이 private, protected, package-protected 메서드에 추가되면 무시된다.
* @Transactional이 호출된 동일한 클래스에 정의된 메서드에 추가됐다. -> 내부 함수 호출로 인해 프록시를 호출하는게 아니여서 트랜잭션이 무시된다.

