package chapter_8.hook;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TeaWithHook extends CaffeineBeverageWithHook{

    @Override
    public void addCondiments() {
        System.out.println("레몬을 추가하는 중");
    }

    @Override
    public void brew() {
        System.out.println("찻잎을 우려내는 중");
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
