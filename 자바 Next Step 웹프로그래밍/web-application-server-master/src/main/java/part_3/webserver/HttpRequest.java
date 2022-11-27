package part_3.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part_5.util.HttpRequestUtils;
import part_5.util.IOUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * 요청 전문가 클래스
 */

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(part_5.util.HttpRequestUtils.class);

    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String firstLine = br.readLine();
        log.info(firstLine);

        extractMethod(firstLine);
        extractPath(firstLine);
        extractQueryString(firstLine);
        extractHeaders(br);
        extractBody(br);
    }

    private void extractMethod(String firstLine) {
        this.method = firstLine.split(" ")[0];
    }

    private void extractPath(String firstLine) {
        this.path = firstLine.split(" ")[1].split("\\?")[0]; //?가 있던 말던 상관 없음
    }

    private void extractQueryString(String firstLine) {
        if(notExistQueryString(firstLine)){ //쿼리 스트링이 없다면
            return ;
        }
        this.parameters.putAll(HttpRequestUtils.parseQueryString(firstLine.split(" ")[1].split("\\?")[1]));
    }

    private boolean notExistQueryString(String firstLine) {
        return firstLine.split(" ")[1].split("\\?").length < 2;
    }

    private void extractHeaders(BufferedReader br) throws IOException {
        Map<String, String> map = new HashMap<>();
        String line = br.readLine();

        while(line != null && !line.equals("")){
            log.info(line);
            String[] str = line.split(" ");
            map.put(str[0].replace(":", " ").trim(), str[1]);
            line = br.readLine();
        }

        this.headers = map;
    }

    private void extractBody(BufferedReader br) throws IOException {

        String contentLength = headers.get("Content-Length");
        if(contentLength == null){
            return ;
        }
        String bodyLine = IOUtils.readData(br, Integer.parseInt(contentLength));
        this.parameters.putAll(HttpRequestUtils.parseQueryString(bodyLine));
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }
}
