package part_5.controller;

import part_5.db.DataBase;
import part_5.model.user.User;
import part_5.util.HttpRequestUtils;
import part_5.webserver.HttpRequest;
import part_5.webserver.HttpResponse;
import part_6.HttpSession;

import java.util.Collection;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogin(request.getSession())) {
            response.sendRedirect("/user/login.html");
            return;
        }

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

//    private boolean isLogin(String cookieValue) {
//        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookieValue);
//        String value = cookies.get("logined");
//        if (value == null) {
//            return false;
//        }
//        return Boolean.parseBoolean(value);
//    }

    private boolean isLogin(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        if(user == null){
            return false;
        }
        return true;
    }
}
