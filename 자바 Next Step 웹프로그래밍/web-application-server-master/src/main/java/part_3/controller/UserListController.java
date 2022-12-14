package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;
import java.util.Collection;

public class UserListController extends AbstractController{

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if(request.getHeader("Cookie") == null){
            response.forward("/user/login_failed.html");
        }
        String cookie = request.getHeader("Cookie").split("=")[1];
        if(Boolean.parseBoolean(cookie) == true) {

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
            response.forwardBody(sb.toString());
        }

        else{
            response.forward("/user/login_failed.html");
        }
    }
}
