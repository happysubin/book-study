package util.part_5;

import org.junit.Assert;
import org.junit.Test;
import part_3.webserver.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class HttpResponseTest {
    private String testDirectory  = "./src/test/resources/";

    @Test
    public void request_Get() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        Assert.assertEquals("GET", request.getMethod());
        Assert.assertEquals("/user/create", request.getPath());
        Assert.assertEquals("keep-alive", request.getHeader("Connection"));
        Assert.assertEquals("bin", request.getParameter("userId"));
    }

    @Test
    public void request_Post() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        Assert.assertEquals("POST", request.getMethod());
        Assert.assertEquals("/user/create", request.getPath());
        Assert.assertEquals("keep-alive", request.getHeader("Connection"));
        Assert.assertEquals("javajigi", request.getParameter("userId"));
    }
}
