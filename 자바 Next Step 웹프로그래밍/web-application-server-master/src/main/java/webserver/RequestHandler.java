package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;


import model.user.User;
import model.user.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }


    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int lineNum = 0;
            String firstLine = "";
            String line;
            while(!(line = br.readLine()).equals("") & line != null){
                log.info(line);
                if(lineNum == 0) firstLine = line;
                lineNum++;
            }

            String httpMethod = HttpRequestUtils.parseHttpMethod(firstLine);
            String htmlPath = HttpRequestUtils.parseHtmlPath(firstLine);

            if(!firstLine.contains(".html")){
                Map<String, String> userParamMap = HttpRequestUtils.parseQueryString(firstLine);
                User user = UserFactory.createUser(HttpRequestUtils.separateParamsAndRequestUri(firstLine));
                System.out.println("user.toString() = " + user.toString());

            }


            byte[] body = Files.readAllBytes(new File("./webapp" + htmlPath).toPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
