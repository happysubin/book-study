package tobyspring.vol1.chapter_1.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComplicatedConnectionMaker implements ConnectionMaker {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //복잡한 로직이 있다고 가정.
        return null;
    }
}
