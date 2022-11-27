package part_3.controller;

import part_3.webserver.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Controller {

    void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException;
}
