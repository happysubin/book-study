package study.excode.account.application.port.out;

import study.excode.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}