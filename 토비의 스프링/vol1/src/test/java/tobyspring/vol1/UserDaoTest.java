package tobyspring.vol1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import tobyspring.vol1.user.dao.UserDaoJdbc;
import tobyspring.vol1.user.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.vol1.user.domain.Level.*;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    ApplicationContext ac;

    UserDaoJdbc userDao;
    User user;
    User user1;
    User user2;

    @BeforeEach
    void beforeEach() throws SQLException {
        userDao = ac.getBean(UserDaoJdbc.class);
        userDao.deleteAll();
        user = new User("id1", "subin", "1234", BASIC, 1, 0);
        user1 = new User("id2", "yebin", "2234", SILVER, 55, 10);
        user2 = new User("id3", "bean", "3234", GOLD, 100, 40);

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

    @Test
    public void update(){
        userDao.add(user1);
        userDao.add(user2);

        user1.setName("update");
        user1.setPassword("1111");
        user1.setLevel(GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        userDao.update(user1);

        User userUpdate = userDao.get(user1.getId());

        checkSameUser(user1, userUpdate);

        User user2same = userDao.get(user2.getId());

        checkSameUser(user2, user2same);
    }

    private void checkSameUser(User user1, User user) {
        assertThat(user1.getId()).isEqualTo(user.getId());
        assertThat(user1.getName()).isEqualTo(user.getName());
        assertThat(user1.getPassword()).isEqualTo(user.getPassword());

        assertThat(user1.getLevel()).isEqualTo(user.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user.getRecommend());
    }
}
