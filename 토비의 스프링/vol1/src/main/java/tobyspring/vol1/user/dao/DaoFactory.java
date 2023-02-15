package tobyspring.vol1.user.dao;


import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DaoFactory {

    /**
     * 애플리케이션의 컴포넌트 역할을 하는 오브젝트와 애플리케이션의 구조를 결정하는 오브젝트를 분리한 것에 의미가 크다.
     */

    @Bean
    public UserDao userDao(){
        return new UserDaoJdbc(new JdbcTemplate(dataSource()));
    }

    @Bean
    public DataSource dataSource(){
        return new DriverDataSource(
                "jdbc:mysql://localhost:3306/toby_spring",
                "com.mysql.cj.jdbc.Driver",
                new Properties(),
                "root",
                "1234"
        );
    }
}
