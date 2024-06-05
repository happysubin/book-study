package com.java.network.chapter01;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.security.*;
import java.security.cert.CertificateException;

public class SSLServerSocket {

    static ServerSocket serverSocket = null;

    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("JKS");

        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        keyStore.load(new FileInputStream(currentDir + "/src/main/resources/keystore.jks"), "1q2w3e4r".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "1q2w3e4r".toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
        //SSLServerSocketFactory ssf = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        try {
            ServerSocket serverSocket = ssf.createServerSocket(8000);
            serverSocket = serverSocket;
            System.out.println("SSLServerSocket Started");
            try(Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println("Client socket created");
                String line = null;
                while (((line = br.readLine()) != null)) {
                    System.out.println(line);
                    out.println(line);
                }
                br.close();
                System.out.println("SSLServerSocket Terminated");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
