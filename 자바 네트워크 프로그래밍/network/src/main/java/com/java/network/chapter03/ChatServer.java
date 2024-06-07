package com.java.network.chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 다중 클라이언트 처리
 */

public class ChatServer {

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        System.out.println("Chat Server started");
        try {

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9090));

            boolean running = true;

            while(running) {
                System.out.println("Waiting for request ... ");
                SocketChannel socketChannel = serverSocketChannel.accept();

                System.out.println("Connected to Client");

                String message;
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    System.out.println("> ");
                    message = scanner.nextLine();

                    if(message.equalsIgnoreCase("quit")) {
                        HelperMethods.sendMessage(socketChannel, "Server terminating");
                        running = false;
                        break;
                    }
                    else {
                        HelperMethods.sendMessage(socketChannel, message);
                        System.out.println("Waiting for message from client ...");
                        System.out.println("Message: " + HelperMethods.receiveMessage(socketChannel));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
