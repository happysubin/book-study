package com.java.network.chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.http.HttpClient;

public class HTTPClient {

    public static void main(String[] args) {
        new HTTPClient();
    }
    
    public HTTPClient() {
        System.out.println("HTTP Client Started!");
        
        try {
            InetAddress localhost = InetAddress.getLoopbackAddress();
            Socket connection = new Socket(localhost, 8080);
            try (OutputStream out = connection.getOutputStream();
                 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ) {
                sendGet(out);
                System.out.println(getResponse(in));
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendGet(OutputStream out) {
        try {
            // Send Request
            out.write("GET /index HTTP/1.0\r\n".getBytes());
            // Send Header
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private String getResponse(BufferedReader in) {
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            return response.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
