
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


