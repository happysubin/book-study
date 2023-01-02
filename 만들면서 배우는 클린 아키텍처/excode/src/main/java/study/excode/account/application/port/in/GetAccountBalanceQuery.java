package study.excode.account.application.port.in;

import study.excode.account.domain.Account;
import study.excode.account.domain.Money;

import static study.excode.account.domain.Account.*;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(AccountId accountId);

}
