package tobyspring.vol1.chapter_1.user.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.vol1.chapter_1.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = ac.getBean(UserDao.class);
        User user = new User();
        user.setId("subin");
        user.setName("안수빈");
        user.setPassword("1234");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공!");
        User user2 = userDao.get(user.getId());

        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId() + " 조회 성공!");

    }
}
