package tobyspring.vol1.callbacktemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Calculator {

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomethingWithReader(br);
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
        BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.parseInt(line);
                }
                return sum;
            }
        };
        return fileReadTemplate(filePath, sumCallback);
    }

    public Integer calMultiply(String filePath) throws IOException {
        BufferedReaderCallback callback = new BufferedReaderCallback() {
            @Override
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer sum = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum *= Integer.parseInt(line);
                }
                return sum;
            }
        };
        return fileReadTemplate(filePath, callback);
    }
}
