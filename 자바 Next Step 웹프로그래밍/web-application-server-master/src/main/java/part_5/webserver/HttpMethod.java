package part_5.webserver;

public enum HttpMethod {
    GET, POST;


    public boolean isPost(){
        return this == POST;
    }
}
