package chapter_3.section_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {

    /**
     * 해당 메서드를 리팩토링하는 과정을 거친다.
     */

    public static String processFile()throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException{ //동작을 파라미터로 넘긴다 -> 동작 파라미터화
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }

    public static void main(String[] args) throws IOException {

        processFile((BufferedReader br) -> br.readLine()); //한 행을 처리
        processFile((BufferedReader br) -> br.readLine() + br.readLine()); //두 행을 처리
    }
}
