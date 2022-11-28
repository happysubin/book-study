package part_5.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import part_5.util.HttpRequestUtils;
import part_5.util.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private RequestLine requestLine;
    private Map<String, String > headers = new HashMap<>();
    private Map<String, String > params = new HashMap<>();

    public HttpRequest(InputStream in){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();

            if(line == null){
                return;
            }

            requestLine =  new RequestLine(line);
            params = requestLine.getParams();
            line = br.readLine();

            while (!line.equals("")){
                log.debug("header : {}", line);
                String[] tokens = line.split(":");
                headers.put(tokens[0].trim(), tokens[1].trim());
                line = br.readLine();
            }

            if(getMethod().isPost()){
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            }


        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeaders(String name) {
        return headers.get(name);
    }

    public String getParams(String name) {
        return params.get(name);
    }
}
