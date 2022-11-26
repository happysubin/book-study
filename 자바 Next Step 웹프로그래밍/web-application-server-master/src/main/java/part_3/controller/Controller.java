package part_3.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface Controller {

    void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException;
}
