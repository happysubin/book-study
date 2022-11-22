package model.user;


public class UserDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
