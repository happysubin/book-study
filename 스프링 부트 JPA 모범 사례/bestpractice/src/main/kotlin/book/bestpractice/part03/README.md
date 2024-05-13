
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