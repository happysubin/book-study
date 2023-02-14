package tobyspring.vol1.chapter_1.user.dao;


import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DaoFactory {

    /**
     * 애플리케이션의 컴포넌트 역할을 하는 오브젝트와 애플리케이션의 구조를 결정하는 오브젝트를 분리한 것에 의미가 크다.
     */

    @Bean
    public UserDao userDao(DataSource dataSource){
        return new UserDao(dataSource);
    }

    @Bean
    public DataSource dataSource() throws ClassNotFoundException {

        return new DriverDataSource(
                "jdbc:mysql://localhost:3306/toby_spring",
                "com.mysql.cj.jdbc.Driver",
                new Properties(),
                "root",
                "1234"
        );
//        dataSource.setDriverClass(Driver);
//        dataSource.setUrl("jdbc:mysql://localhost:3306/toby_spring");
//        dataSource.setUrl("root");
//        dataSource.setPassword("1234");
//        return dataSource;
    }
}
