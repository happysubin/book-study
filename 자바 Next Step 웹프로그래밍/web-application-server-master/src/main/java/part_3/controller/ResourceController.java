package part_3.controller;

import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;

public class ResourceController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse response) throws IOException {
        response.forward(httpRequest.getPath());
    }
}
