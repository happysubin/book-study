package com.java.network.chapter01;

import java.io.IOException;
import java.net.*;
import java.util.Date;

public class MultiCastServer {

    //이 서버는 메시지를 브로드캐스트한다.
    public static void main(String[] args) {
        System.out.println("Multicast Time Server");
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket();

            while(true) {
                //현재 날짜와 시간을 유지하는 바이트 배열을 생성하기 위해 무한 루프 사용
                String dateText = new Date().toString();
                byte[] buffer = new byte[256];
                buffer = dateText.getBytes();

                InetAddress group = InetAddress.getByName("224.0.0.0");
                DatagramPacket packet;
                packet = new DatagramPacket(buffer, buffer.length, group, 8888);
                serverSocket.send(packet);
                System.out.println("Time sent: " + dateText);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
