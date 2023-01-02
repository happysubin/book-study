package study.excode.account.application.port.in;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import study.excode.account.domain.Money;
import study.excode.common.SelfValidating;

import static study.excode.account.domain.Account.*;

@Value
@EqualsAndHashCode(callSuper = false)
public
class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
