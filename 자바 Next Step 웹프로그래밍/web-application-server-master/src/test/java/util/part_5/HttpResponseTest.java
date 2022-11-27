package util.part_5;

import org.junit.Assert;
import org.junit.Test;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.*;

public class HttpResponseTest {
    private String testDirectory  = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseRedirect() throws Exception{
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.forward("/index.html");
    }

    @Test
    public void responseCookies() throws Exception{
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.forward("/index.html");
    }


    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
