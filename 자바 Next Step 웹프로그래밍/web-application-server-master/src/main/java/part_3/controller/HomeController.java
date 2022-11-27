package part_3.controller;

import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;


public class HomeController implements Controller{
    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        HttpResponse response = new HttpResponse(dos);
        response.forward(httpRequest.getPath());
    }

}
