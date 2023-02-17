package tobyspring.vol1.user.service;

import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.User;
import tobyspring.vol1.user.service.mailSender.UserService;

import java.util.List;

import static tobyspring.vol1.user.domain.Level.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserServiceImpl(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    @Override
    public void upgradeLevels(){
        List<User> users = userDao.getAll();
        for (User user : users) {
            if(userLevelUpgradePolicy.canUpgradeLevel(user)){
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }
    }

    @Override
    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(BASIC);
        userDao.add(user);
    }

    public void setMailSender(MailSender mailSender){
        UserLevelUpgradePolicyImpl userLevelUpgradePolicy = (UserLevelUpgradePolicyImpl) this.userLevelUpgradePolicy;
        userLevelUpgradePolicy.setMailSender(mailSender );
    }
}
