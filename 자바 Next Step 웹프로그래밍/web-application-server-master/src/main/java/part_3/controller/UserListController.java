package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.util.HttpResponseUtils;
import part_3.webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class UserListController implements Controller{
    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        String cookie = requestMap.get(RequestHandler.COOKIE);

        if(Boolean.parseBoolean(cookie) == true){


            Collection<User> users = DataBase.findAll();

            StringBuilder sb = new StringBuilder();
            sb.append("<table border='1'>");
            for (User user : users) {
                sb.append("<tr>");
                sb.append("<td>" + user.getUserId() + "</td>");
                sb.append("<td>" + user.getName() + "</td>");
                sb.append("<td>" + user.getEmail() + "</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            byte[] body = sb.toString().getBytes();

            HttpResponseUtils.response200Header(dos, body.length);
            HttpResponseUtils.responseBody(dos, body);

        }

        else{
            HttpResponseUtils.responseLoginFailHeader(dos);
        }
    }
}
