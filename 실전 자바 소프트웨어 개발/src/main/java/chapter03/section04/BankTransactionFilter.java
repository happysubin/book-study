package chapter03.section04;

import chapter02.section06.BankTransaction;

@FunctionalInterface
public interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}
