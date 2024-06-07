package com.java.network.chapter03;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Chapter03 {

    public static void main(String[] args) {
        creatingBuffers();
//        getWebPage();
//        bulkTransferExample();
    }

    private static void creatingBuffers() {
        String contents = "Book";
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(contents.getBytes());


        // 뷰는 다른 버퍼로 데이터를 미러링한다.
        //한 버퍼의 수정이 다른 버퍼에 영향을 주는 것을 보여주기 위해 중복된 첫번째 문자는 'L'로 변경
        ByteBuffer duplicateBuffer = buffer.duplicate();
        duplicateBuffer.put(0,(byte)0x4c); // 'L'
        System.out.println("buffer: " + buffer.get(0));
        System.out.println("duplicateBuffer: " + duplicateBuffer.get(0));
        System.out.println();

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer(); //읽기 전용
        System.out.println("Read-only: " + readOnlyBuffer.isReadOnly());
    }

    private static void displayBuffer(IntBuffer buffer) {
        int arr[] = new int[buffer.position()];
        buffer.rewind();
        buffer.get(arr);
        for (int element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
        for (int i = 0; i < buffer.position(); i++) {
            System.out.print(buffer.get(i) + " ");
        }
        System.out.println();
    }

    private static void bulkTransferExample() {
        int[] arr = {12, 51, 79, 54};
        IntBuffer buffer = IntBuffer.allocate(6);
        System.out.println(buffer);

        buffer.put(arr);
        System.out.println(buffer);
        displayBuffer(buffer);

        int length = buffer.remaining();
        buffer.put(arr, 0, length);
        System.out.println(buffer);
        displayBuffer(buffer);
    }
}
