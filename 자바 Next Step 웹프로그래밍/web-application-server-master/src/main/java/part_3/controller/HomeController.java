package part_3.controller;

import part_3.util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static part_3.webserver.RequestHandler.*;

public class HomeController implements Controller{
    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + requestMap.get(PATH)).toPath());
        HttpResponseUtils.response200Header(dos, body.length);
        HttpResponseUtils.responseBody(dos, body);
    }

}
