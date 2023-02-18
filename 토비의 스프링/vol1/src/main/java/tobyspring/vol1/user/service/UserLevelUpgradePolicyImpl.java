package tobyspring.vol1.user.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import tobyspring.vol1.user.dao.UserDao;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;


@Setter
@Getter
public class UserLevelUpgradePolicyImpl implements UserLevelUpgradePolicy{

    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    protected final UserDao userDao;
    protected MailSender mailSender;

    public UserLevelUpgradePolicyImpl(UserDao userDao, MailSender mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom("useradmin@ksug.org");
        simpleMailMessage.setSubject("Upgrade 안내");
        simpleMailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

        mailSender.send(simpleMailMessage);
    }


}
