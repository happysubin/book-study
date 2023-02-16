package tobyspring.vol1.user.service;

import org.springframework.stereotype.Component;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;

import static tobyspring.vol1.user.service.UserService.*;

@Component
public class UserLevelUpgradePolicyImpl implements UserLevelUpgradePolicy{

    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    protected final UserDao userDao;

    public UserLevelUpgradePolicyImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel){
            case BASIC: return (user.getLogin()) >= MIN_LOG_COUNT_FOR_SILVER;
            case SILVER: return (user.getRecommend()) >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
