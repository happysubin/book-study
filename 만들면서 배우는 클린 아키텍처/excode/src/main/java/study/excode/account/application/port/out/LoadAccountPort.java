package study.excode.account.application.port.out;

import study.excode.account.domain.Account;

import java.time.LocalDateTime;

import static study.excode.account.domain.Account.*;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
