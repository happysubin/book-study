package part_3.controller;

import part_3.util.HttpResponseUtils;
import part_3.webserver.HttpRequest;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoginFormController implements Controller{
    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + httpRequest.getPath()).toPath());
        HttpResponseUtils.response200Header(dos, body.length);
        HttpResponseUtils.responseBody(dos, body);
    }
}
