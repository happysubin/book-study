#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.

1. WebServerLauncher의 main 함수를 실행.
2. 탐갯 서버 객체를 생성하고, 포트를 설정한다.
3. 또한 화면을 보여줄 html의 절대 경로를 설정한다.
4. was인 탐캣이 시작하고, 소켓을 만들어 사용자의 연결을 기다린다.
5. was는 서블릿 컨테이너 + 웹 서버인데, 탐캣이 시작하면 서블릿 컨테이너 초기화가 시작된다.
6. 즉 ServletContext를 생성한다. 그리고 해당 객체의 초기화가 진행된다.
7. 그러면 서블릿 컨테이너 초기화 과정에서 ServletContextListener의 contextInitialized가 실행된다.
8. 이때 데이터베이스와 연결을 하고 테이블을 생성한다.
9. loadOnStartUp 설정을 통해 @WebServlet 애노테이션이 달린 서블릿 객체들을 사용자 요청전에 미리 띄운다.
10. 디스패쳐 서블릿의 init 메서드가 실행되고 RequestMapping 객체를 생성한다.
11. initMapping() 메서드를 호출하고 URL과 컨트롤러를 매핑한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.

* 서블릿이 접근하기 전에 ResoureceFilter와 CharacterEncodingFilter doFilete 메서드가 실행된다.
* ResoureceFilter는 해당 요청이 정적자원이 아닌 요청은 서블릿으로 요청을 위임한다.
* 요청 처리는 프론트 컨트롤러 패턴의 디스패쳐 서블릿으로 매핑된다.
* 그럼 RequestMapping 객체를 통해 URL과 매핑되는 컨트롤러인 HomeController의 execute 메서드를 실행한다.
* 반환 값은 ModelAndView이고 JspView, JsonView에 맞춰 화면을 렌더링한다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.

* ShowController는 서블릿의 초기화 과정에서 URL과 매핑되며 RequestMapping 안에 URL과 같이 1개만 존재한다.
* 즉 싱글톤 패턴인데 여러 쓰레드에서 접근하면 필드 값으로 있는 Question 객체와 List<Answer> 객체에서 동시에 많은 쓰레드가 접근한다.
* 그러므로 동시성 이슈가 발생한다. 따라서 인스턴스 변수가 아닌 지역 변수로 해결해야 한다.
