package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


import controller.Controller;
import controller.HomeController;
import controller.SaveUserController;
import controller.UserFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.HttpResponseUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    public static final String METHOD = "method";
    public static final String PATH = "path";

    private Socket connection;
    static private Map<String, Controller> controllerMap = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

    }

    static{
        controllerMap.put("/index.html", new HomeController());
        controllerMap.put("/user/create", new SaveUserController());
        controllerMap.put("/user/form.html", new UserFormController());
    }


    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            //로깅
            int lineNum = 0;
            int contentLength = 0;
            String firstLine = "";
            String line;
            while(!(line = br.readLine()).equals("") & line != null){
                log.info(line);
                if(lineNum == 0) firstLine = line;
                if (line.contains("Content-Length")) {
                    contentLength = getContentLength(line);
                }
                lineNum++;
            }

            Map<String, String>  requestMap = new HashMap<>();

            String httpMethod = HttpRequestUtils.parseHttpMethod(firstLine);
            String path = HttpRequestUtils.parsePath(firstLine);
            String bodyLine = null;

            if(httpMethod.equals("POST")){
                String s = IOUtils.readData(br, contentLength);
                System.out.println("s = " + s);
            }

            createRequestMap(httpMethod, path, bodyLine, requestMap); //왜 여기가 문제지..?

            Controller controller = controllerMap.get(path);
            controller.doProcess(requestMap, dos);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private Integer getContentLength(String line) {
        return Integer.parseInt(line.split(" ")[1]);
    }

    private void createRequestMap(String httpMethod, String path, String body, Map<String, String> requestMap) {

        requestMap.put(METHOD, httpMethod);
        requestMap.put(PATH, path);

        if(body != null){
            Map<String, String> bodyMap = HttpRequestUtils.parseQueryString(body);
            for (String key : bodyMap.keySet()) {
                requestMap.put(key, bodyMap.get(key));
            }
        }
    }


}
