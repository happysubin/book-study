package controller;

import util.HttpResponseUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static webserver.RequestHandler.PATH;

public class ResourceController implements Controller {
    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        String path = requestMap.get(RequestHandler.PATH);
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
