package tobyspring.vol1.user.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
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
    private final DataSource dataSource;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy, DataSource dataSource) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
        this.dataSource = dataSource;
    }

    public void upgradeLevels() throws Exception {

        TransactionSynchronizationManager.initSynchronization(); //동기화 관리 클래스를 사용.
        Connection c = DataSourceUtils.getConnection(dataSource); //해당 메서드는 커넥션 오브젝트를 생성하면서 트랜잭션 동기화에 사용하도록 저장소에 바인딩한다.
        c.setAutoCommit(false);
        try{
            List<User> users = userDao.getAll();
            for (User user : users) {
                if(userLevelUpgradePolicy.canUpgradeLevel(user)){
                    userLevelUpgradePolicy.upgradeLevel(user);
                }
            }
            c.commit();
        }
        catch(Exception e){
            c.rollback();
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection(c, dataSource); //디비 커넥션을 닫음

            //동기화 및 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(BASIC);
        userDao.add(user);
    }
}
