package part_3.controller;

import part_3.util.HttpResponseUtils;
import part_3.webserver.RequestHandler;
import part_5.webserver.HttpRequest;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class ResourceController implements Controller {
    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        String path = httpRequest.getPath();
        if(path.contains(".css")){
            byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
            HttpResponseUtils.responseCss200Header(dos, body.length);
            HttpResponseUtils.responseBody(dos, body);
        }
        else if (path.contains(".js")){
            byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
            HttpResponseUtils.responseJs200Header(dos, body.length);
            HttpResponseUtils.responseBody(dos, body);
        }
    }
}
