package chapter_8.hook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class CoffeeWithHook extends CaffeineBeverageWithHook{

    @Override
    protected void addCondiments() {
        System.out.println("우유와 설탕을 추가하는 중");
    }

    @Override
    protected void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    protected boolean customerWantsCondiments() {
        String answer = getUserInput();

        if(answer.toLowerCase().startsWith("y")){
            return true;
        }
        return false;
    }

    private String getUserInput() {
        String answer = null;
        System.out.println("커피에 우유와 설탕을 넣을가? (y/n) ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            answer = br.readLine();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(answer == null){
            return "no";
        }
        return answer;
    }
}
