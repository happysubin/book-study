package chapter03.section04;

import chapter02.section06.BankTransaction;
import fixture.BankTransactionFactory;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankStatementProcessorTest {

    @Test
    void 동작파라미터화를_통한_코드의_유연성을_증가시킴() {

        BankStatementProcessor processor = new BankStatementProcessor(BankTransactionFactory.getBankTransactions());

        List<BankTransaction> transactions = processor.findTransactions(bankTransaction ->
                bankTransaction.getDate().getMonth() == Month.FEBRUARY
                        && bankTransaction.getAmount() >= 1_000);

        assertEquals(1, transactions.size());

    }

}