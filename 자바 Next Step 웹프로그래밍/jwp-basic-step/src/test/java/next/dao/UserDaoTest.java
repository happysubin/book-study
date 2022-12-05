package next.dao;

import static org.junit.Assert.*;

import core.jdbc.ConnectionManager;
import org.junit.Before;
import org.junit.Test;

import next.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;
import java.util.List;

public class UserDaoTest {

//    @Before
//    public void setup() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(new ClassPathResource("jwp.sql"));
//        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
//    }

    @Test
    public void crud() throws Exception {
        User expected = new User("user146", "password12", "name12", "11@112");
        UserDao userDao = new UserDao();
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void finalAll() throws Exception {
        UserDao userDao = new UserDao();
        List<User> allUser = userDao.findAllUser();
        System.out.println("allUser.size() = " + allUser.size());
        for (User user : allUser) {
            System.out.println("user = " + user.toString());
        }
    }

    @Test
    public void update() throws Exception {
        //given
        UserDao userDao = new UserDao();
        User update = new User("user146","pass", "name2", "123@123.com");
        userDao.update(update);

        //when
        User user = userDao.findByUserId("user146");
        assertEquals(user.getEmail(), update.getEmail());
        assertEquals(user.getName(), update.getName());

    }
}
