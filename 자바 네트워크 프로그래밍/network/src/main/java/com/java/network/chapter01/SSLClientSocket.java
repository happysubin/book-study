package com.java.network.chapter01;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class SSLClientSocket {

    public static void main(String[] args) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        System.out.println("SSLClientSocket Started");

        KeyStore keyStore = KeyStore.getInstance("JKS");

        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        keyStore.load(new FileInputStream(currentDir + "/src/main/resources/keystore.jks"), "1q2w3e4r".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "1q2w3e4r".toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        InetAddress localHost = InetAddress.getLocalHost();
        try (Socket socket =  sslSocketFactory.createSocket(localHost, 8000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter text: ");
                String inputLine = scanner.nextLine();
                if ("quit".equalsIgnoreCase(inputLine)) {
                    break;
                }
                out.println(inputLine);
                System.out.println("Server response: " + br.readLine());
            }
            System.out.println("SSLServerSocket Terminated");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
