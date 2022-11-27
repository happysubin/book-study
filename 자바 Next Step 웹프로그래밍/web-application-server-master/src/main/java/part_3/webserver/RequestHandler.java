package part_3.webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part_3.controller.*;
import part_3.util.HttpRequestUtils;
import part_3.util.IOUtils;
import part_5.webserver.HttpRequest;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    public static final String METHOD = "method";
    public static final String PATH = "path";
    public static final String COOKIE = "cookie";

    private Socket connection;
    static private Map<String, Controller> controllerMap = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

    }

    static{
        controllerMap.put("/index.html", new HomeController());
        controllerMap.put("/user/create", new SaveUserController());
        controllerMap.put("/user/form.html", new UserFormController());
        controllerMap.put("/user/login.html", new LoginFormController());
        controllerMap.put("/user/login", new LoginProcessController());
        controllerMap.put("/user/login_failed.html", new LoginFailController());
        controllerMap.put("/user/list.html", new UserListController());
    }


    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(in);


            Controller controller = controllerMap.get(httpRequest.getPath());

            if(controller == null){
                //static 관련 css, js
                ResourceController resourceController = new ResourceController();
                resourceController.doProcess(httpRequest, dos);
            }

            else{
                controller.doProcess(httpRequest, dos);
            }

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private String extractCookie(Map<String, String> cookieMap) {
        if(cookieMap.keySet().size()> 1){
            throw new RuntimeException("쿠키가 너무 많습니다");
        }

        String cookie = "";

        for (String s : cookieMap.keySet()) {
            cookie = cookieMap.get(s);
        }

        return cookie;
    }

    private Integer getContentLength(String line) {
        return Integer.parseInt(line.split(" ")[1]);
    }

    private void createRequestMap(String httpMethod, String path, String body, String cookie,  Map<String, String> requestMap) {

        requestMap.put(METHOD, httpMethod);
        requestMap.put(PATH, path);
        requestMap.put(COOKIE, cookie);

        if(body != null){
            Map<String, String> bodyMap = HttpRequestUtils.parseQueryString(body);
            for (String key : bodyMap.keySet()) {
                requestMap.put(key, bodyMap.get(key));
            }
        }
    }


}
