package part_3.controller;

import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;

public class ResourceController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.forward(request.getPath());
    }
}
