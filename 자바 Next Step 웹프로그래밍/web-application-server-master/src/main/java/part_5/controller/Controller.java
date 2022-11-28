package part_5.controller;

import part_5.webserver.HttpRequest;
import part_5.webserver.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
