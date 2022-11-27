package part_3.webserver;

import part_3.util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


/**
 * 응답 전문가 클래스
 */
public class HttpResponse {

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.dos =  new DataOutputStream(outputStream);
    }

    public void forward(String path) throws IOException {
        Path file = new File("./webapp" + path).toPath();
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        if(path.endsWith(".html")){
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        }

        else if(path.endsWith(".css")){
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
        }

        else if(path.endsWith(".js")){
            dos.writeBytes("Content-Type: */*;charset=utf-8\r\n");
        };
        byte[] body = Files.readAllBytes(file);
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        HttpResponseUtils.responseBody(dos, body);
    }

    public void sendRedirect(String redirectPath) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
        dos.writeBytes("Location: " + redirectPath + " \r\n");

        if(headers.get("Set-Cookie") != null){
            dos.writeBytes("Set-Cookie: " + headers.get("Set-Cookie") + "; Path=/\r\n");
        }
        dos.writeBytes("\r\n");
    }

    public void forwardBody(String bodyString) throws IOException {
        byte[] body = bodyString.getBytes();
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        HttpResponseUtils.responseBody(dos, body);
    }

    public void addHeader(String field, String value){
        this.headers.put(field, value);
    }
}
