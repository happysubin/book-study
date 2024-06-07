package com.java.network.chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerChannelTimeClient {

    public static void main(String[] args) {
         SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 7070);

         try (SocketChannel socketChannel = SocketChannel.open(socketAddress)) {
             ByteBuffer buf = ByteBuffer.allocate(64);
             int bytesRead = socketChannel.read(buf);
             while (bytesRead > 0) {
                 buf.flip();
                 while (buf.hasRemaining()) {
                     System.out.print((char) buf.get());
                 }
                 System.out.println();
                 bytesRead = socketChannel.read(buf);
             }
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }

    private static void displayByteBuffer(ByteBuffer byteBuffer) {
        System.out.println("Capacity: " + byteBuffer.capacity()
                + " limit: " + byteBuffer.limit()
                + " position: " + byteBuffer.position());
    }

}
