package tobyspring.vol1.user.service;

import tobyspring.vol1.user.domain.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
