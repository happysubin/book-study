package chapter_3.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputTest {

    private final static String PATH = "/Users/ansubin/Desktop/book-study/헤드퍼스트 디자인 패턴/code/src/main/java/chapter_3/io";

    public static void main(String[] args) {
        int c;

        try{
            InputStream in = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream(PATH + "/test.txt")));

            while((c = in.read()) >= 0){
                System.out.print((char)c);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
