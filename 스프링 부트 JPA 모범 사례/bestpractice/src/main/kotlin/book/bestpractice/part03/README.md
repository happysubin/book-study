
# parrt 03. 페치

## 항목 21: 다이렉트 페치 사용 방법

다이렉트 페칭 또는 ID로 가져오기는 식별자를 알고 있고 지연 연관관계가 현재 영속성 컨텍스트에서  필요하지 않을 때에 엔티티를 가져오는 바람직한 방법이다.

글로벌 페치 로딩 전략을 선택하고 필요한 곳만 페치 조인으로 튜닝을 하는 것이 좋다.

### 스프링 데이터를통한 다이렉트 페치

```java

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

Optional<Author> author = authorRepository.findById(1L);
```

### EntityManager를 통한 다이렉트 페치

```java

Optional.ofNullable(entityManager.find(clazz, id));
```

### 하이버네이트 Session을 통한 다이렉트 패치(엔티티매니저 구현체)

```java
Session session = entityManager.unwrap(Session.class)
Optional.ofNullable(session.find(clazz, id));
```

### 다이렉트 페치 및 세션 수준 반복 읽기

왜 JPA가 지정된 ID를 갖는 엔티티를 찾고자 영속성 콘텍스트를 먼저 확인할까?
답은 하이버네이트가 __세션 수준 반복 읽기__ 를 보장하기 위해서다.
이는 처음 가져온 엔티티는 영속성 컨텍스트에 캐시되고, 동일한 엔티티의 계속되는 가져오기는 영속성 컨텍스트에서 수행되는 것을 의미한다.
달리 말하자면 세션 수준 반복 읽기는 동시 쓰기 처리에서 __업데이트 손실__ 을 방지한다.

영속성 컨텍스트에 캐시된 엔티티를 JPQL을 직접적으로 사용해 조회하지 않는 이상 데이터베이스에서 데이터를 가져오지 않고 영속성 컨텍스트에서 가져온다.

```java
@Transactional
fun fetchTest(id: Long) {
    val bookReview = em.find(BookReview::class.java, id)
    bookReview.email = "changeeeee@email.com"
    println("bookReview = ${bookReview}")
    val bookReview2 = bookReviewRepository.fetchById(id) ?: RuntimeException() //JPQL을 호출하므로 이게 플러쉬가 된다.
    println("bookReview = ${bookReview2}")
    //플러쉬가 행해지므로 세션 수준 반복 읽기가 가능한것이다.
}
```

지금까지 하이버네이트의 세션 수준 반복 읽기가 JPQL 또는 네이티브 SQL로 표현된 엔티티쿼리에서 예상대로 작동한다는 점을 확인했다.

JPQL 쿼리 프로젝션과 네이티브 SQL 쿼리 프로젝션에서는 세션 수준 반복 읽기가 작동하지 않는다. (오...)

이런 종류의 쿼리는 항상 최신 데이터베이스 상태를 로드한다. __하지만 트랜잭션 격리 수준을 REPETABLE_READ로 변경하면 프로젝션 모두 세션 수준 반복 읽기가 작동한다.__ 


## 항목 22: 미래 영속성 컨텍스트에서 데이터베이스 변경 사항 전파를 위한 읽기 전용 엔티티의 사용 이유

어떤 엔티티에서 여러 속성을 가지고 있다. 비즈니스 로직에서 엔티티를 로드하고 수정하고 다시 저장한다.
단일트랜잭션으로 처리할 필요 없이 2개의 서로 다른 트랜잭션에서 수행한다.

```java
import jakarta.transaction.Transactional;

@Transactional
public Author fetchAuthorReadWriteMode(String name) {
    Author author = authorRepository.findByName(name);
    return author;
}
```

위 코드에서 가져온 엔티티는 메서드 내에서 수정되지 않았다.
위 엔티티 상태는 MANAGED이며 하이드레이티드 상태도 갖고 있다. 

> 하이드레이티드 상태는 캐시상에 엔티티를 객체가 아닌 Object[]으로 보관한 상태를 말한다.
> 개별 속성이 Object로 분리돼 있기 때문에 디스어셈블드 상태라고도 한다.

이 방식은 적어도 다음과 같은 2가지 단점을 갖는다.

* 하이버네이트는 엔티티 변경 사항을 데이터베이스로 전파할 준비가 돼있으므로 메모리에 하이드레이트 상태를 유지한다.
* 플러시 시점에 하이버네이트는 수정 처리를 위해 엔티티를 스캔하는데 스켄에는 이 엔티티도 포함된다.

성능 페널티는 메모리와 CPU에 반영된다.
불필요한 하이드레이티드 상태를 저장하면 메모리를 더 소모하고, 플러시 시점에 엔티티를 스캔하고, 가비지 컬렉터가 이를 수집해 CPU 리소스가 소모된다.
읽기 전용 모드로 엔티티를 가져와 이와 같은 단점을 피해야 한다.

```java
@Transactional(readOnly = true)
public Author fetchAuthorReadMode(String name) {
    Author author = authorRepository.findByName(name);
    return author;
}
```

위 코드의 엔티티는 READ_ONLY 상태이며 하이드레이티드 상태는 무시됐다.
자동 플러시 시간도 없고 더티 체킹도 적용되지 않는다. 메모리 소비도 없고 CPU도 소비하지 않는다.

아래는 수정 코드다
```java
//분리된 상태에서 읽기 전용 엔티티 수정
Author authorRO = bookstoreService.fetchAuthorReadOnlyMode();
authorRO.setAge(authorRO.getAge() + 1);
bookstoreService.updateAuthor(authorRO)

//엔티티 병합 
@Transactional
public void updateAuthor(Author author) {
    //내부적으로 merge() 호출
    authorRepository.save(author);
}
```

위 코드는 select와 update로 구현된다.

위 같은 로직은 웹 애플리케이션에서 일반적으로 발생하며 HTTP Long CONVERSATION으로 알려져있다.
일반적으로 웹 애플리케이션에서 이런 종류의 시나리오에는 2개 이상의 HTTP 요청이 필요하다.

특히 이 경우 첫 번째 요청은 저자 프로필을 로드하고 두 번째 요청은 프로필 변경 사항을 전달한다.
HTTP 요청 사이에는 저자가 생각하는 시간이 있는데, 이에 대해선 추후에 더 살펴본다.

아마도 비관적 락, 낙관적 락에 대해 얘기하지 않을까..? (추측)


## 항목 23: 하이버네이트 Bytecode Enhancement를 통한 엔터티 속성 지연 로딩 방법


기본적으로 엔티티 속성들은 즉시 로드되기 때문에 용량이 많은 바이트 데이터(예: 이미지) 등은 사용하지 않는다면 엔티티를 로드할때마다 가져오는 것은 제거돼야 할 성능 저하 요소다.
위 문제를 해결하려면 속성 지연 로딩을 사용하면 된다.

속성 지연을 사용하려면 몇 가지 단계를 따라야 한다.

1. 하이버네이트 Bytecode Enhancement 플러그인을 추가해야한다.
2. enableLazyInitialization 설정을 통해 지연 초기화를 활성화해 엔티티 클래스의 바이트 코드를 계측하도록 하이버네이트에게 지시한다.
3. build.gradle에 아래와 같은 설정을 추가한다.
4. @Basic(fetch = FetchType.LAZY)로 지연로딩돼야 하는 엔티티 속성에 어노테이션을 지정한다.

```
hibernate {
	enhancement {
		enableLazyInitialization = true  //속성 지연 로딩
		enableDirtyTracking = true //변경 추적
		enableAssociationManagement = true //양방향 연관관계 동기화
		enableExtendedEnhancement = false 
	}
}
```

위와 같은 4단계를 거치고 엔티티를 조회하면 지연로딩을 설정한 컬럼은 가져오지 않는 것을 확인할 수 있다.

### 속성 지연 로딩과 N+1

N + 1은 필요 예상보다 더 많은 SQL문이 시행되면서 발생하는 성능 저하 문제를 나타낸다.
즉 필요한 것보다 더 많은 데이터베이스 호출을 수행하면 CPU, RAM 메모리, 디비 커넥션등과 같은 리소스가 소비된다.

N + 1을 회피하려면 서프 엔티티 기술을 활용하거나 DTO로 지연 로딩 속성을 명시적으로 처리하는 SQL SELECT을 트리거해 N + 1 성능 저하를 피할 수 있다.

```java

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

public interface AuthorDto {
    String getName();

    byte[] getAvatar();
}

@Transactional(readOnly = true)
@Query("SELECT a.name AS name, a.avatar AS avatar FROM Author a WHERE a.age >= ?1")
List<AuthorDto> findDtoByAgeGreaterThanEqual(int age);

```

보통 OSIV를 비활성화화면 지연 초기화 예외가 발생할 수 있다.
그래도 OSIV는 비활성화하는 편이 좋다.

그래서 제일 간단한 해결 방법은 직렬화 이전에 기본 값을 명시적으로 정하거나, 트랜잭션 안에서 데이터를 로드하는 것이다.
또는 @JsonInclude(Include.NON_DEFAULT) 설정을 주면 직렬하를 생략한다. 책에서 나온 방법은 @JsonFilter를 활용하는 법도 있다.

역시 조회는 그냥 프로젝션을 사용하는 것이 젤 맘 편한 것 같다.

## 항목 24: 서브 엔티티를 통한 엔티티 속성 지연 로딩 방법

항목 23에 대한 다른 해결 방법을 제시한다.

id, age, name, genre는 즉시 로딩하고 avatar는 지연 방식으로 처리하는 것을 목적으로 한다.
이 접근 방법은 엔티티를 서브엔티티로 분리하는 것을 기반으로 한다.

소스 코드 chapter 24를 참고.

AuthorShallow는 id, age, name, genre를 즉시 가져오는 반면 AuthorDeep는 이 4가지 속성과 avatar를 추가로 가져온다.

__결론적으로 avatar는 필요에 따라 불러 올 수 있다.__

### 결론

* 하이버네이트는 지연 로딩 속성을 지원하지만 이를 위해선 Bytecode Enhancement가 필요하고 OSIV와 Jackson 직렬화 문제를 처리해야 한다.
* 반면 서브 엔티티를 사용하는 것은 Bytecode Enhancement가 필요하지 않고 여러 문제가 발생하지 않기 때문에 더 나은 대안이 될 수 있다.

개인적인 결론은 프로젝션을 사용하자..!!!!!!