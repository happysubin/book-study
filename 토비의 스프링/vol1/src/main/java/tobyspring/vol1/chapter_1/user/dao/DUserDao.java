package tobyspring.vol1.chapter_1.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao{

    @Override
    Connection getConnection() throws ClassNotFoundException, SQLException {
        /**
         * 다른 커넥션을 리턴한다고 가정
         */
        Class.forName("Oracle");
        Connection c = DriverManager.getConnection("something_url", "root", "1234");
        return c;

    }
}
