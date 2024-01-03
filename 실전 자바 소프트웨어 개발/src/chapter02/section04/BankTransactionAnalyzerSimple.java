package chapter02.section04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 2번 요구사항: 특정 달엔 몇 건의 입출금이 발생했는가? 라는 요구사항을 만들기 위해 기존 클래스를 복붙해서 코드를 추가함.
 */

public class BankTransactionAnalyzerSimple {

    private static final String Resources = "src/resources/transactions.csv";

    public static void main(String[] args) throws IOException {
        final Path path = Paths.get(Resources);
        final List<String> lines = Files.readAllLines(path);

        double total = 0d;

        for (String line : lines) {
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);
            total += amount;
        }
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath = " + projectPath);
        System.out.println("The total for all transactions is " + total);
    }
}
