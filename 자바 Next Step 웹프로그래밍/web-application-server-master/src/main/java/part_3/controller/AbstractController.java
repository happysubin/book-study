package part_3.controller;

import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller{


    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        } else if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response)throws IOException {}
    protected void doPost(HttpRequest request, HttpResponse response)throws IOException{}
}
