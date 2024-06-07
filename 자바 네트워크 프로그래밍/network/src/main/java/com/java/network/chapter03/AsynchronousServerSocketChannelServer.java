package com.java.network.chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AsynchronousServerSocketChannelServer {

    public AsynchronousServerSocketChannelServer() {
        System.out.println("Asynchronous Server Started");
        try (AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open()) {
            InetSocketAddress localhost = new InetSocketAddress("localhost", 5000);
            serverChannel.bind(localhost);

            System.out.println("Waiting for client to connect... ");
            Future acceptResult = serverChannel.accept();

            try (AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) serverChannel.accept()) {
                while((clientChannel != null) && (clientChannel.isOpen())) {
                    ByteBuffer buffer = ByteBuffer.allocate(32);
                    Future result = clientChannel.read(buffer);
                    // Technique 1
                    while (!result.isDone()) {
                        // do nothing
                    }
                    // Technique 2
//                    result.get();
                    // Technique 3
//                    result.get(10, TimeUnit.SECONDS);
                    buffer.flip();

                    String message = new String(buffer.array()).trim();
                    System.out.println(message);
                    if (message.equals("quit")) {
                        break;
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        new AsynchronousServerSocketChannelServer();
    }
}
