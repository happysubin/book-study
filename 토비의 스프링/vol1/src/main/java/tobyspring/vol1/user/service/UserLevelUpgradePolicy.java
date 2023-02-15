package tobyspring.vol1.user.service;

import tobyspring.vol1.user.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
