package part_3.model.user;

import java.util.Map;

public class UserFactory {

    private static String PASSWORD = "password";
    private static String NAME = "password";
    private static String USERID = "userId";
    private static String EMAIL = "email";

    public static User createUser(String userId, String password, String name, String email){
        return new User(
                userId,
                password,
                name,
                email
        );}
}
