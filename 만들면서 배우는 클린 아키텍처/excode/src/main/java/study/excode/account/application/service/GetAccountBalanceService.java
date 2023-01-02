package study.excode.account.application.service;

import lombok.RequiredArgsConstructor;
import study.excode.account.application.port.in.GetAccountBalanceQuery;
import study.excode.account.domain.Account;
import study.excode.account.domain.Money;

import java.time.LocalDateTime;

import static study.excode.account.domain.Account.*;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                .calculateBalance();
    }
}
