package part_3.controller;

import part_5.webserver.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface Controller {

    void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException;
}
