package tobyspring.vol1.user.service;

import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.User;

import java.util.List;

import static tobyspring.vol1.user.domain.Level.*;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;
    private final PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels(){
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            List<User> users = userDao.getAll();
            for (User user : users) {
                if(userLevelUpgradePolicy.canUpgradeLevel(user)){
                    userLevelUpgradePolicy.upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        }
        catch(Exception e){
            transactionManager.rollback(status);
            throw e;
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(BASIC);
        userDao.add(user);
    }

    public void setMailSender(MailSender mailSender){
        UserLevelUpgradePolicyImpl userLevelUpgradePolicy = (UserLevelUpgradePolicyImpl) this.userLevelUpgradePolicy;
        userLevelUpgradePolicy.setMailSender(mailSender );
    }

    public MailSender getMailSender(){
        UserLevelUpgradePolicyImpl userLevelUpgradePolicy = (UserLevelUpgradePolicyImpl) this.userLevelUpgradePolicy;
        return userLevelUpgradePolicy.getMailSender();
    }
}
