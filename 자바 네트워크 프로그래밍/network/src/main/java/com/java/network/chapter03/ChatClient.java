package com.java.network.chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {
    public ChatClient() {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 9090);

        try (SocketChannel socketChannel = SocketChannel.open(address);) {

            System.out.println("Connected to Chat Server");
            String message;
            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("Waiting for message from the server ...");
                System.out.println("Message: " + HelperMethods.receiveMessage(socketChannel));
                System.out.print("> ");
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("quit")) {
                    HelperMethods.sendMessage(socketChannel, "Client terminating");
                    break;
                }
                // Send message
                HelperMethods.sendMessage(socketChannel, message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
