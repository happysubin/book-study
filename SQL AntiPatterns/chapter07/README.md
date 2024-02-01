## chapter07. 다형성 연관

댓글과 버그는 다대일 관계다.

댓글과 기능요청은 다대일 관계다.

댓글은 기능요청에도 달리고 버그에도 달린다.

버그나 기능요청 중 어느 이슈타입과 관계되든 Comments를 하나의 테이블에 저장하고 싶다. 그러나 여러 개의 부모테이블을 참조하는 FK를 만들 수는 없다.

SQL에서는 각 행별로 다른 테이블과 조인할 수 없고, SQL 문법에서는 쿼리를 제출하는 시점에 모든 테이블 이름이 정해져 있엉 ㅑ한다.

쿼리가 실행되면서 테이블이 바뀔 수는 없다. 잘못된 것이 뭘까?? 

### 목표: 여러 부모 참조

<img width="400" alt="스크린샷 2024-02-01 오후 1 47 22" src="https://github.com/happysubin/book-study/assets/76802855/f9d885ce-d382-484e-8755-8637bccf1f18">


위 그림에서는 자식 테이블의 FK가 갈라진다. 따라서 Comments 테이블에 있는 행은 Bugs 테이블에 있는 행 또는 FeatureRequests 테이블에 있는 행 중 하나하고만 대응한다. 

__특정 댓글은 하나의 버그 또는 하나의 기능 요청, 둘 중 하나만 참조해야 한다.__


### 안티패턴: 이중 목적의 FK 사용

이런 경우에 대한 해법은 다형성 연관 이름이 붙을 정도로 널리 알려져 있다.
여러 테이블을 참조하기 때문에 난잡한 연관이라 불리기도 한다.

#### 다형성 연관관계 정의

다형성 연관을 작동하게 하려면 FK 칼럼 issue_id 옆에 문자열 타입의 별도 칼럼을 추가해야 한다.
이 별도 칼럼에 현재 행이 참조하는 부모 테이블 이름을 넣는다. 
이 예에서 새로 추가하는 칼럼 이름을 issue_type으로 했고, 여기에 이 연관에서 참조하는 두 개의 부모 테이블 이름으로 Bugs 또는 FeatureRequests가 들어간다.

```sql
CREATE TABLE Comments (
    comment_id SERIAL PRIMARY KEY,
    issue_type VARCHAR(20),
    issue_id BIGINT UNSIGNED NOT NULL,
    author BIGINT UNSIGNED NOT NULL,
    comment_date DATETIME,
    comment TEXT,
    FOREIGN KEY (author) REFERENCES Accounts(account_id)
);
```

issue_id에 대한 FK 선언이 빠져있는 것을 확인할 수 있다. 
사실 FK는 하나의 테이블만 참조할 수 있기 때문에 다형성 연관을 사용할 경우에는 이 연관을 메타데이터 선언할 수 없다.
그 결과 Comments.issue_id의 값이 부모테이블에 있는 값과 대응되도록 강제할 수 없고, 데이터 정합성도 보장할 수 없다.

또한 Comments.issue_type에 있는 문자열이 데이터베이스에 있는 테이블에 대응되는지를 확인하는 메타데이터도 없다.

#### 다형성 연관에서의 조회

Comments 테이블의 issue_id 값은 Bugs와 FeatureRequests 양쪽 부모 테이블의 PK 칼럼에 나타날 수 있다.

자식 테이블을 부모 테이블과 조인할 때 issue_type을 정확하게 사용하는 것이 중요하다.
issue_id 값을 Bugs 테이블과 대응시키려 할 때는 FeatureRequests 테이블과 대응시키면 안된다.

예를 들어, 다음 쿼리는 PK 값이 1234인 버그에 대한 댓글을 조회한다.

```sql
SELECT *
FROM Bugs AS b JOIN Comments AS c
    ON (b.issue_id = c.issue_id AND c.issue_type = 'Bugs')
WHERE b.issue_id = 1234;
```

버그가 Bugs 테이블 하나에 저장되어 있다면 위 쿼리는 잘 동작한다. 그러나 Comments가 Bugs 테이블과 FeatureRequests 테이블 모두 연관되어 있을 때는 문제가 발생한다. SQL에서는 조인할 모든 테이블을 명시해줘야 한다. 한 행씩 읽어가면서 Comments.issue_type 칼럼의 값에 따라 테이블을 바꿔가며 조인하기란 불가능하다.

댓글에 대해 버그 또는 기능 요청을 조회하려면, 부모 테이블 둘 다와 외부 조인을 하는 쿼리가 필요하다. 조인 조건의 일부로 Comments.issue_type 칼럼의 값을 사용하기 때문에 조인될 때는 각 행이 부모 테이블 중 한 쪽하고만 연결될 것이다.

외부 조인을 사용한다는 것은 결과 집합에서 각 행에 매칭되지 않는 부모로부터 온 필드를 NULL이 됨을 의미한다.

```sql
SELECT * 
FROM Comments AS c
    LEFT OUTER JOIN Bugs AS b
        ON (b.issue_id = c.issue_id AND c.issue_type = 'Bugs')
    LEFT OUTER JOIN FeatureRequests AS f
        ON (f.issue_id = c.issue_id AND c.issue_type = 'FeatureRequests')
```

#### 비 객체지향 예제

Bugs와 FeatureRequests 예제에서 두 부모 테이블은 서로 관련된 서브타입이었다.
다형성 연관은 부모 테이블이 서로 아무런 관계가 없을 때도 사용할 수 있다.

아래처럼 Users, Orders, Addresses를 예로 들 수 있다.

<img width="400" alt="스크린샷 2024-02-01 오후 2 14 08" src="https://github.com/happysubin/book-study/assets/76802855/54ff40c5-4406-49e1-a53a-4b69201d3893">

이 경우 Addresses 테이블은 특정 주소에 대해 부모 테이블을 Users나 Orders로 저장하는 다형성 칼럼을 가진다.

둘 중 하나를 선택해야 함에 유의하자.

한 주소를 사용자와 주문 둘 다에 연관시킬 수 없다. 사용자가 상품을 자신이 배송받기 위해 주문한 경우라도 마찬가지다.

또한, 사용자가 배송지 주소뿐 아니라 청구지 주소도 갖고 있다면, 이를 구별하기 위한 방법이 Addresses 테이블에 있어야 한다.
마찬가지로 다른 부모들 또한 Addresses 테이블에 특별한 사용법을 표시해야 할 수 있다.

```sql
CREATE TABLE Addresses(
    address_id SERIAL PRIMARY KEY,
    parent VARCHAR(20), -- Users 또는 Orders
    parent_id BIGINT UNSIGNED NOT NULL,
    users_usage VARCHAR(20), -- billing 또는 shipping
    orders_usage VARCHAR(20), --billing 또는 shipping
    address TEXT
);
```

### 안티패턴 인식 방법

* 이 태깅 스키마는 데이터베이스 내의 어떤 리소스에도 태그를 달 수 있다.
* 우리 데이터베이스 설계에서는 FK를 선언할 수 없어.
* entity_type 칼럼의 용도가 뭐지? -> 그건 각 행의 부모 테이블이 뭔지를 알려주는 칼럼이야 라고 말하면 100%. 안티패턴

### 안티패턴: 사용이 합당한 경우

* 다형성 연관 안티패턴은 사용을 피하고, FK와 같은 제약조건을 사용해 참조 정합성을 보장해야 한다.
* 다형성 연관은 메타데이터 대신 애플리케이션 코드에 지나치게 의존하는 경우가 많다.
* Hibernate와 같은 객체-관계 프로그래밍 프레임워크를 사용하는 경우 이 안티패턴 사용이 불가피할 수 있다.
* 이런 프레임워크는 참조 정합성 유지를 위한 애플리케이션 로직을 캡슐화해 다형성 연관으로 인해 생기는 위험을 완화해줄 수 있다.
* 성숙하고 평판이 좋은 프레임워크를 선택했다면, 프레임워크 설계자가 연관 관계 오류 없이 구현했을 것이란 확신을 어느 정도 가질 수 있다.
* 그러나 프레임워크의 도움 없이 다형성 연관을 처음부터 직접 구현한다면 쓸데없는 작업을 반복하는 것이다.

### 해법: 관계 단순화

다형성 연관의 단점을 피하면서 필요한 데이터 모델을 지원하기 위해서는 데이터베이스를 다시 설계하는 게 낫다.

지금부터 데이터 관계를 그대로 수용하면서 정합성을 강제하기 위해 메타데이터를 더 잘 활용하는 몇 가지 방법을 설명할 것이다.


#### 역참조

이 안티패턴을 해결하는 첫 번째 방법은 '다형성 연관에서는 관계의 방향이 거꾸로'라는 문제의 본질을 이해하고 나면 간단하다.

##### 교차테이블 생성

자식 테이블 Comments에 있는 FK는 여러 부모 테이블을 참조할 수 없으므로, 대신 Comments 테이블을 참조하는 여러 개의 FK를 사용하도록 한다.

각 부모에 대해 별도의 교차 테이블을 생성하고, 교차 테이블에는 각 부모 테이블에 대한 FK 뿐만 아니라 Comments에 대한 FK도 포함시킨다.

```sql
CREATE TABLE BugsComments (
    issue_id BIGINT UNSIGNED NOT NULL
    comment_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (issue_id, comment_id),
    FOREIGN KEY (issue_id) REFERENCES Bugs(issue_id),
    FOREIGN KEY (comment_id) REFERENCES Comments(comment_id)
);

CREATE TABLE FeaturesComments (
    issue_id BIGINT UNSIGNED NOT NULL
    comment_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (issue_id, comment_id),
    FOREIGN KEY (issue_id) REFERENCES FeatureRequests(issue_id),
    FOREIGN KEY (comment_id) REFERENCES Comments(comment_id)
);  
```

이 방법을 사용하면 Comments.issue_type 칼럼이 불필요해진다. 이제 연관 관계를 오류 없이 관리하는 데 애플리케이션 코드에 의지하지 않아도 된다.
메타 데이터로 데이터 정합성을 강제할 수 있다.


<img width="591" alt="스크린샷 2024-02-01 오후 2 45 10" src="https://github.com/happysubin/book-study/assets/76802855/54d9bebb-7760-44e0-92e6-e415dbb84b3d">



##### 신호등 설치

이 방법의 잠재적 약점은 허용하고 싶지 않은 연관이 생길 수 있다는 것이다.
교차 테이블은 보통 다대다 관계를 모델링하는 데 사용되므로 특정 댓글이 여러 개의 버그나 기능요청과 연관될 수 있다.
그러나 각 댓글은 하나의 버그 또는 하나의 기능요청과 관계되어야 할 것이다.
이 규칙을 부분적으로 강제할 수 있는데, 각 교차 테이블의 comment_id 칼럼에 UNIQUE 제약 조건을 선언하면 된다.

```sql
CREATE TABLE BugsComments (
    issue_id BIGINT UNSIGNED NOT NULL,
    comment_id BIGINT UNSIGNED NOT NULL,
    UNIQUE KEY (comment_id),
    PRIMARY KEY (issue_id, comment_id),
    FOREIGN KEY (issue_id) REFERENCES Bugs(issue_id),
    FOREIGN KEY (comment_id) REFERENCES Comments(comment_id)
);
```

이렇게 하면 특정 댓글이 교차 테이블에 한 번만 참조됨을 보장할 수 있고, 자연히 여러 개의 버그 또는 여러 개의 기능요청과 연관되는 것을 방지하게 된다.

그러나 이 메타데이터로는 특정 댓글이 양쪽 교차 테이블 모두에 한 번 씩 참조되는 것, 즉 특정 댓글이 버그와 기능 요청 양쪽에 동시에 연관되는 것은 방지하지 못한다. 이렇게 되기를 원하지는 않겠지만, 이를 방지하는 것은 애플리케이션 코드의 책임으로 남는다.

##### 양쪽 다 보기

특정 버그 또는 기능 요청에 대한 댓글은 교차 테이블을 이용해 간단히 조회할 수 있다.

```sql
SELECT *
FROM BugsComments AS b
    JOIN Comments AS c USING (comment_id)
WHERE b.issue_id = 1234;
```

특정 댓글에 대응되는 버그나 기능 요청은 두 교차 테이블로의 외부조인을 사용해 조회할 수 있다.

모든 부모테이블을 지정해야 하지만, 다형성 연관 안티패턴을 사용했을 때 처럼 쿼리가 복잡해지지는 않는다. 또한, 교차 테이블을 사용하면 참조 정합성에 의존할 수 있다. 반면 다형성 연관을 사용하면 그럴 수 없다.

```sql
SELECT *
FROM Comments AS c
    LEFT OUTER JOIN (BugComments JOIN Bugs AS b USING (issue_id))
        USING (comment_id)
    LEFT OUTER JOIN (FeautresComments JOIN FeatureRequests AS f USING (issue_id))
        USING (comment_id)
    WHERE c.comment_id = 9876;

```

##### 차선 통합

때론 여러 부모 테이블에 대해 조회한 결과를 하나의 테이블에서 조회한 것처럼 보이게 할 필요가 있을 수 있다.

2가지 방식이 있다.

1. UNION 사용
2. COALESCE 함수 사용

쿼리가 둘 다 복잡하므로 View로 뽑아서 사용하면 유용하다.

### 공통 수퍼테이블 생성

객체지향 다형성에서는 서브타입이 공통의 수퍼타입을 공유하기 때문에 두 서브타입을 비슷하게 참조할 수 있다.

SQL의 다형성 연관 안티패턴에서는 중요한 엔티티인 공통 수퍼타입이 빠져있는데, 모든 부모 테이블이 상속할 베이스 테이블을 생성해 문제를 수정할 수 있다.

자식 테이블인 Comments에 베이스 테이블을 참조하는 FK를 추가한다. issue_type 칼럼은 필요하지 않다.

<img width="417" alt="스크린샷 2024-02-01 오후 2 59 04" src="https://github.com/happysubin/book-study/assets/76802855/a95fb97d-7949-4abe-93a3-e04c3cc74e03">

```sql
CREATE TABLE Issues (
    issue_id SERIAL PRIMARY KEY
);

CREATE TABLE Bugs (
    issue_id BIGINT UNSIGNED PRIMARY KEY,
    FOREIGN KEY (issue_id) REFERENCES Issues (issue_id),
    ...
)

CREATE TABLE FeatureRequests (
    issue_id BIGINT UNSIGNED PRIMARY KEY,
    FOREIGN KEY (issue_id) REFERENCES Issues (issue_id),
);

CREATE TABLE Comments (
    comment_id SERIAL PRIMARY KEY,
    issue_id BIGINT UNSIGNED NOT NULL,
    author BIGINT UNSIGNED NOT NULL,
    comment_date DATETIME,
    comment TEXT,
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id),
    FOREIGN KEY (author) REFERENCES Accounts(account_id)
)
```

Bugs와 FeatureRequests의 issue_id 칼럼은 PK인 동시에 FK이기도 한 것에 유의하자.
Bugs와 FeatureRequests에 있는 issue_id 칼럼은 각자 새로운 키 값을 생성하지 않고 Issues 테이블에서 생성한 대체키 값을 참조한다.

특정 댓글이 참조하는 버그 또는 기능 요청을 비교적 간단한 쿼리를 통해 조회할 수 있다.
Issues 테이블에 속성을 정의하지 않았다면 쿼리에 이 테이블을 포함시킬 필요가 없다. 또한, Bugs 테이블과 Issues 테이블의 PK 값이 같기 때문에, Bug를 Comments와 직접 조인할 수 있다. 데이터베이스에서 컬럼이 서로 유사한 정보를 표현하는 경우에는 FK 제약조건으로 직접 연결되어 있지 않더라도 두 테이블을 조인할 수 있다.

```sql
SELECT *
FROM Comments AS c
    LEFT OUTER JOIN Bugs AS b USING (issue_id)
    LEFT OUTER JOIN FeatureRequests AS f USING (issue_id)
WHERE c.comment_id = 9876
```

특정 버그가 주어졌을 때 관련된 댓글도 마찬가지로 쉽게 조회할 수 있따.

```sql
SELECT *
FROM Bugs AS b
    JOIN Comments AS  c USING (issue_id)
WHERE b.issue_id = 1234;
```

요점은, Issues와 같은 조상 테이블을 사용하면 FK를 통해 데이터베이스 정합성을 강제할 수 있다는 것이다.
