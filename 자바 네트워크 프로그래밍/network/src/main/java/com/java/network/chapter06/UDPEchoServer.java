package com.java.network.chapter06;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoServer {

    public static void main(String[] args) {
        int port = 9000;
        System.out.println("UDP Echo Server Started");
        try (DatagramChannel channel = DatagramChannel.open()) {
            DatagramSocket socket = channel.socket();
            InetSocketAddress address = new InetSocketAddress(port);

            socket.bind(address);
            ByteBuffer buffer = ByteBuffer.allocateDirect(65507);

            while(true) {
                SocketAddress client = channel.receive(buffer); //버퍼를 사용
                buffer.flip();

                buffer.mark();
                System.out.print("Received: [");
                StringBuilder message = new StringBuilder();

                while (buffer.hasRemaining()) {
                    message.append((char) buffer.get());
                }
                System.out.println(message + "]");
                buffer.reset();

                channel.send(buffer, client);
                System.out.println("Sent: [" + message + "]");
                buffer.clear();
            }


        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();

        }
        System.out.println("UDP Echo Server Terminated");

    }
}
