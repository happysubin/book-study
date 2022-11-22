# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답 및 초기 설정
* 성공
* 사용자 요청이 발생 할 때까지 대기 상태에 있도록 지원하는 역할은 자바에 포함되어 있는 ServerSocket 클래스가 담당.
* WebServer 클래스는 ServerSocket에 사용자 요청이 발생하는 순간 클라이언트와 연결을 담당하는 Socket을 RequestHandler에 전달하면서 새로운 스레드를 실행하는 방식으로 멀티스레드 프로그래밍을 지원하고 있다.
* RequestHandler 클래스는 Thread를 상속하고 있으며, 사용자의 요청에 대한 처리와 응답에 대한 처리를 담당하는 가장 중심이 되는 클래스다.
* 프로그래밍을 할 때 좋은 습관 중의 하나는 프로그래밍 실행 중 발생하는 로그 메시지를 주의 깊게 살펴보는 것이다.
* 디버깅하니 실제로 들어오는 스트림은 SocketInputStream이다.

### 요구사항 2 - get 방식으로 회원가입
*  Get 방식으로 회원 객체 생성만 진행

### 요구사항 3 - post 방식으로 회원가입
* br.readLine()은 줄바꿈 문자가 나오지 않으면 Lock에 걸려서 대기함.
* br.read()와 Content-Length 헤더를 사용해서 해결.
* Content-Length가 어디까지 body가 인지 알려줌. 즉 바디의 크기를 알려줌.

### 요구사항 4 - redirect 방식으로 이동

* Location 헤더를 사용해서 리다이렉트함.
* HTTP 공부를 다시 해야할 것 같다고 느낌.
* 리팩토링하면서 위에 적은 br.readLine()에서 헤맴.

### 요구사항 5 - cookie
* Set-Cookie를 사용함.
* 200번대로 내려주는데 이러면 Redirect가 안됨.
* 차라리 300번대로 내리고 Redirect하는게 맞는듯. 수정하자.
* /user/** 경로에서 쿠키는 살아있음. /index.html로 가면 쿠키가 없어짐.

### 요구사항 6 - stylesheet 적용
* Accept: text/css 와 */* 방식을 사용해서 빠르게 해결. 다형성을 지키지는 못함..
* 필요한 css, js 파일마다 http 요청을 서버로 보낸다.

### heroku 서버에 배포 후
* 