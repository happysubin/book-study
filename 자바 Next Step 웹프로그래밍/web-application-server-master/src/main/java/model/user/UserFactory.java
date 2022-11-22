package model.user;

import util.HttpRequestUtils;

import java.util.Map;

public class UserFactory {

    private static String PASSWORD = "password";
    private static String NAME = "password";
    private static String USERID = "userId";
    private static String EMAIL = "email";

    public static User createUser(String line){
        Map<String, String> map = HttpRequestUtils.parseQueryString(line);
        return new User(
                map.get(USERID),
                map.get(PASSWORD),
                map.get(NAME),
                map.get(EMAIL)
        );}
}
