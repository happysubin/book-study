package tobyspring.vol1.chapter_1.user.dao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    /**
     * 애플리케이션의 컴포넌트 역할을 하는 오브젝트와 애플리케이션의 구조를 결정하는 오브젝트를 분리한 것에 의미가 크다.
     */

    @Bean
    public UserDao userDao(ConnectionMaker connectionMaker){
        return new UserDao(connectionMaker);
    }

    @Bean
    public ConnectionMaker connectionMaker(){
        return new SimpleConnectionMaker();
    }
}
