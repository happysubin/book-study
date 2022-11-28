package util.part_5;

import org.junit.Assert;
import org.junit.Test;
import part_5.webserver.HttpMethod;
import part_5.webserver.HttpRequest;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class HttpRequestV2Test {
    private String testDirectory  = "./src/test/resources/";

    @Test
    public void request_Get() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        Assert.assertEquals(HttpMethod.GET, request.getMethod());
        Assert.assertEquals("/user/create", request.getPath());
        Assert.assertEquals("keep-alive", request.getHeaders("Connection"));
        Assert.assertEquals("bin", request.getParams("userId"));
    }

    @Test
    public void request_Post() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        Assert.assertEquals(HttpMethod.POST, request.getMethod());
        Assert.assertEquals("/user/create", request.getPath());
        Assert.assertEquals("keep-alive", request.getHeaders("Connection"));
        Assert.assertEquals("javajigi", request.getParams("userId"));
    }



}
