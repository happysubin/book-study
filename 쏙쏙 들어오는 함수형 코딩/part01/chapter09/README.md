## chapter09. 계층형 설계 2

### 패턴 2: 추상화벽

추상화 벽은 여러 가지 문제를 해결하는데, 그중 하나는 팀 간 책임을 명확하게 나누는 것이다.

#### 추상화 벽으로 구현을 감춘다.

추상화 벽은 세부 구현을 감춘 함수로 이루어진 계층이다. 추상화 벽에 있는 함수를 사용할 때는 구현을 전혀 몰라도 함수를 사용할 수 있다.

함수형 프로그래머는 문제를 높은 수준으로 생각하기 위해 추상화 벽을 효과적인 도구로 사용한다.

예를 들어 마케팅 팀은 지저분한 반복문이나 배열을 직접 다루지 않고 마케팅 코드를 만들기 위해 추상화 벽에 있는 함수를 사용할 수 있다.

#### 세부적인 것을 감추는 것은 대칭적이다.

추상화 벽을 사용하면 마케팅팀이 세부 구현을 신경 쓰지 않아도 된다.

추상화 벽을 만든 개발팀은 추상화 벽에 있는 함수를 사용하는 마케팅 관련 코드를 신경쓰지 않아도 된다. 두 팀 모두 독립적으로 일할 수 있다.

추상화 벽은 흔하게 사용하는 라이브러리나 API와 비슷하다.

#### 장바구니 데이터 구조 바꾸기

배열보다 해시맵을 사용하는 것이 단일 검색에 효율적이다.

#### 장바구니 객체로 다시 만들기

장바구니를 배열을 사용하지 않고 자바스크립트 객체로 다시 만듦.

#### 추상화 벽이 있으면 구체적인 것을 신경쓰지 않아도 된다.

장바구니를 조작하는 함수를 고쳐 사용하고 있는 데이터 구조를 완전히 바꿨다. 그러면서 마케팅팀은 코드를 고치지 않았다.
심지어 마케팅팀은 데이터 구조가 바뀌었는지도 모른다. 이게 어떻게 가능한것일까?

데이터 구조를 변경하기 위해 함수 다섯 개만 바꿀 수 있었던 것은 함수가 추상화 벽에 있는 함수이기 때문이다.

추상화 벽은 "어떤 것을 신경쓰지 않아도 되지?"라는 말을 거창하게 표현한 개념이다
계층 구조에서 어떤 계층에 있는 함수들이 장바구니와 같이 공통된 개념을 신경 쓰지 않아도 된다면 그 계층을 추상화 벽이라고 할 수 있다.
추상화 벽은 필요하지 않은 것을 무시할 수 있도록 간접적인 단계를 만든다.

![KakaoTalk_Photo_2024-02-18-21-37-45](https://github.com/happysubin/about-backend-lecture-study/assets/76802855/5a6df39f-8550-4289-a7a5-99cef1a82bde)

추상화 벽이 의미하는 것은 추상화 벽 위에 있는 함수가 데이터 구조를 몰라도 된다는 것이다.

추상화 벽에 있는 함수만 사용하면 되고 장바구니 구현에 대해서는 신경 쓰지 않아도 된다. 그래서 추상화 벽 위에 있는 함수를 사용하는 사람에게 장바구니 구조가 배열에서 객체로 바뀌었다는 것을 알리지 않아도 되었다.

점선을 가로지르는 화살표가 없다는 것이 중요하다. 만약 점선 위에 있는 함수가 장바구니를 조작하기 위해 splice() 함수를 호출한다면 추상화 벽 규칙을 어기는 것이다. 신경쓰지 않아얗할 세부적인 구현을 사용하고 있는 것이다.
이런 것을 완전하지 않은 추상화 벽이라고 부른다. 완전하지 않은 추상화 벽을 완전한 추상화 벽으로 만드는 방법은 추상화 벽에 새로운 함수를 만드는 것이다.

#### 추상화 벽은 언제 사용하면 좋을까?

1. 쉽게 구현을 바꾸기 위해
2. 코드를 읽고 쓰기 쉽게 만들기 위해
3. 팀 간에 조율해야할 것을 줄이기 위해
4. 주어진 문제에 집중하기 위해

#### 추상화 벽 리뷰

추상화 벽으로 추상화 벽 아래에 있는 코드와 위에 있는 코드의 의존성을 없앨 수 있다.
서로 신경쓰지 않아도 되는 구체적인 것을 벽을 기준으로 나눠서 서로 의존하지 않게 한다.


일반적으로 추상화 벽 위에 이쓴 코드는 데이터 구조와 같은 구체적인 내용을 신경쓰지 않아도 된다.

추상화 벽과 그 아래에 있는 코드는 높은 수준의 게층에서 함수가 어떻게 사용되는지 몰라도 된다.

모든 추상화는 다음과 같이 동작한다.
* 추상화 단계의 상위에 있는 코드와 하위에 있는 코드는 서로 의존하지 않게 정의한다.
* 추상화 단계의 모든 함수는 비슷한 세부 사항을 무시할 수 있도록 정의한다.
* 추상화 벽으로 추상화를 강력하고 명시적으로 만들 수 있다.
* 마케팅 관련 코드는 장바구니가 어떻게 구현되어 있는지 몰라도 된다. 추상화 벽에 있는 모든 함수가 이것을 가능하게 한다.

바뀌지 않을지도 모르는 코드를 언젠가 쉽게 바꿀 수 있게 만드려는 함정에 빠지지 않아야 한다. 추상화 벽을 사용하면 코드를 쉽게 고칠 수 있다.
하지만 코드를 쉽게 고치려고 ㅊ추상화 벽을 사용하는 것은 아니다. 추상화 벽은 팀 간에 커뮤니케이션 비용을 줄이고, 복잡한 코드를 명확하게 하기 위해 전략적으로 사용해야 한다.

#### 앞에서 고친 코드는 직접 구현에 더 가깝다.

첫 번째 패턴인 직접 구현 패턴을 다시 봤다. 데이터 구조를 바꿨더니 함수 대부분이 한 줄짜리 코드가 되었다.
하지만 코드 줄 수는 중요하지 않다. 중요한 것은 코드가 적절한 구체화 수준과 일반화가 되어 있는지이다.
일반적으로 한 줄 짜리 코드는 여러 구체와 수준이 섞일 일이 없기 때문에 좋은 코드라는 표시다.

<br>

### 패턴 3: 작은 인터페이스

작은 인터페이스 패턴은 새로운 코드를 추가할 위치에 관한 것이다.
인터페이스를 최소화하면 하위 계층에 불필요한 기능이 쓸데 없이 커지는 것을 막을 수 있다.

마케팅팀에서 장바구니에 제품을 많이 담은 사람이 시계를 구입하면 10% 할인해주려고 한다.

시계 할인 마케팅을 구현하기 위한 두 가지 방법이 있다.

하나는 추상화 벽에 구현하는 방법이고, 다른 하나는 추상화 벽 위에 있는 계층에 구현하는 방법이다. 마케팅팀이 사용해야 하므로 추상화 벽 아래에 구현할 수 없다.

추상화 벽 계층에 있으면 해시 맵 데이터 구조로 되어 있는 장바구니에 접근할 수 있다. 하지만 같은 계층에 있는 함수를 사용할 수 없다.

추상화 벽 위에 만들면 해시 데이터 구조를 직접 접근할 수 없다. 추상화 벽에 있는 함수를 사용해서 장바구니에 접근해야 한다.

#### 추상화 벽 위에 있는 계층에 구현하는 것이 더 좋다.

시계 할인 마케팅 관련 코드는 두 번째 방법인 추상화 벽 위에 있는 계층에 만드는 것이 더 좋다.
여러 이유가 있지만, 추상화 벽 위에 있는 계층에 만드는 것이 더 직접 구현에 가깝다.
그래서 두 번째 방법이 더 좋다. 그리고 첫번째 방법을 사용하면 시스템 하위 계층 코드가 늘어나기 때문에 좋지 않다.

첫 번째 방법을 사용해도 추상화 벽을 잘 유지할 수 있다.
호출 화살표가 장벽을 건너지 않기 때문이다.
하지만 이 방법은 장벽의 또 다른 목적을 위반한다. 만드려는 코드는 마케팅을 위한 코드다.
그리고 마케팅팀은 반복문 같은 구체적인 구현에 신경쓰고 싶지 않다.
그러므로 첫 번째 방법으로 구현한 코드는 추상화 벽 아래 위치해야 하고 개발팀에서 관리해야 한다.
만약 첫번째 방법으로 구현하면 마케팅팀에서 코드를 바꾸고 싶을 때 개발팀에 이야기해야한다.

추상화 벽에 구현하는 방법은 다른 문제가 있다. 추상화 벽에 만드는 함수는 개발팀과 마케팅팀 사이에 계약이라고 할 수 있다.
추상화 벽에 새로운 함수가 생긴다면 계약이 늘어나는것이고, 변경이 생기면 계약에 사용하는 용어를 서로 맞춰야 하므로 시간이 많이 든다.

새로운 기능을 만들 때 하위 계층에 기능을 추가하거나 고치는 것보다 상위 계층에 만드는 것이 작은 인터페이스 패턴이다.

작은 인터페이스 패턴을 사용하면 하위 계층을 고치지 않고 상위 계층에서 문제를 해결할 수 있다.

작은 인터페이스 패턴은 추상화 벽뿐만 아니라 모든 계층에 적용할 수 있는 패턴이다.

__이번에는 마케팅팀이 장바구니에 제품을 담을 때마다 로그를 남겨달라고 개발팀에 요청했다.__

add_item() 함수에 로그를 남기는 함수 코드를 넣으려고 한다. 로그를 남기는 함수는 액션이므로 add_item()을 사용하는 함수가 모드 액션이 되면서 액션이 퍼져나간다.
그럼 테스트가 더 어려워진다.

logAddToCart() 함수에 대한 두 가지 사실을 알았다.
하나는 logAddToCart() 함수는 액션이라는 사실과 logAddToCart() 함수는 추상화 벽 위에 있어야 한다는 점이다.

여기서는 add_item_to_cart() 함수가 로그를 남길 좋은 곳인 것 같다.

add_item_to_cart() 함수는 장바구니에 제품을 담을 때 호출하는 핸들러 함수다.

이 함수는 사용자가 장바구니에 제품을 담는 의도를 정확히 반영하는 위치다. 그리고 이 함수는 이미 액션이다.
그래서 이 함수에 logAddToCart() 함수를 추가하는 것이 좋다.

잘못된 위치에 함수를 놓아 액션이 퍼져나갈 뻔 했다. 다행히 좋은 위치를 찾았다.

작은 인터페이스 패턴을 사용하면 깨끗하고 단순하고 믿을 수 있는 인터페이스에 집중할 수 있다.

그리고 감춰진 ㅗㅋ드의 나머지 부분을 대신하는 코드로 사용할 수 있다. 또 인터페이스가 많아져서 생기는 불필요한 변경이나 확장을 막아준다.

<br>

### 패턴 3: 작은 인터페이스 리뷰

계층형 설계에서 완전한 추상화 벽과 최소한의 인터페이스 사이에 유연하게 조율해야 한다.

아래는 추상화 벽을 작게 만들어야 하는 이유다.

1. 추상화 벽에 코드가 많을수록 구현이 변경되었을 때 고쳐야할 것이 많다.
2. 추상화 벽에 있는 코드는 낮은 수준의 코드이기 때문에 더 많은 버그가 있을 수 있다.
3. 낮은 수준의 코드는 이해하기 더 어렵다
4. 추상화 벽에 코드가 많을수록 팀 간 조율해야 할 것도 많아진다.
5. 추상화 벽에 인터페이스가 많으면 알아야할 것이 많아 사용하기 어렵다.

상위 계층에 어떤 함수를 만들 때 가능한 현재 계층에 있는 함수로 구현하는 것이 작은 인터페이스를 실천하는 방법이다.
함수가 하려는 목적을 잘 파악하고 어떤 계층에 구현하는 것이 적합할지 생각해보세요.
일반적으로 그래프에서 상위에 계층에 구현하는 것이 좋다.

추상화 벽을 개선하는 데 작은 인터페이스를 사용했지만 사실 모든 계층에서 사용할 수 있다.
이상적인 계층은 더도 덜도 아닌 필요한 함수만 가지고 있어야 한다.
함수는 바뀌어도 안되고 나중에 더 늘어나도 안된다.
계층이 가진 함수는 완전하고, 적고, 시간이 지나도 바뀌면 안된다. 이것이 작은 인터페이스가 전체 계층에 사용되는 이상적인 모습

그런데 이는 매우 힘들고 이를 이루려는 노력이 중요하다.

함수의 목적에 맞는 계층이 어디인지 찾는 감각을 기르는 것이 가장 중요하다.

### 패턴 4. 편리한 계층

편리한 계층은 앞에 3개의 패턴과는 다르게 좀 더 현실적이고 실용적이다.

강력한 추상화 계층은 만들기 어렵다. 시간이 지나면 열심히 만든 추상화 벽이 크게 도움이 되지 않는다고 느낄 수 있다.
추상화 계층을 높게 만드는 것은 매우 어려운 일이다.

추상화는 가능한 일과 불가능한 일의 차이를 나타내기도 한다. 

비즈니스 문제를 해결하기 위해 일하고 있는 개발자로서 이처럼 거대한 추상 계층을 만들 시간적 여유가 없을 수 있다.
비즈니스는 기다려주지 않는다.

지금 코드가 괜찮다면 설계는 멈춰도 된다는 것이 편리한 계층 패턴이다.
반복문은 감싸지 않고 그대로 두고 호출 화살표가 조금 길어지거나 계층이 다른 계층과 섞여도 그대로 두는 것이다.

그러다 구체적인 것을 너무 많이 알아야 하거나, 코드가 지저분하다고 느끼면 다른 패턴을 사용해야 한다.

역시 설계는 트레이드 오프....다...

<br>

### 그래프로 알 수 있는 코드에 대한 정보는 무엇이 있을까?

코드에서는 소프트웨어의 기능적 요구사항을 확인할 수 있다.

호출 그래프의 구조를 통해 아래 비기능적 요구사항을 확인할 수 있다.

1. 유지보수성: 요구사항이 바뀌었을 때 가장 쉽게 고칠수 있는 코드는 어떤 코드인가요?
2. 테스트성: 어떤 것을 테스트하는 것이 가장 중요한가요?
3. 재사용성: 어떤 함수가 재사용하기 좋은가요?

### 그래프의 가장 위에 있는 코드가 고치기 가장 쉽다.

정말 심플하게 생각하면 의존성이 적어서 영향력이 적다..

가장 낮은 계층은 여러 계층에 영향을 준다. 그러므로 시간이 지나도 변하지 않는 코드는 젤 아래 계층에 있어야 한다.

### 아래 계층에 있는 코드는 테스트가 중요하다.

많은 코드가 가장 아래에서 잘 동작하는 코드에 의존한다. 그래서 가장 아래에 있는 코드를 테스트하는 것이 중요하다.

코드를 잘 만들고 있다면, 자주 바뀌는 코드는 위로 올리고 더 안정적인 코드는 아래에 둬야 한다.

제대로 만들었다면 가장 아래에 있는 코드보다 가장 위에 있는 코드가 더 자주 바뀐다.
즉 자주 바뀌므 로 테스트도 오래가지 않는다.

테스트도 만드려면 결국 비용이다. 

패턴을 사용하면 테스트 가능성에 맞춰 코드를 계층화할수 있다.
하위 계층으로 코드를 추출하거나 상위 계층에 함수를 만드는 일은 테스트의 가치를 결정한다.

### 아래에 있는 코드가 재사용하기 더 좋다.

### 그래프가 코드에 대해 알려주는 것.

1. 유지보수성
   * 위로 연결된 것이 적은 함수가 바꾸기 쉽다.
   * 자주 바뀌는 코드는 가능한 위쪽에 있어야 한다.
2. 테스트 가능성
   * 위쪽으로 많인 연결된 함수를 테스트하는 것이 더 가치 있다.
   * 아래쪽에 이쓴 함수를 테스트하는 것이 위쪽에 있는 함수를 테스트하는 것보다 가치가 있다.
3. 재사용성
   * 아래ㅉ고에 함수가 적을 수록 더 재사용하기 좋다.
   * 낮은 수준의 단계로 함수를 빼내면 재사용성이 더 높아진다.

그래도.. 비즈니스 로직 테스트가 제일 중요하지 않을까..?라고 생각해본다.

