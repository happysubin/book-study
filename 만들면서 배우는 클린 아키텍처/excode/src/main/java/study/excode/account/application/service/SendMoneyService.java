package study.excode.account.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import study.excode.account.application.port.in.SendMoneyCommand;
import study.excode.account.application.port.in.SendMoneyUseCase;
import study.excode.account.application.port.out.AccountLock;
import study.excode.account.application.port.out.LoadAccountPort;
import study.excode.account.application.port.out.UpdateAccountStatePort;

@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;



    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 비즈니스 규칙 검증

        // TODO: 모델 상태 조작

        // TODO: 출력 값 반환
        return true;
    }
}
