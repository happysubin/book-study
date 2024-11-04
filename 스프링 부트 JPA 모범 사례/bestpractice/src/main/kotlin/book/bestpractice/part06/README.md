
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

## 항목 63. 트랜잭션 타임아웃 설정 및 롤백이 예상대로 작동하는지 확인하는 방법

스프링은 트랜잭션 타임아웃을 명시적으로 설정하기 위한 여러 방법을 지원한다.
가장 널리 사용되는 방법은 다음과 같은 간단한 서비스 메서드에서와 같이 @Transactional 애노테이션의 타임아웃 요소를 사용하는 것이다.

```java

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = 10)
public void newAuthor() {
    Author author = new Author();
    author.setAge(23);
    author.setGenre("Anthology");
    author.setName("Mark Janel");
    
    
    Thread.sleep(15_000);
    
    authorRepository.saveAndFlush(author);
}

```

위 코드는 현재 스레드가 트랜잭션 커밋을 15초 동안 지연하고 트랜잭션이 10 초 후에 타임아웃이 되기에 관련 예외 발생과 트랜잭션 롤백이 나타날 것으로 예상할 수 있다.
그러나 예상대로 작동하지 않는다. 대신 트랜잭션은 15초 후에 커밋된다.

다른 시도는 2개의 동시 트랜잭션을 사용하는 것으로, 트랜잭션 A는 트랜잭션 B가 타임아웃될 정도로 오랫동안 배타적 잠금을 유지하는 것이다.
이 방법도 가능하지만 더 간단한 방법이 있다.

RDBMS에 고유한 SQL SLEEP 기능을 사용하는 트랜잭션 서비스 메서드에 SQL 쿼리를 넣기만 하면 된다.
대부분의 RDBMS에는 SLEEP 기능이 있다.

SLEEP 함수는 지정된 시간 동안 현재 명령문을 일시 중지해 트랜잭션을 일시적으로 멈춘다.
트랜잭션 타임아웃보다 긴 시간 동안 트랜잭션을 일시 중지하면 트랜잭션이 만료되고 롤백돼야 한다.

다음과 같은 리포지터리는 타임아웃이 10초로 설정돼 있는 동안 현재 트랜잭션을 15초 동안 지연시키는 SELECT() 기반 쿼리를 정의하고 있다.

```java
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT SLEEP(15)", nativeQuery = true)
    void sleepQuery();
}


@Transactional(timeout = 10)
public void newAuthor() {
    Author author = new Author();
    author.setAge(23);
    author.setGenre("Anthology");
    author.setName("Mark Janel");


    authorRepository.sleepQuery(15_000);

    authorRepository.saveAndFlush(author);
}
```

#### 트랜잭션 및 쿼리 타임아웃 설정

@Transactional의 timeout 항목 지정은 메서드 또는 클래스 수준에서 트랜잭션 타임아웃을 설정하는 데 매우 편리한 방법이다.
또는 다음과 같이 application.properties의 spring.transaction.default-timeout 속성을 통해 전역 타임아웃을 명시적으로 설정할 수 있다.

```
spring.transaction.default-timeout=10
```

쿼리 수준에서는 2가지 힌트를 통해 타임아웃을 설정할 수도 있다.

#### 트랜잭션이 롤백됐는지 확인

트랜잭션이 시간 초과되면 롤백해야 하며, 특정 도구를 통해 데이터베이스 레벨에서 또는 애플리케이션 로그로 이를 확인할 수 있다.
먼저 다음과 같이 application.properties에 트랜잭션 로깅을 활성화해보자.

아래와 같이 트랜잭션 로깅을 활성화해보자.

```
logging.level.ROOT=INFO
logging.level.org.springframework.orm.jpa=DEBUG
logging.level.org.springframework.transaction=DEBUG
```

이제 만료된 트랜잭션은 다음과 같이 확인할 수 있다.

## 항목 64. 리포지터리 인터페이스에서 @Transactional을 사용하는 이유와 방법

일반적으로 데이터베이스 속도는 초당 트랜잭션 수로 표현되는 트랜잭션 처리량으로 주어진다.
이는 데이터베이스가 장기 실행 트랜잭션보다 많은 짧은 트랜잭션을 수용하도록 구축됨을 의미한다.
짧은 트랜잭션을 처리하고자 데이터 액세스 레이어 성능을 끌어 올리려면 이 항목에 제시되는 기술을 적용해보자

#### 인터페이스 리포지터리의 쿼리 메서드는 기본적으로 트랜잭션 콘텍스트에서 실행되는가?

결론부터 말하면 읽기 전용 쿼리에도 명시적 트랜잭션을 사용하는 것이 좋다.

```
또한 스프링은 사용자 정의 쿼리 메서드에 대해 기본 트랜잭션 콘텍스트를 제공하지 않는다.
반면 내장 쿼리 메서드(ex save(), findById(), delete())에는 문제가 없다.
이들은 확장된 내장 리포지터리 인터페이스를 상속하며 기본 트랜잭션 콘텍스트와 함께 제공된다.

```

대부분의 경우 트리거된 SQL문이 ACID 특성이 있는 트랜잭션에서 작업 단위로 실행된다는 가정하에 쿼리 메서드 호출을 포함하는 서비스 메서드를 구현한다.

그러나 분명히 이 가정은 아래 사례에는 유효하지 않다. 

```java
public void callFetchByNameAndDeleteByNeGenreMethods() {
    Author author = authorRepository.fetchByName("subin");
    authorRepository.deleteByNeGenre(author.getGenre());
}
```

AuthorRepository 쿼리 메서드에 대해 트랜잭션을 제공하지 않기 때문에 예외를 발생시킨다.

#### 그럼 단순히 서비스 메서드 수준에서 @Transactional을 추가하는 것이 옳은가?

위 코드를 다음과 같이 수정하면 예외가 발생하지 않는다.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional
public void callFetchByNameAndDeleteByNeGenreMethods() {
    Author author = authorRepository.fetchByName("subin");
    authorRepository.deleteByNeGenre(author.getGenre());
}
```

장기 실행 트랜잭션인 경우를 고려해보면 항상 서비스 메서드에 @Transactional을 붙이는 것은 옳지 않다.

#### 그렇다면 리포지터리 인터페이스로 @Transactional을 이동하자

개발자가 직접 정의한 쿼리 메서드의 경우 레포지토리에 @Transactional를 정의하면 트랜잭션 예외가 발생하지 않는다.
쿼리 메서드에 @Transactional를 정의해도 마찬가지로 예외가 발생하지 않는다.

#### 그러나 서비스 메서드에서 더 많은 쿼리 메서드를 호출하려면 어떻게 해야할까? ACID를 잃는가?

논리적 트랜잭션을 정의하는 여러 SQL문을 생성하는 여러 쿼리 메서드를 호출해야 할 가능성이 높다.
@Transactional이 없는 서비스 메서드에서 여러 쿼리 메서드를 호출하면 이 작업 묶음에 단위에 대한 ACID 특징이 손실된다.
따라서 서비스 메서드에도 @Transactinal을 추가해야 한다.

먼저 AuthorRepository 리포지토리 인터페이스를 구성하는 가장 좋은 방법을 살펴보자. 다음 조언을 따르자.

* 쿼리 메서드에 대해서도 @Transactional(readOnly = true)를 사용하는 것이 좋다. 
* 아무 설정도 없는 @Transactional을 조작하는 메서드에 추가하거나 해당 인터페이스에 다시 지정한느 것을 확실히 해야 한다.
* 인터페이스에 @Transactional(readOnly = true)를 정의하고 개별 메서드에 @Transactional를 정의하자.

그럼 제일 중요한 장기 트랜잭션은 어떡해야할까?

* 코드 리팩토링을 하고 구현을 재설계해 더 짧은 트랜잭션을 얻어야 한다.
* 참고로 하이버네이트 5.2.10 이상을 사용하면 데이터베이스 커넥션 지연 획득이 가능하다. 이건 위에 설정값이 존재한다.
* 일반적인 규칙으로, 쿼리 메서드 호출로 데이터베이스와 상호작용하지 않는 무거운 비즈니스로직을 중간에 처리하는 트랜잭션을 사용하지 않도록 노력해야 한다.
* 이로 인해 시간이 많이 걸리고 이해, 디버깅, 리팩토링, 리뷰가 어려운 장기 실행 트랜잭션과 복잡한 서비스 메서드가 될 수 있다.

#### 그럼 커넥션 획득을 지연하면 리포지터리 인터페이스에 @Transactional을 사용하지 않아도 되는가?

가능하면 커넥션 획득을 지연시키자.
그런 다음 대부분의 경우 리포지터리 인터페이스가 아닌 서비스 수준에서만 @Transactinal을 사용할 수 있다.
그러나 이는 읽기 전용 데이터베이스 작업을 하는 서비스 메서드에 @Transactional(readOnly = true) 추가를 꾸준히 잊어버리는 경향이 있음을 의미한다.

또한 트랜잭션의 ACID 설정이 필요 없다면 리포지토리 쿼리 메서드에만 @Transactional을 적용하는 것도 좋은 방법이다.

#### 간단하고 일반적인 3가지 시나리오

__데이터베이스와 상호작용하지 않는 코드에서 발생한 예외로 인한 서비스 메서드 롤백__

```java
public void foo() {
    // DML 문
    // 데이터베이스와 상호작용하지 않지만
    // RuntimeException이 발생하기 쉬운 작업 수행
}
```

데이터베이스와 상호작용하지 않는 강조된 코드 부분에서 런타임 예외가 발생하면 현재 트랜잭션을 롤백해야한다.
첫 번째 유혹은 이 서비스 메서드에 @Transactional을 지정하는 것이다.
이런 시나리오는 @Transactional(rollbackFor = Exception.class)를 사용하는 것이 체크 예외에 대해서도 일반적으로 사용된다.

그러나 @Transactional을 서비스 메서드에 추가하기로 결정하기 전에 다시 한번 생각해보는 것이 좋다.
다른 해결책이 있을지도 모른다.
예를 들어 동작에 영향을 주지 않고 작업 순서만 다음과 같이 변경할 수 있다.

```java
public void foo() {
    // 데이터베이스와 상호작용하지 않지만
    // RuntimeException이 발생하기 쉬운 작업 수행
    // DML문을 트리거하는 쿼리 메서드 호출
}
```

이제 이 서비스 메서드에는 @Transactional 애노테이션을 지정할 필요가 없다.
데이터베이스와 상호작용하지 않는 작업에서 RuntimeException이 발생해도 save()는 호출되지 않으므로 데이터베이스 호출을 하지 않는다.

더욱이 이런 작업에 시간이 많이 걸리는 경우 save() 메서드에 대해 열린 트랜잭션 기간에 영향을 주지 않는다.
최악의 시나리오에서는 작업 순서를 변경할수 없으며 작업은 시간이 많이 소요되는 것이다.
설상 가상으로 애플리케이션에서 많이 호출되는 메서드일 수 있다.
이런 상황에서는 서비스 메서드가 장기 실행 트랜잭션을 발생시키는데, 서비스 메서드에 @Transactional 애노테이션을 추가하지 않도록 해결 방법을 재설계해야 한다.

__전이와 @Transactional__

```java
public void fooAndBuzz() {
    Foo foo = new Foo();
    
    Buzz buzz1 = new Buzz();
    Buzz buzz2 = new Buzz();
    
    foo.addBuzz(buzz1);
    foo.addBuzz(buzz2);
    
    fooRepository.save(foo);
}
```

위와 같은 로직에서도 서비스 메서드에 @Transactional을 붙일 필요가 없다. 3개의 인서트문이 실행돼도 1개라도 실패하면 롤백된다.

__조회 > 수정 > 저장 및 중간의 장기 실행 작업__

```java
public void callSelectModifyAndSave() {
    Foo foo = fooRepository.findBy...(...); //단기 트랜잭션
    
    //foo 데이터를 사용하는 장기 실행 작업
    
    foo.setFooProperty(...);
    fooRepository.save(foo); //단기 트랜잭션
}
```

이런 경우는 서비스 메서드에 @Transactional을 거는 것보다 짧게 2개의 트랜잭션을 가져가는 것이 유리하다.
위 두 메서드가 모두 버전 기반 낙관적 잠금 또는 재시도 메커니즘에 의존할 수 있다. 이 메서드는 @Transactional 애노테이션이 없기 때문에 아래와 같이 @Retry를 적용하면 된다.

```java
import org.springframework.dao.OptimisticLockingFailureException;

@Retry(times = 10, on = OptimisticLockingFailureException.class)
public void callSelectModifyAndSave() {
    Foo foo = fooRepository.findBy...(...); //단기 트랜잭션

    //foo 데이터를 사용하는 장기 실행 작업

    foo.setFooProperty(...);
    fooRepository.save(foo); //단기 트랜잭션
}
```

위 방법이 장기 실행 트랜잭션보다 훨씬 낫다.