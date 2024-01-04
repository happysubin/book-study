package chapter02.section06;

import fixture.BankTransactionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankStatementProcessorTest {


    @Test
    void 일정_구간의_거래_내역을_구하라() {
        List<BankTransaction> transactions = BankTransactionFactory.getBankTransactions();
        BankStatementProcessor processor = new BankStatementProcessor(transactions);

        double result = processor.calculateTotalDateRange(LocalDate.of(2023, 12, 20), LocalDate.of(2024, 1, 10));

        Assertions.assertEquals(160, result);
    }
}