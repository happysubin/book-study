
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
