package tobyspring.vol1.callbacktemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Calculator {

    public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initValue) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
            T res = initValue;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        }
        catch (IOException e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(br != null){
                try{
                    br.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer calSum(String filePath) throws IOException {
        LineCallback sumCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.parseInt(line);
            }
        };
        return lineReadTemplate(filePath, sumCallback,0);
    }

    public Integer calMultiply(String filePath) throws IOException {
        LineCallback multiplyCallback = new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.parseInt(line);
            }
        };
        return lineReadTemplate(filePath, multiplyCallback,1);
    }


    public String concatenate(String filePath) throws IOException {
        LineCallback multiplyCallback = new LineCallback<String>() {
            @Override
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        };
        return lineReadTemplate(filePath, multiplyCallback,"");
    }
}
