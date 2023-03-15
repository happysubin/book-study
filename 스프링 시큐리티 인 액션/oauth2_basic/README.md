# 스프링 시큐리티를 사용한 oauth2 실습 레포지토리

* oauth2Login() 인증 메서드를 사용해 필터 체인에 새 인증 필터를 추가했다.
* 실습은 깃허브가 우리 권한 부여 서버임을 설정하고 진행했다.

## ClientRegistration
* ClientRegistration 인터페이스는 oauth2 아키텍처의 클라이언트를 나타내며 다음과 같은 클라이언트의 모든 세부 정보를 정의해야 한다.
  * 클라이언트 ID와 시크릿
  * 인증에 이용되는 그랜트 유형
  * 리디렉션 URI
  * 범위
 
참고로 스프링 시큐리티는 CommonOAuth2Provider라는 클래스를 정의하고 있다.
이를 사용해 가장 일반적인 공급자에 대한 인증에 이용할 수 있는 ClientRegistration 인스턴스를 부분적으로 정의한다.

## ClientRegistrationRepository

* ClientRegistrationRepository는 ClientRegistration 세부 정보를 얻는다. 또한 UserDetail 서비스와 비슷하다.
* ClientRegistrationRepository 객체는 등록 ID로 ClientRegistration을 찾는다.
* 인증 필터는 ClientRegistrationRepository 객체에서 권한 부여 서버 클라이언트 등록에 대한 세부 정보를 얻는다.
* ClientRegistrationRepository 객체에는 하나 이상의 ClientRegistration 객체가 있다.