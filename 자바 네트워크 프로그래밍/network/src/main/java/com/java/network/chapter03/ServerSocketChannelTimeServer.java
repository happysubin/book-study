package com.java.network.chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ServerSocketChannelTimeServer {

    /**
     * flip 메서드의 역할
     * flip 메서드는 다음과 같은 작업을 수행합니다:
     *
     * limit을 position의 현재 값으로 설정: limit을 현재 position 값으로 설정하여 현재 위치까지의 데이터만 읽을 수 있도록 합니다.
     * position을 0으로 재설정: position을 0으로 설정하여 읽기 작업이 버퍼의 처음부터 시작되도록 합니다.
     * 이 메서드는 데이터를 쓰는 모드에서 읽는 모드로 전환할 때 유용합니다. 예를 들어, 데이터를 버퍼에 쓴 후 읽으려고 할 때, flip 메서드를 호출하여 읽기 작업을 준비합니다.
     * @param args
     */

    public static void main(String[] args) {
        System.out.println("Time Server Started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); //서버 소켓 채널 인스턴스 생성
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));

            while(true) { //요청 수신까지 무한 반복
                System.out.println("Waiting for request ...");
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel != null) {
                    String dateAndTimeMessage = "Date: " + new Date(System.currentTimeMillis());
                    ByteBuffer buffer = ByteBuffer.allocate(64); //64 바이트 크기
                    buffer.put(dateAndTimeMessage.getBytes());
                    buffer.flip(); //limit을 현재의 포지션으로 설정하고 position을 0으로 만든다.
                    while(buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }
                    System.out.println("Sent: " + dateAndTimeMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
