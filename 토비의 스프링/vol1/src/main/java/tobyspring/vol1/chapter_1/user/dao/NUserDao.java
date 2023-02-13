package tobyspring.vol1.chapter_1.user.dao;

import tobyspring.vol1.chapter_1.user.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new NUserDao();
        User user = new User();
        user.setId("subin");
        user.setName("안수빈");
        user.setPassword("1234");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공!");
        User user2 = userDao.get(user.getId());

        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공!");

    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/toby_spring", "root", "1234");
        return c;
    }
}
