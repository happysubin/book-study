package chapter02.section06;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class BankStatementProcessor {

    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        double total = 0;
        for (BankTransaction bankTransaction : bankTransactions) {
            if(bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0;
        for (BankTransaction bankTransaction : bankTransactions) {
            if(bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    /**
     * isAfter은 첫 번째 객체가 두 번째 객체보다 나중의 날짜인지를 확인
     */
    public double calculateTotalDateRange(final LocalDate from , LocalDate to) {
        double total = 0;
        for (BankTransaction bankTransaction : bankTransactions) {
            if(from.isBefore(bankTransaction.getDate()) && to.isAfter(bankTransaction.getDate())) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }
}
