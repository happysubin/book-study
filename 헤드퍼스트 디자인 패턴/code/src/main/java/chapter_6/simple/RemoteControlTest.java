package chapter_6.simple;

public class RemoteControlTest {

    public static void main(String[] args) { //커맨드 패턴에서 클라이언트에 해당하는 부분
        SimpleRemoteControl remote = new SimpleRemoteControl(); //remote 변수가 invoker 역할을 한다.
        Light light = new Light(); //리시버 생성
        LightOnCommand lightOnCommand = new LightOnCommand(light); //커맨드 객체 생성. 리시버를 전달.

        remote.setCommand(lightOnCommand);
        remote.buttonWasPressed();
    }
}
