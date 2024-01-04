package fixture;

import chapter02.section06.BankTransaction;

import java.time.LocalDate;
import java.util.List;

public class BankTransactionFactory {

    private BankTransactionFactory() {}

    public static List<BankTransaction> getBankTransactions() {
        return List.of(
                new BankTransaction(LocalDate.of(2024, 01, 01), 100, "user1"),
                new BankTransaction(LocalDate.of(2024, 01, 02), 60, "user2"),
                new BankTransaction(LocalDate.of(2024, 01, 11), 200, "user3"),
                new BankTransaction(LocalDate.of(2024, 01, 11), 300, "user4"),
                new BankTransaction(LocalDate.of(2024, 02, 11), 1500, "user5")

        );
    }
}
