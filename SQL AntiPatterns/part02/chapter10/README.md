## chapter10. 반올림 오류

### 목표: 정수 대신 소수 사용

정수는 유용한 타입이지만, 1, 327, -19와 같은 정수만 저장할 수 있다,

그러나 2.5와 같ㅇ느 소수 값은 표현하지 못한다. 정수보다 정밀한 수치가 필요하다면 다른 데이터 타입이 필요하다.

예를 들어, 금액 한계는 $19.95와 같이 보통 소수점 둘째 자리까지의 소수로 표현한다.

따라서 목표는 정수가 아닌 수를 저장하고 이를 산술 연산에 저장하는 것이다. 다른 목표도 있는데, 말할 필요도 없지만 산술 연산의 결과가 정확해야 한다는 것이다.

### 안티패턴: FLOAT 데이터 타입 사용

대부분의 프로그래밍 언어는 float나 double과 같은 실수를 표현하기 위한 데이터 타입을 지원한다.

SQL도 비슷한 데이터 타입을 같은 이름으로 지원한다.

많은 프로그래머가 SQL에서 소수 데이터가 필요한 곳에 자연스럽게 FLOAT 데이터 타입을 사용한다. 프로그램을 작성할 때 사용하던 float 데이터 타입에 익숙하기 때문이다.

SQL의 FLAT 데이터 타입은 다른 프로그래밍 언어의 float와 마찬가지로 IEEE 754 표준에 따라 실수를 이진 형식으로 부호화한다. 이 데이터 타입을 효과적으로 사용하려면 이 형식의 부동 소수점 수 특성을 이해할 필요가 있다.

> IEEE754: IEEE에서 개발한 컴퓨터에서 부동소수점을 표현하는 가장 널리 쓰이는 표준이다.

#### 필요에 의한 반올림 

많은 프로그래머가 부동 소수점 수의 특성, 즉 십진수로 표현된 모든 수를 이진수로 표현할 수 없다는 사실을 모르고 있다.
즉, 어떤 수는 사용자의 필요와 관계 없이 가까운 다른 수로 반올림되어야 한다.

이런 반올림 동작의 배경을 이해하기 위해, 1/3과 같은 유리수와 0.33333... 과 같은 순환 소수로 표현된 수를 비교해보자.
진짜 값을 정확히 표현하려면 무한대의 자리수가 필요하므로, 십진수 소수로는 표현할 수 없다.
자릿수는 수의 정도이므로, 순환소수는 무한대의 정도가 필요하다.

타협안은 유한소수를 사용하고 0.333과 같이 원래의 값에 가능한 가까운 값을 선택하는 것이다. 그러나 이렇게 하면 우리가 의도한 것과 정확히 같은 값은 되지 않는다.

```
1/3 + 1/3 + 1/3 = 1
0.333 + 0.333 + 0.333 = 0.999
```

1/3의 근사 값 세 개를 더하면 정도를 높여도 여전히 1.0을 얻을 수 없다. 순환 소수를 유한소수로 표현하려면 이렇게 타협할 수 밖에 없다.

```
1/3 + 1/3 + 1/3 = 1
0.333333 + 0.333333 + 0.333333 = 0.999999
```

이는 우리가 생각할 수 있는 정상적인 어떤 수를 유한소수로는 표현하지 못할 수도 있다는 뜻이다.

IEEE 754는 부동 소수점 수를 밑수가 2인 형식으로 표현한다. 이진수로 무한한 정도를 요하는 값과 십진수에서 무한한 정도를 요구하는 수는 다르다. 59.95와 같이 십진수에서 유한한 정도를 가지는 값을 이진수로 표현하려면 무한한 정도가 필요하다. FLOAT 데이터는 이렇게 할 수 없으므로, 밑 수를 2로 하는 가장 가까운 값을 사용해 저장하는데, 이는 밑수를 10으로 했을 때 59.950000762939와 같다.


어떤 값들은 두 형식에서 모두 유한한 정도로 표현한다. 이론적으로는, IEEE 754 형식에서 숫자가 어떻게 저장되는지를 자세히 이해하면, 십진수 값이 이진수에서 어떻게 표현될지를 예상할 수 있다. 그러나 실제로 부동 소수점수에 대해 이런 계산을 하는 사람은 거의 없을 것이다. 데이터베이스에 FLOAT로 오차 없이 표현되는 값만 저장되어 있다고 할 수는 없으므로, 애플리케이션에서는 이런 칼럼에 있는 어떤 값이든 반올림되었다고 가정해야 한다.

몇몇 데이터베이스는 DOUBLE PRECISIONrhk REAL이라 불리는 데이터 타입을 지원한다. 이런 데이터 타입이나 FLOAT가 지원하는 정도는 데이터베이스 구현에 따라 다르지만, 모두 유한한 수의 이진수 자리로 부동 소수점 수를 표현하므로, 모두 비슷한 반올림 동작을 가진다.

#### SQL에서 FLOAT 사용

어떤 데이터베이스는 부정확한 값을 보정해, 의도한 값을 표시해준다.

> 56.95를 리턴하는 커리가 있는데 FLOAT에 실제로 저장되어 있는 이 값과 같지 않다. 59.95에 10억을 곱해보면 불일치를 확인할 수 있다.

동등 비교에 FLOAT를 사용하는 것도 문제다. 이 문제를 회피하는 흔한 방법은 두 부동 소수점 값이 일정 수준 이상 충분히 가까우면 '사실상 같은' 값으로 다루는 것이다.

많은 값을 집계해 계산할 때도 FLOAT의 부정확한 특성으로 인한 정확성 문제가 발생한다. 

> 예를 들어, 한 칼럼에 있는 부동 소수점 수의 합계를 계산하기 위해 SUM()을 사용하는 경우, 반올림으로 인한 오차도 축적된다.
> 금융 애플리케이션에서의 복리 계산은 연속한 곱을 적용하는 좋은 예다. 금융 관련 애플리케이션은 정확한 값을 사용하는 것이 제일 중요하다.

### 안티패턴 인식 방법

FLOAT, REAL, DOUBLE PRECISION 데이터 타입이 사용되는 곳이면 어디든 의심이 간다.
부동 소수점 수를 사용하는 대부분의 애플리케이션에서는 IEEE 754 형식이 제공하는 넓은 범위의 값이 필요하지 않다.

SQL에서도 FLOAT 데이터 타입을 사용하는 것이 자연스러워 보인다. 대부분의 프로그래밍 언어에 있는 데이터 타입과 이름이 같기 때문이다. 그러나 더 좋은 데이터 타입이 있다.

### 해법: NUMERIC 데이터 타입 사용

고정 소수점 수에는 FLOAT나 이와 비슷한 타입을 사용하지 말고, NUMERIC 또는 DECIMAL 타입을 사용해야 한다.

```sql
ALTER TABLE Bugs ADD COLUMN hours NUMERIC(9,2);
ALTER TABLE Accounts ADD COLUMN hourly_rate NUMERIC(9,2);
```

* 이런 데이터 타입은 칼럼 정의에서 지정한 정도까지 수치를 정확하게 표현한다.
* 데이터 타입의 둘째 인수로 스케일을 지정할 수 있다. 스케일은 소수점 오른쪽의 자릿수다.
* 정도가 9이고 스케일이 2라면 1234567.89와 같은 형식이 저장 가능하다.
* 칼럼에 지정한 정도와 스케일은 테이블의 모든 행에 적용된다.

NUMERIC과 DECIMAL의 장점은 유리수가 FLOAT 타입에서와 같이 반올림되지 않고 저장된다는 것이다. 
59.95란 값을 지정하면, 이 값이 정확하게 저장된다고 확신할 수 있다.





> 자바에서도 돈 계산에서는 BigDecimal 사용하는데 그거랑 비슷한 것 같다.