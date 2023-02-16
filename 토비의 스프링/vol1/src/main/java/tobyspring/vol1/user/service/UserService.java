package tobyspring.vol1.user.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
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
}
