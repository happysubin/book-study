package tobyspring.vol1.user.dao;


import com.zaxxer.hikari.util.DriverDataSource;
import net.bytebuddy.asm.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tobyspring.vol1.user.service.*;
import tobyspring.vol1.user.service.mailSender.MockMailSender;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DaoFactory {

    /**
     * 애플리케이션의 컴포넌트 역할을 하는 오브젝트와 애플리케이션의 구조를 결정하는 오브젝트를 분리한 것에 의미가 크다.
     */

    @Bean
    public UserDao userDao(){
        return new UserDaoJdbc(new JdbcTemplate(dataSource()));
    }

    @Bean
    public DataSource dataSource(){
        return new DriverDataSource(
                "jdbc:mysql://localhost:3306/toby_spring",
                "com.mysql.cj.jdbc.Driver",
                new Properties(),
                "root",
                "1234"
        );
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public TxProxyFactoryBean txProxyFactoryBean(
            PlatformTransactionManager platformTransactionManager, UserService userService
    ){
        return new TxProxyFactoryBean(userService, platformTransactionManager, "upgradeLevels", UserService.class);
    }

    @Bean
    public TransactionAdvice transactionAdvice(PlatformTransactionManager transactionManager){
        return new TransactionAdvice(transactionManager);
    }

    @Bean
    public NameMatchMethodPointcut nameMatchMethodPointcut(){
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }


    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy(){
        return new UserLevelUpgradePolicyImpl(userDao(), new MockMailSender());
    }


    @Bean
    public UserService userService(){
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy());
    }

    @Bean
    public ProxyFactoryBean proxyFactoryBean(){
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userService());
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(nameMatchMethodPointcut(), transactionAdvice(transactionManager())));
        return proxyFactoryBean;
    }
}
