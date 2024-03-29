## chapter03. 순진한 트리

요구사항은 독자들이 답글을 달 수 있고 심지어 답글에 대한 답글도 달 수 있기 때문에, 가지를 뻗어 깊게 확장하는 글 타래를 형성할 수 있다.

이런 답글 타래를 추적하기 위해, 각 답글은 답글을 다는 대상 글에 대한 참조를 가지도록 하는 단순한 해법을 선택했다.


> parent.sql 참조

그러나 곧 답글의 긴 타래를 하나의 SQL로 불러오기 어렵다는 점이 명확해진다.
단지 고정된 깊이까지만, 즉 바로 아래 자식 또는 그아래 손자뻘 되는 글까지 얻어낼 수 있다.
그렇지만 글타래는 깊이가 무제한이다. 특정 글타래에 대해 모든 답글을 얻기 위해서는 많은 SQL 쿼리를 실행해야 할 것이다.

생각할 수 있는 다른 방법은 모든 글을 불러온 다음, 학교에서 배운 전통적인 트리 알고리즘을 사용해 애플리케이션 메모리 안에서 트리 구조를 구성하는 것이다.

보통 기사에는 수백만개의 댓글이 달린다고 가정하자. 그렇다면 누군가 웹사이트를 볼 때마다 수백만 개의 답글을 정렬하는 것은 비효율적이다.

### 목표: 계층 구조 저장 및 조회하기

데이터가 재귀적 관계를 가지는 것은 흔한 일이다. 데이터는 트리나 계층적 구조가 될 수 있다.

계층적 데이터에서는, 개별 항목을 조회하는 경우, 전체 중 관련된 부분만 포함한 부분집합만 조회하는 경우 또는 전체를 조회하는 경우가 있을 것이다.

트리 데이터 구조를 가지는 예에는 다음과 같은 것이 포함된다.

* 조직도
  * 직원과 관리자의 관계는 트리 구조 데이터의 교과서적 예제다.
  * 조직도는 SQL에 대한 수많은 책과 글에 나타난다.
  * 조직도에서 직원은 관리자를 가지는데 트리 구조에서는 직원의 부모를 나타낸다.
  * 관리자 또한 직원이다
* 글타래: 답글에 대한 답글의 글타래에 트리가 사용될 수 있다. 이 트리에서 글의 자식은 답글이다

### 안티패턴: 항상 부모에 의존하기

책과 글에서 흔히 설명하는 초보적 방법은 parent_id 칼럼을 추가하는 것이다.

__이 설계는 인접 목록이라 불린다.__

#### 인접 목록에서 트리 조회하기

인접 목록은 많은 개발자들이 기본으로 선택하는 방법이지만 트리에서 필요한 가장 흔한 작업 중 하나인 모든 자식 조회하기를 제대로 못 한다면 안티패턴이 될 수 있다.

1. 답글과 그 답글의 바로 아래 자식은 비교적 간단한 쿼리로 얻을 수 있다. (join 1개)
2. 요구하는 답글의 깊이, 즉 단계가 깊어질 수록 쿼리가 매우 복잡해진다. (컬럼도 헷갈리고 조인도 많아짐)
3. 인접 목록에서 트리 구조를 조회하는 다른 방법은 글타래의 모든 행을 가져와서 트리처럼 보여주기 전에 애플리케이션에서 계층 구조를 만들어내는 것이다.
4. 다만 데이터베이스에서 애플리케이션으로 대량의 데이터를 가져오는 방식은 굉장히 비효율적. 

#### 인접 목록에서 트리 유지하기

트리에서 노드를 삭제하는 것은 복잡하다.
서브트리 전체를 삭제하려면 FK 제약 조건을 만족하기 위해 여러 번 쿼리를 날려 모든 자손을 찾은 다음, 가장 아래 단계뿌터 차례로 삭제하면서 올라가야 한다.

삭제할 노드의 자손을 현재 노드이ㅡ 부모로 이어 붙인다거나 이동하지 않고 항상 함께 삭제한다면, FK에 ON DELETE CASCADE 옵션을 주어 이를 자동화할 수 있다.

그러나 자식이 있는 노드를 삭제하면서 그 자식을 자신의 부모에 이어 붙인 다거나 또는 트리의 다른 곳으로 이동하고 싶은 경우에는, 먼저 자식들의 parent_id를 변경한 다음 원하는 노드를 삭제해야 한다.

매우 불편..

### 안티패턴 인식 방법

1. 트리에서 얼마나 깊은 단계를 지원해야 하지?
2. 트리 데이터 구조를 관리하는 코드를 건드리는게 겁나
3. 트리에서 고아 노드를 정리하기 위해 주기적으로 스크립트를 돌려야해

### 안티패턴이 합당한 경우

만약 인접 목록으로 계층적 데이터를 작업하는 데 이정도만으로도 충분하다면, 인접 목록은 적절한 방법이다. 

depth가 얕다면 그냥 사용해도 괜찮은 듯.

### 해법: 대안 트리 모델 사용

> 계층적 데이터를 저장하는 데는 인접 목록 모델 외에도 경로 열거, 중첩 집합, 클로저 테이블과 같은 몇 가지 대안이 있다.

#### 경로 열거

인접 목록의 약점 중 하나는 트리에서 주어진 노드의 조상들을 얻는 데 비용이 많이 든다는 것이다.

경로 열거 방법에서는 일련의 조상을 각 노드의 속성으로 저장해 이를 해결한다.

> 디렉토리 구조에서도 경료 열거 형태를 볼 수 있다. /user/local/lib/와 같은 UNIX 경로는 파일 시스템에서의 경로 열거다. usr은 local의 부모고, local은 lib의 부모다.

Comments 테이블에 parent_id 칼럼 대신, 긴 VARCHAR 타입의 path란 컬럼을 정의한다.

이 칼럼에 저장되는 문자열은 트리의 꼭대기부터 현재 행까지 내려오는 조상의 나열로 UNIX 경로와 비슷하다.
심지어 '/'를 구분자로 사용해도 된다.

> path-enum.sql 참고

![KakaoTalk_Photo_2024-01-29-21-30-11](https://github.com/happysubin/book-study/assets/76802855/de0d267c-a40e-401b-9b60-5d9e5cb4b5e8)

* 조상은 현재 행의 경로와 다른 행의 경로 패턴을 비교해 쉽게 조회할 수 있다.
* 새로운 노드를 삽입하는 방법은 인접 목록 모델에서와 비슷하다.
* 다른 노드를 수정하지 않고도 종단이 아닌 노드를 추가할 수 있다. (non-leaf)

단점은 데이터베이스는 경로가 올바르게 형성되도록 하거나 경로 값이 실제 노드에 대응되도록 강제할 수 없다.
경로 문자열을 유지하는 것은 애플리케이션 코드에 종속되며, 이를 검증하는 데는 비용이 많이 든다.

경로 열거를 사용하면, 구분자 사이의 요소 길이가 같은 경우, 데이터를 계층 구조에 따라 쉽게 정렬할 수 있다.

#### 중첩 집합

중첩 집합은 각 노드가 자신의 부모를 저장하는 대신 자기 자손의 집합에 대한 정보를 저장한다.
이 정보는 트리의 각 노드를 두 개의 수로 부호화해 나타낼 수 있는데, 여기서는 nsleft와 nsright로 부르겠다.

> nested-sets.sql 참고

nsleft 수는 모든 자식노드의 nsleft 수보다 작아야 하고, nsright는 모든 자식의 nsright 수보다 커야 한다.

이런 숫자는 comment_id 값과는 아무런 상관이 없다.

이 값을 할당하는 쉬운 방법 중 하나는, 트리를 깊이 우선 탐색(DFS)하면서 값을 하나씩 증가시켜가면서 할당하는 것인데, 
자손으로 한 단계씩 내려갈 때는 nsleft에 값을 할당하고 가지를 한 단계씩 올라올 때는 nsright에 값을 할당하는 것이다.

![KakaoTalk_Photo_2024-01-29-21-37-45](https://github.com/happysubin/book-study/assets/76802855/cd587eeb-c1b5-444c-86a7-415dc5774933)

* 중첩 집합 모델의 주요 강점 중 하나는, 자식을 가진 노드를 삭제했을 때 그 자손이 자동으로 삭제된 노드 부모의 자손이 된다는 것이다. 
* 그러나 자식이나 부모를 조회하는 것과 같이 인접 목록 모델에서는 간단했던 일부 쿼리가 중첩 집합 모델에서는 더욱 복잡해진다. 
* 주어진 노드 c1의 부모는 그 노드의 조상이지만 부모 노드와 자식 노드 사이에 다른 노드는 존재할 수 없다. 
* 따라서 부가적인 외부 조인을 사용해 c1의 조상이고 부모의 자손인 노드를 검색할 수 있다. 이런 노드를 찾지 못한 경우가 c1의 조상이자 직접적인 부모가 되는 것이다. 
* 중첩 집합 모델에서는 노드를 추가, 이동하는 것과 같은 트리 조작도 다른 모델을 사용할 때보다 복잡하다.
* 새로운 노드를 추가한 경우 새 노드의 왼쪽 값보다 큰 모든 노드의 왼쪽, 오른쪽 값을 다시 계산해야 한다.

중첩 집합 모델은 각 노드에 대해 조작하는 것보다는 서브트리를 쉽고 빠르게 조회하는 것이 중요할 때 가장 잘 맞는다.

노드를 추가하고 이동하는 것은 왼쪽, 오른쪽 값을 재계산해야 하기 때문에 복잡하다. 

__트리에 노드를 삽입하는 경우가 빈번하다면, 중첩 집합은 좋은 선택이 아니다.__

#### 클로저 테이블

클로저 테이블은 계층 구조를 저장하는 단순하는 우아한 방법이다.

클로저 테이블은 부모-자식 관계에 대한 경로만을 저장하는 것이 아니라, 트래의 모든 경로를 저장한다.

Comments 테이블에 더해 두 개의 칼럼을 가지는 TreePaths 테이블을 생성한다.
TreePaths 테이블의 각 칼럼은 Comments에 대한 FK다.

트리 구조에 대한 정보를 Comments 테이블에 저장하는 대신 TreePaths를 사용한다.
이 테이블에는 트리에서 조상/자손 관계를 가진 모든 노드 쌍을 한 행으로 저장한다.

__또한 각 노드에 대해 자기 자신을 참조하는 행도 추가한다.__

![KakaoTalk_Photo_2024-01-29-22-03-12](https://github.com/happysubin/book-study/assets/76802855/84403a50-d1b3-4b92-81e0-b73f1df0321e)

이 테이블에서 조상이나 자손을 가져오는 쿼리는 중첩 집합에서보다 훨씬 직관적이다.

* 새로운 종말 노드, 예를 들어 답글 #5에 새로운 자식을 추가하려면, 먼저 자기 자신을 참조하는 행을 추가한다.
* 그 다음 Tree Paths에서 답글 #5를 descendant로 참조하는 모든 행을 복사해, descendant를 새로운 답글 아이디로 바꿔 놓는다.
* 부모 노드를 삭제하고 난 후 자식들을 다른 부모 노드로 옮기기도 편리하다.
* 클로저 테이블 모델은 중첩 집합 모덻보다 직관적이다. 조상과 자손을 조회하는 것은 두 방법 모두 빠르고 쉽지만, 클로저 테이블이 계층구조 정보를 유지하기가 쉽다.
* 두 방법 모두 인접 목록이나 경로 열거 방법보다 자식이나 부모를 조회하기 편리하다.

그러나 부모나 자식 노드를 더 쉽게 조회할 수 있도록 TreePaths에 path_length 속성을 추가해 클로저 테이블을 개선할 수 있다.
자기자신에 대한 path_lenght는 0, 자식에 대한 path_length는 1, 손자에 대한 path_length는 2와 같은 식이다.


### 어떤 모델을 사용해야 하는가?

* 인접 목록을 많이 사용하지만, 조회에서 불리하다.
* 경로 열거는 브레드크럼을 사용자 인터페이스에 보여줄 때는 좋지만, 참조 정합성을 강제하지 못하고 정보를 중복 저장하기 때문에 깨지기 쉬운 구조다.
* 중첩 집합은 영리한 방법이다. 지나치게 영리한 것일 수도 있다. 역시 참조 정합성을 지원하지는 못한다. 트리를 수정하는 일은 거의 없고 조회를 많이 하는 경우 적합
* 클로저 테이블은 갖아 융튱성 있는 모델이고 한 노드가 여러 트리에 속하는 것을 허용하는 유일한 모델이다. 관계를 저장하기 위한 별도 테이블이 필요하다. 깊은 계층 구조를 인코딩하는 데는 많은 행이 필요하고, 계산을 줄이는 대신 저장 공간을 많이 사용하는 트레이드 오프가 발생한다.

용량 많이 사용해도 개인적으로 클로저 테이블 사용하는게 편하다고 생각.