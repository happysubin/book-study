
# 1장 연관관계

## 항목 1. @OneToMany 연관관계를 효과적으로 구성하는 방법

1. 단방향 @OneToMany 사용 X
2. 항상 부모(일) 자식(다) 측으로 전이를 사용(커스케이드)
3. 부모 측에 mappedBy 지정
4. 부모 측에 orphanRemoval 지정
5. 만약 양방향 연관 관계면 연관 관계의 양측을 동기화 상태로 유지(연관관계 편의 메서드)
6. 글로벌 지연로딩 전략 활용
7. equals와 hashCode 오버라이딩
8. @JoinColumn을 사용해 명시적으로 지정

자식 엔티티 삭제처리는 많은 SQL문이 생성될 수 있으므로 주의. 벌크 처리를 하는 것이 가장 좋은 방법.

## 항목 2. 단방향 @OneToMany 연관관계를 피해야 하는 이유

더 많은 쿼리가 발생하므로 비효율적

> 참고 OrderColumn으로 연관관계(List) 정렬 가능

다 관계인 엔티티가 연관관계의 주인이면 안된다.

## 헝목 3. 단방향 @ManyToOne의 효율성

쓸데 없는 쿼리가 나가지 않으며 효율적. chapter01에서 OneToMany 부분만 지우면 됨.

## 항목 4. @ManyToMany 연관 관계를 효율적으로 구성하는 법

걍 별도의 중간 엔티티를 정의하자.

* 연관관계의 주인을 선택해야한다.
* List가 아닌 Set 사용
* 연관관계의 양측 동기화 상태 유지
* CascadeType.All 및 CascadeType.REMOVE 사용하지 않기. 대부분의 경우 PERSISTE와 MERGE를 사용하자.
* 조인 테이블 설정하자.
* 지연 로딩 사용
* equals와 hashCode 오버라이딩하고 toString()은 주의해서 정의

## 항목 5. @ManyToMany에서 Set이 List보다 나은 이유

List 쿼리가 여러번 나간다. Set이 더 효율적. 

## 항목 6. CascadeType.REMOVE 및 orphanRemoval=true를 사용해 하위 엔티티 제거를 피해야하는 이유와 시기

애플리케이션이 삭제를 하는 경우 CascadeType.REMOVE나 orphanRemoval=true를 함께 사용할 수 있다.
이 방법은 유용하며, 부모와 자식을 위한 낙관적 잠금 메커니즘의 이점을 누릴 수 있다.

대신 쿼리가 많이 나가 성능 문제가 있을 수 있는데 이는 벌크 처리를 통해 DELETE 문의 수를 최적화 가능하다.
벌크 처리의 단점은 아래와 같다.

* 자동화된 낙관적 잠금 메커니즘을 무시한다.
* 영속성 컨텍스트는 벌크 작업에 의해 수행된 수정 사항을 반영하고자 동기화되지 않기 때문에 유효하지 않은 컨텍스트를 가질 수 있다.
* 전이 삭제 또는 orphanRemoval을 활용할 수 없다.

와 같은 단점이 문제가 된다면 벌크 작업을 피하거나 직접 해당 문제를 처리하는 2가지 선택사항이 있다.
가장 어려운 부분은 영속성 컨텍스트에 로드되지 않은 자식에 대한 자동 낙관적 잠금 메커니즘 작업을 에뮬레이션하는 것이다.

예제에서는 자동 낙관적 잠금 메커니즘이 비활성화됐다고 가정하지만 flushAutomatically = true 또는 clearAutomatically = true를 통해 영속성 컨텍스트 동기화 문제를 관리할 수 있다.
그러나 이 2가지 설정이 항상 필요한 것은 아니다. 사용 방법은 달성해야 할 목적에 따라 다르다.

## 항목 7. JPA 엔티티 그래프를 통해 연관관계를 가져오는 방법

엔티티 그래프는 JPA 2.1에서 도입됐으며 지연 로딩 예외와 N+1 문제를 해결해 엔티티 로드 성능 개선에 도움이 된다.
개발자는 엔티티와 관련된 연관관계와 하나의 select문에 로드돼야할 기본적인 필드들을 지정하고 해당 엔티티에 대한 여러 엔티티 그래프를 정의해 다른 엔티티를 연결할 수 있으며,
하위 그래프를 사용해 복잡한 페치 플랜을 만들 수 있다.
엔티티 그래프는 전역적이며 여러 엔티티에서 재사용될 수 있다.

현재 FetchType의 의미 쳬계를 재정의하고자 다음과 같은 2가지 속성을 갖는다.

* 페치 그래프: javax.persistence.fetchgraph 속성으로 지정되는 기본 가져오기 유형으로, attributeNodes에 지정되는 속성들은 EAGER로 처리된다. 나머지 속성들은 LAZY
* 로드 그래프: javax.persistence.loadgraph 속성으로 지정돼 사용되는 가져오기 유형으로 attributeNodes에 지정된 속성들은 EAGER로 처리. 나머지는 지정된 타입이나 기본 FetchType에 따라 처리

페치 조인과 동일한 결과를 얻지만 다른 방법들을 살펴봄.

* NamedEntityGraph로 엔티티 그래프 정의
  * 쿼리 메서드 오버라이딩
  * 쿼리 빌더 메커니즘 사용
  * Specification 사용
  * @Query 및 JPQL 사용
  * 애드혹 엔티티 그래프 -> 이게 젤 좋은 듯
* 엔티티 매니저를 통한 엔티티 그래프 정의

> 참고로 Specification, CriteriaQueryBuilder는 생략

주의할 부분

* 엔티티 그래프와 네이티브 쿼리는 같이 사용 불가능.
* 페치 조인으로 페이징을 처리하면 성능 저하에 주의하자.
* JPA에서 여러 List 타입의 연관 관계를 fetch join으로 함께 불러오려고 하면, "카테시안 곱(Cartesian Product)" 문제가 발생
* 따라서 한 번에 최대 하나의 연관관계만 가져오자.

## 항목 8. JPA 엔티티 서브 그래프를 통해 연관관계를 가져오는 방법

서브그래프를 사용하면 복잡한 엔티티 그래프를 작성할 수 있는데, 서브그래프는 주로 다른 엔티티 그래프 또는 엔티티 서브그래프에 포함되는 엔티티 그래프다.

* NamedEntityGraph 및 @NamedSubgraph 사용
* 애드혹 엔티티 그래프에서 점 노테이션(.) 사용 -> 역시나 이게 제일 좋다.
* 엔티티 매니저를 통한 엔티티 서브 그래프 정의는 생략

## 항목 9: 엔티티 그래프 및 기본 속성 처리 방법

하이버네이트 JPA 기반으로 엔티티 그래프를 사용해 엔티티의 일부 기본 속성만을 가져오려면 다음과 같은 수정된 해결책이 필요하다.

* 하이버네이트 Bytecode Enhancement
* @Basic(fetch = FetchType.LAZY)를 사용해 엔티티 그래프의 일부가 아닌 기본 속성 지정

여기서 주요 단점은 지정된 기본 속성이 엔티티 그래프를 사용하는 쿼리뿐만 아니라 다른 모든 쿼리들도 지연 로딩으로 처리되는데, 대부분의 이 동작을 원하지 않는다는 점이다.

> 그냥 단순히 이런게 있구나 확인하고 넘어감.

## 항목 10. 하이버네이트 @Where 애노테이션을 통한 연관관계 필터링

* 결과적으로 join fetch where를 사용하는 것이 더 좋다.
  * 하나의 조회문으로 모두 처리가능
  * 파라미터로 바인딩 가능
* Where를 사용하는 것은 소프트 삭제 구현에서 추천
* Where가 Deprecated 되었으므로 @SQLRestriction를 사용해야함.

# 항목 11. @MapsId를 통한 단방향/양방향 @OneToOne 최적화 방법


### 단방향 @OneToOne

부모 측이 지속적으로 또는 매번 자식 측을 필요로 한다면 새로운 쿼리가 추가되면서 성능이 저하될 수 있다. 

### 양방향 @OneToOne

양방향 @OneToOne의 주요 단점은 다음과 같이 부모를 가져올 때 확인할 수 있다.

LAZY 연관관계임에도 Author를 가져오면 SELECT 문들이 트리거 된다. 항상 조회문 1개가 추가되는 것이다.
부모엔티티 다음에 하이버네이트는 자식 엔터티도 가져온다.
애플리케이션 부모만 필요한 경우에도 자식을 가져오는 것은 리소스 낭비이자 성능 저하를 초래한다.

해결 방법은 부모 측에 Bytecode Enhancement와 @LazyToOne(LazyToOneOption.NO_PROXY)를 사용하는 것이다.
또는 더 좋은 방법은 단방향 @OneToOne과 @MapsId를 사용하는 것이다.

### @OneToOne을 구원하는 @MapsId

![KakaoTalk_Photo_2024-04-17-21-29-59](https://github.com/happysubin/book-study/assets/76802855/c3baf825-6547-40b4-83b5-4add8b997c9e)

@MapsId는 @ManyToOne 단방향 @OneToOne 연관관계에 적용할 수 있는 JPA 2.0 애노테이션이며 이를 통해 자식 테이블의 기본키 자체가 부모 테이블의 기본키를 참조하는 외래키가 될 수 있다.

부모 엔티티는 양방향 @OneToOne이 필요하지 않으므로 이는 삭제.

author_id가 author의 식별자로 설정되었다.
이는 부모 테이블과 자식 테이블이 동일한 키를 가진 것을 의미한다.

개발자는 이제 부모 식별자로 자식을 가져올 수 있다.


## 항목 12: 단 하나의 연관관계만 Null이 아닌지 확인하는 방법

BeanValidation을 통해 Null이 아닌지 확인하는 방법을 살펴봄
