package com.java.network.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SimpleEchoClientCopy {

    public static void main(String args[]) {
        System.out.println("Simple Echo Client");

        try {
            System.out.println("Waiting for connection.....");
            InetAddress localAddress = InetAddress.getLocalHost();

            try (Socket clientSocket = new Socket(localAddress, 6000);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.println("Connected to server");
                Scanner scanner = new Scanner(System.in);

                // Traditional implementation
                while (true) {
                    System.out.print("Enter text: ");
                    String inputLine = scanner.nextLine();
                    if ("quit".equalsIgnoreCase(inputLine)) {
                        break;
                    }
                    out.println(inputLine);

                    //아래 코드로 반복문 대체 가능
                    // Functional implementation
//                Supplier<String> socketInput = () -> {
//                    try {
//                        return br.readLine();
//                    } catch (IOException ex) {
//                        return null;
//                    }
//                };
//                Stream<String> stream = Stream.generate(socketInput);
//                stream
//                        .map(s -> {
//                            System.out.println("Client request: " + s);
//                            out.println(s);
//                            return s;
//                        })
//                        .allMatch(s -> s != null);

                    String response = br.readLine();
                    System.out.println("Server response: " + response);
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

