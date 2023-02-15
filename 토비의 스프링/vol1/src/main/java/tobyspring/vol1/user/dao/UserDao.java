package tobyspring.vol1.user.dao;

import tobyspring.vol1.user.domain.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();

    void update(User user);
}
