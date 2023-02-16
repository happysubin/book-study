package tobyspring.vol1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;
import tobyspring.vol1.user.service.UserLevelUpgradePolicy;
import tobyspring.vol1.user.service.UserLevelUpgradePolicyImpl;
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

    @Autowired
    PlatformTransactionManager transactionManager;

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
    void upgradeLevels() throws Exception {
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

    @Test
    void upgradeAllOrNothing(){
        TestUserService testUserService = new TestUserService(userDao,users.get(3).getId(), transactionManager);
        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        assertThatThrownBy(()->{
            testUserService.upgradeLevels();
        }).hasMessage("테스트 예외");

        checkLevel(users.get(1), false);
    }

    static class TestUserService extends UserLevelUpgradePolicyImpl {

        private String id;
        private PlatformTransactionManager transactionManager;

        public TestUserService(UserDao userDao, String id, PlatformTransactionManager platformTransactionManager) {
            super(userDao);
            this.id = id;
            this.transactionManager = platformTransactionManager;
        }

        @Override
        public void upgradeLevel(User user) {
            if(user.getId().equals(this.id)) throw new RuntimeException("테스트 예외");
            super.upgradeLevel(user);
        }

        public void upgradeLevels(){
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try{
                List<User> users = userDao.getAll();
                for (User user : users) {
                    if(canUpgradeLevel(user)){
                        upgradeLevel(user);
                    }
                }
                transactionManager.commit(status);
            }
            catch(Exception e){
                transactionManager.rollback(status);
                throw e;
            }
        }
    }
}
