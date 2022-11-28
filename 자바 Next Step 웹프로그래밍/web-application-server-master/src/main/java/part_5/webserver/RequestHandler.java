package part_5.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part_5.controller.Controller;
import part_5.db.DataBase;
import part_5.model.user.User;
import part_5.util.HttpRequestUtils;
import part_5.util.IOUtils;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            Controller controller = RequestMapping.getController(request.getPath());

            if(controller == null){
                String path = getDefaultUrl(request.getPath());
                response.forward(path);
            }
            else{
                controller.service(request, response);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getDefaultUrl(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
