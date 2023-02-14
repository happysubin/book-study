package tobyspring.vol1.chapter_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import tobyspring.vol1.chapter_1.user.dao.DaoFactory;
import tobyspring.vol1.chapter_1.user.dao.UserDao;
import tobyspring.vol1.chapter_1.user.domain.User;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    ApplicationContext ac;

    UserDao userDao;
    User user;
    User user1;
    User user2;

    @BeforeEach
    void beforeEach() throws SQLException {
        userDao = ac.getBean(UserDao.class);
        userDao.deleteAll();
        user = new User("id1", "subin", "1234");
        user1 = new User("id2", "yebin", "2234");
        user2 = new User("id3", "bean", "3234");

        System.out.println("ac = " + ac); //동일한 주소가 출력됨.
        System.out.println("this = " + this); //다른 주소가 출력됨. 테스므 메서드 마다 테스트 클래스 인스턴스를 만들어 실행하기 때문이다.
    }


    @Test
    void count() throws SQLException, ClassNotFoundException {

        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(3);
    }


    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {

        userDao.add(user);
        userDao.add(user1);

        assertThat(userDao.getCount()).isEqualTo(2);

        User getUser = userDao.get("id1");

        assertThat(getUser.getName()).isEqualTo("subin");
        assertThat(getUser.getPassword()).isEqualTo("1234");

        User getUser2 = userDao.get("id2");

        assertThat(getUser2.getName()).isEqualTo("yebin");
        assertThat(getUser2.getPassword()).isEqualTo("2234");

    }

    @Test
    void getUserFailure(){
        assertThatThrownBy(()->{
            userDao.get("hello");
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
