package com.java.network.chapter04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) {
        new WebServer();
    }

    public WebServer() {
        System.out.println("WebServer Started");
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            while(true) {
                System.out.println("Waiting for client request");
                Socket remote = serverSocket.accept();
                System.out.println("Connection made");
                new Thread(new ClientHandler(remote)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
