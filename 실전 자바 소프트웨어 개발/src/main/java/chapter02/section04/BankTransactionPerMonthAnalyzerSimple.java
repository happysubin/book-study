package chapter02.section04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 1번 요구사항. 은행 입출 내역의 총 수입과 총 지출은 각각 얼마인가? 결과가 양수인가? 음수인가?
 * KISS keep it short and simple 원식 준수
 *
 * 0. 파일의 모든 행을 가져옴
 * 1. 콤마로 열 분리
 * 2. 금액 추출
 * 3. 금액을 더블로 파싱
 */

public class BankTransactionPerMonthAnalyzerSimple {

    private static final String RESOURCES = "src/main/resources/transactions.csv";

    public static void main(String[] args) throws IOException {
        final Path path = Paths.get(RESOURCES);
        final List<String> lines = Files.readAllLines(path);

        final DateTimeFormatter Date_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        double total = 0d;
        for (String line : lines) {
            final String[] columns = line.split(",");
            final LocalDate date = LocalDate.parse(columns[0], Date_PATTERN);
            if(date.getMonth() == Month.JANUARY) {
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }
        }
        System.out.println("The total for all transactions is " + total);
    }
}
