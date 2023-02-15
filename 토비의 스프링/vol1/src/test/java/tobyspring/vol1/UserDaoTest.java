package tobyspring.vol1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void getAll(){
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        userDao.add(user);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user, users1.get(0));

        userDao.add(user1);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user, users2.get(0));
        checkSameUser(user1, users2.get(1));


        userDao.add(user2);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user) {
        assertThat(user1.getId()).isEqualTo(user.getId());
        assertThat(user1.getName()).isEqualTo(user.getName());
        assertThat(user1.getPassword()).isEqualTo(user.getPassword());

    }
}
