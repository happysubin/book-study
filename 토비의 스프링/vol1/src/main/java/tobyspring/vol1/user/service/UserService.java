package tobyspring.vol1.user.service;

import org.springframework.stereotype.Service;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.User;

import java.util.List;

import static tobyspring.vol1.user.domain.Level.*;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels(){
        List<User> users = userDao.getAll();
        for (User user : users) {
            if(userLevelUpgradePolicy.canUpgradeLevel(user)){
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(BASIC);
        userDao.add(user);
    }
}
