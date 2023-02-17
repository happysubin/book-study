package tobyspring.vol1.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tobyspring.vol1.user.domain.User;

public class UserServiceTx implements UserService {

    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    public UserServiceTx(UserService userService, PlatformTransactionManager transactionManager) {
        this.userService = userService;
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            userService.add(user);
            transactionManager.commit(status);
        }
        catch(Exception e){
            transactionManager.rollback(status);
            throw e;
        }
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            userService.upgradeLevels();
            transactionManager.commit(status);
        }
        catch(Exception e){
            transactionManager.rollback(status);
            throw e;
        }
    }
}
