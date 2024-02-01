## chapter06. 엔터티-속성-값

### 목표: 가변 속성 지원

소프트웨어 프로젝트에서 확장성은 자주 목표로 언급된다. 우리는 미래에도 프로그래밍을 조금만 하거나 아니면 하지 않고도 새로운 요구사항을 유동적으로 수용할 수 있도록 소프트웨어를 설계 하고 싶다.

일반적인 테이블은 테이블에 있는 모든 행과 관계된 속성 컬럼으로 이루어져 있고, 각 행은 비슷한 객체의 인스턴스를 나타낸다.

속성 집합이 다르면 객체의 타입도 다르다는 뜻이며, 따라서 다른 테이블에 있어야 한다.

그러나 현대적인 OOP 모델에서는 동일한 데이터 타입을 확장(상속)하는 것과 같은 방법으로 객체의 타입도 관계를 가질 수 있다.

객체지향 설계에서 이런 객체들은 서브타입 인스턴스로 다룰 수도 있고, 같은 베이스 타입의 인스터스로도 간주할 수도 있다.

우리는 여러 객체의 계산이나 비교를 간단히 하기 위해 객체를 하나의 데이터베이스 테이블에 행으로 저장하고 싶다.

또한 객체의 각 서브타입이 베이스 타입이나 다른 서브타입에는 적용되지 않는 속성 컬럼을 저장하는 것도 허용해야 한다.

#### 예시 

<img width="358" alt="스크린샷 2024-02-01 오전 11 31 01" src="https://github.com/happysubin/book-study/assets/76802855/b7e4c6be-8129-4b75-957d-dad69f2c2581">

Bug와 FeatureRequest는 베이스 타입인 Issue의 속성을 공통으로 가진다. 모든 이슈는 이슈를 보고한 사람과 관계가 있다.
또한 이슈는 제품과도 관계가 있을 뿐 아니라 우선순위도 갖는다. 그러나 Bug는 버그가 발생한 제품의 버전과 버그의 중요도 또는 영향도와 같은 다른 속성도 갖는다. 마찬가지로 FeatureRequest 또한 자신만의 속성을 갖는다. 이 예에서는 해당 기능 개발에 대한 예산을 지원하는 스폰을 속성으로 갖는다고 하자.

### 안티패턴: 범용 속성 테이블 사용

가변 속성을 지원해야 할 때 일부 개발자가 흥미를 갖는 방법은 별도 테이블을 생성해 속성을 행으로 저장하는 것이다.

* 엔터티: 보통 이 칼럼은 하나의 엔터티에 대해 하나의 행을 가지는 부모 테이블에 대한 FK다.
* 속성: 일반적인 테이블에서의 칼럼 이름을 나타내지만, 이 새로운 설계에서는 각 행마다 속성이 하나씩 들어간다.
* 값: 모든 엔터티는 각 속성에 대한 값을 가진다. 예를 들어, PK 값이 1234인 버그가 주어졌을 때, status란 속성을 가지고, 그 속성 값은 NEW다.
  
<img width="429" alt="스크린샷 2024-02-01 오전 11 40 06" src="https://github.com/happysubin/book-study/assets/76802855/965425f8-0a90-4da9-a25b-0406507dd531">


이 설계는 Entity-Attribute-Value(엔터티-속성-값) 또는 줄여서 EAV라 불린다.

때로는 오픈 스키마, 스키마리스 또는 이름-값 쌍으로 불리기도 한다.

```sql
CREATE TABLE Issues (
    issued_id SERIAL PRIMARY KEY
);

INSERT INTO Issues (issue_id) VALUES (1234);

CREATE TABLE IssueAttributes (
    issue_id BIGINT UNSIGNED NOT NULL,
    attr_name VARCHAR(100) NOT NULL,
    attr_value VARCHAR(100),
    PRIMARY KEY (issue_id, attr_name),
    FOREIGN KEY (issue_id) REFERENCES Issues(issue_id)
);

INSERT INTO IssueAttributes (issue_id, attr_name, attr_value)
    VALUES 
        (1234, 'product', '1'),
        (1234, 'date_reported', '2009-06-01'),
        (1234, 'status', 'NEW'),
        (1234, 'description', 'Saving does not work'),
        (1234, 'reported_by', 'Bill'),
        (1234, 'version_affected', '1.0'),
        (1234, 'severity', 'loss of functionality'),
        (1234, 'priority', 'high')
```

별도 테이블을 추가해 다음과 같은 이득을 얻은 것 같아 보인다.

* 두 테이블 모두 적은 컬럼을 갖고 있다.
* 새로운 속성을 지원하기 위해 칼럼 수를 늘릴 필요가 없다.
* 특정 속성이 해당 행에 적용되지 않을 때 NULL을 채워야 하는 칼럼이 지저분하게 생기는 것을 피할 수 있다.

개선된 설계처럼 보이지만 데이터베이스 설계가 단순하다고 해서 사용하기 어려운 것을 보상해주지는 않는다.


#### 속성 조회

일반적인 테이블 설계에서는 모든 버그와 보고일자를 조회하려면 다음과 같은 간단한 쿼리를 실행하면 된다.

```sql
SELECT issue_id, date_reported FROM Issues;
```

EAV 설계를 사용할 때 위 쿼리와 동일한 정보를 얻으려면 더 복잡하고 명확하지 않은 아래와 같은 쿼리를 작성해야 한다.

```sql
SELECT issue_id, attr_value AS "date_reported"
FROM IssueAttributes
WHERE attr_name = "date_reported";
```

#### 데이터 정합성 지원

* 필수 속성 사용 불가.
  * EAV 설계에서는 각 속성이 IssueAttributes 테이블에서 칼럼이 아니래 행으로 대응된다.
  * issue_id 값에 대해 행이 있는지, 그 행의 attr_name 칼럼이 date_reported란 문자열을 가지고 있는지 확인하는 제약조건이 필요하다.
  * 그러나 SQL은 이런 사항을 확인할 수 있는 제약 조건을 지원하지 않는다. 전부 코드로 작성해야함.
* SQL 데이터 타입 사용 불가
  * DATETIME 같은 형식 사용이 불가능하고 문자열 타입을 사용해야 한다.
* 참조 정합성 강제 불가
  * 일반적인 데이터베이스에서는 색인 테이블에 대한 FK를 정의해 특정 속성의 범위를 제한할 수 있다.
  * 예를 들어, 버그나 이슈 의 status 속성은 BugStatus 테이블에 저장된 짧은 목록의 값 중 하나여야 한다.
  * EAV 설계에서는 이런 식의 제약 조건을 적용할 수 없다. 참조 정합성 제약 조건은 테이블의 모든 행에 적용된다.
* 속성 이름 강제 불가
* 행을 재구성하기
  * 각 속성이 IssueAttributes 테이블의 별도 행으로 저장되어 있으므로, 행 하나의 일부로 속성을 꺼내기 위해서는 각 속성에 대한 조인이 필요하다.
  * 이 쿼리를 작성하는 시점에 모든 속성의 이름을 알아야 한다. 쿼리는 아래와 같다.

```sql
SELECT i.issue_id
    i1.attr_value AS "date_reported",
    i2.attr_value AS "status",
    i3.attr_value AS "priority",
    i4.attr_value AS "description"
FROM Issues AS i
    LEFT OUTER JOIN IssueAttributes AS i1
        ON i.issue_id = i1.issue_id AND i1.attr_name = 'date_reported'
    LEFT OUTER JOIN IssueAttributes AS i2
        ON i.issue_id = i2.issue_id AND i2.attr_name = 'status'
    LEFT OUTER JOIN IssueAttributes AS i3
        ON i.issue_id = i3.issue_id AND i3.attr_name = 'priority'
    LEFT OUTER JOIN IssueAttributes AS i4
        ON i.issue_id = i4.issue_id AND i4.attr_name = 'description'
```

내부 조인을 사용하려면 IssueAttributes에 속성이 하나라도 없는 경우 아무 것도 리턴되지 않을 수 있으므로 외부 조인을 사용해야 한다.

속성 개수가 늘어나면 조인 회수도 늘어나야 하고 쿼리의 비용은 지수적으로 증가한다.

### 안티패턴 인식 방법

1. 이 데이터베이스는 메타데이터 변경 없이 확장이 가능하지. 런타임에 새로운 속성을 정의할 수 있어. -> 관계형 데이터베이스는 이런 유연성 제공 X
2. 하나의 쿼리에서 조인을 최대 몇번이나 할 수 있지?
3. 우리 전자상거래 플랫폼에서는 리포트를 어떻게 생성해야 할지 이해할 수가 없어. 아무래도 컨설턴트를 고용해야할 것 같아. -> EAV 설계를 사용하는 소프트웨어 패키지에서는 일반적인 리포트 생성 쿼리가 매우 복잡하거나 비현실적이기까지 하다.

### 안티패턴 사용이 합당한 경우

관계형 데이터베이스에서는 EAV 안티패턴 사용을 합리화하기 어렵다.

동적 속성이 필요한 곳에서는 비관계형 기술(NOSQL)을 사용하는 것이 가장 좋은 방법이다. 

### 해법: 서브타입 모델링

#### 단일 테이블 상속

> jpa 강의 테이블 설계에서 배운 부분. 하나의 테이블에 다 저장. 사용하지 않는 컬럼에는 null을 넣는다. DBA가 선호

새로운 객체 타입이 생기면, 데이터베이스는 새로 생긴 객체 타입의 속성을 수용해야 한다. 새로운 객체에만 있는 속성에 대한 칼럼을 추가해 테이블을 변경해야 한다.
테이블에 들어가는 칼럼 수의 실질적인 한계에 직면할 수도 있다.

단일 테이블 상속의 또 다른 한계는 어떤 속성이 어느 서브타입에 속하는지를 정의하는 메타데이터가 없다는 것이다.
주어진 서브타입에 적용되지 않는 속성을 애플리케이션에서 무시하도록 할 수 는 있다. 그러나 어떤 속성이 어떤 서브타입에 적용 가능한지를 직접 추적해야 한다. 이런 정보를 데이터베이스에 메타데이터를 사용해 정의할 수 있다면 좀 더 좋을 것이다. 

단일 테이블 상속은 서브타입 개수가 적고 특정 서브타입에만 속하는 속성 개수가 적을 때, 그리고 액티브 레코드와 같은 단일 테이블 데이터베이스 접근 패턴을 사용해야할 때 가장 좋다.

#### 구체 테이블 상속

> jpa 강의 테이블 설계에서 배운 부분. 모든 구체 테이블을 만든다. ORM 전문가와 DBA 모두 반대

특정 서브타입을 저장할 때 해당 서브타입에 적용되지 않는 속성을 저장할 수 없게 한다는 것이 장점이다.

그러나 서브타입 속성에서 어떤 속성이 공통 속성인지를 알기가 어렵다. 또한 새로운 공통속성이 추가되면 모든 서브타입 테이블을 변경해야 한다. 

관련된 객체가 이런 서브타입 테이블에 저장되었다는 것을 알려주는 메타데이터도 없다.
즉, 프로젝트에 새로 투입된 프로그래머가 테이블 정의를 살펴보면, 이런 서브타입 테이블에 일부 칼럼이 공통으로 존재하는 것을 보겠지만, 이들 테이블 사이에 논리적 관계가 있어서 그런 것인지 아니면 그저 우연의 일치로 테이블이 비슷한 것인지 메타데이터를 통해서 알 수가 없다.

각 서브타입이 별도 테이블에 저장되어 있는 경우, 서브타입에 상관없이 모든 객체를 보는 것이 불편하다. 이 쿼리를 쉽게 하려면 각 서브타입 테이블에서 공통 속성만을 선택한 다음 이를 UNION으로 묶은 뷰를 정의해야 한다.

#### 클래스 테이블 상속

> jpa 강의 테이블 설계에서 배운 부분. fk를 사용해 상속 테이블을 만든다. ORM 전문가가 선호

서브타입 테이블의 PK는 베이스 테이블에 대한 FK 역할도 한다.

메타 데이터에 의해 일대일 관계가 강제된다. 

이 설계는 검색에서 베이스 타입에 있는 속성만 참조하는 한, 모든 서브타입에 대한 검색을 하는 데 효율적인 방법을 제공한다. 
검색 조건을 만족하는 항목을 찾으면, 서브타입에만 있는 속성은 각 서브타입 테이블을 조회해 얻을 수 있다.

베이스 테이블의 행이 어떤 서브타입을 나타내는지는 알 필요가 없다. 서브타입 개수가 적다면 각 서브타입과 조인하는 쿼리를 작성해 단일 테이블 상속의 테이블에서와 같은 결과를 만들어낼 수 있다. 주어진 행에 적용되지 않는 서브타입의 속성은 NULL이 된다.

```sql
SELECT i.*, b.*, f.*
FROM Issues AS i
    LEFT OUTER JOIN Bugs AS b USING (issue_id)
    LEFT OUTER JOIN FeatureRequests AS f USING (issue_id);
```

모든 서브타입에 대한 조회가 많고 공통 칼럼을 참조하는 경우가 많다면 이 설계가 가장 적합하다.


#### 반구조적 데이터

서브타입의 수가 많거나 똔느 새로운 속성을 지원해야 하는 경우가 빈번하다면, 데이터의 속성 이름과 값을 XML or JSON 형식으로 부호화해 TEXT, CLOB 컬럼으로 저장할 수도 있다. (Serialized LOB == 직렬화된 LOB)

```sql
CREATE TABLE Issues (
    issue_id SERIAL PRIMARY KEY,
    reported_by BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED,
    priority VARCHAR(20),
    version_resolved VARCHAR(20),
    status VARCHAR(20),
    issue_type VARCHAR(10), 
    attributes TEXT NOT NULL, -- 모든 동적 속성을 저장
    .. --생략
);
```

이 설계의 장점은 확장이 쉽다. 새로운 속성은 언제든 TEXT 컬럼에 저장할 수 있다. 
각 행이 잠재적으로 전혀 다른 속성 집합을 가질 수 있으므로, 행 수만큼 서브타입을 갖는 것도 가능하다.

단점은, 이런 구조에서는 SQL이 특정 속성에 접근하는 것을 거의 지원하지 못한다는 점이다. 
TEXT 안에 들어가있는 각 속성에 대해서는 행 기반의 제한을 하거나, 계산을 집계하거나, 정렬을 하는 등의 다른 연산을 하는 조회가 쉽지 않다. 여러 속성을 담고 있는 TEXT 칼럼을 하나의 값으로 꺼내야 하고 코드를 복호화해 속성을 해석하는 애플리케이션 코드도 작성해야 한다.

이 설계는 서브타입 개수를 제한할 수 없고 어느 때고 새로운 속성을 정의할 수 있는 완전한 유연성이 필요할 때 가장 적합하다.

#### 사후처리

프로젝트를 인계 받았는데 데이터 모델이 변경 불가능하거나 회사에서 EAV를 사용하는 서드파티 소프트웨어 플랫폼을 도입한 경우와 같이, EAV  설계를 사용할 수 밖에 없는 경우가 있따.

이런 경우라면 안티패턴 절에서 설명한 문제를 잘 이해해 EAV를 사용할 때 수반되는 부가 작업을 예상하고 계획해야 한다.

무엇보다도, 일반적인 테이블에 데이터가 저장되어 있을 때처럼 엔티티를 단일 행으로 조회하는 쿼리를 작성하면 안된다.
대신 엔티티에 관련된 속성을 조회해 저장되어 있는 그대로 한 행씩 꺼내 처리해야 한다.

```sql
SELECT issue_id, attr_name, attr_value
FROM IssueAttributes
WHERE issue_id = 1234; 
```

이 쿼리는 작성하기 쉽고, 데이터베이스에서 처리하기도 쉽다. 또한 해당 이슈와 관련된 모든 속성을 리턴하며, 쿼리를 작성할 때 얼마나 많은 속성이 있는지 상관이 없다.

이 형식의 결과를 사용하려면, 결과 집합에 대해 루프를 돌면서 객체의 속성을 설정하는 애플리케이션 코드를 작성해야 한다.