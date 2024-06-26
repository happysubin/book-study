package com.java.network.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleEchoServer {

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)){
            System.out.println("Waiting for connection.....");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                // Traditional implementation
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Client request: " + inputLine);
                    out.println(inputLine +" 추가 응답");
                }

                //아래 코드가 while loop 문 대신 사용할 수 있는 코드다.
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Simple Echo Server Terminating");
    }
}
