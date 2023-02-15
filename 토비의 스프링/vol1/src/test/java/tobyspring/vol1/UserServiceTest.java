package tobyspring.vol1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;
import tobyspring.vol1.user.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static tobyspring.vol1.user.domain.Level.*;
import static tobyspring.vol1.user.service.UserLevelUpgradePolicyImpl.MIN_LOG_COUNT_FOR_SILVER;
import static tobyspring.vol1.user.service.UserLevelUpgradePolicyImpl.MIN_RECOMMEND_FOR_GOLD;
import static tobyspring.vol1.user.service.UserService.*;

@SpringBootTest
public class UserServiceTest {

    List<User> users;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @BeforeEach
    void beforeEach(){
        userDao.deleteAll();
        users = Arrays.asList(
                new User("id1", "user1", "1234", BASIC, MIN_LOG_COUNT_FOR_SILVER - 1,0),
                new User("id2", "user2", "1234", BASIC, MIN_LOG_COUNT_FOR_SILVER,0),
                new User("id3", "user3", "1234", SILVER, 60,MIN_RECOMMEND_FOR_GOLD - 1),
                new User("id4", "user4", "1234", SILVER, 60,MIN_RECOMMEND_FOR_GOLD),
                new User("id5", "user5", "1234", GOLD, 100,Integer.MAX_VALUE));
    }

    @Test
    void bean(){
        assertThat(userService).isNotNull();
    }

    @Test
    void add(){
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);

        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isSameAs(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isSameAs(BASIC);
    }

    @Test
    void upgradeLevels(){
        for (User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);

    }

    private void checkLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded){
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        }
        else{
            assertThat(userUpdate.getLevel()).isSameAs(user.getLevel());
        }
    }
}
