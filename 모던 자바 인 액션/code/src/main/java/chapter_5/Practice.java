package chapter_5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
                );

        //2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정렬하시오

        List<Transaction> t1 = transactions
                .stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        for (Transaction transaction : t1) {
            System.out.println("transaction.toString() = " + transaction.toString());
        }

        //거래자가 근무하는 모든 도시를 중복 없이 나열하시오.

        List<String> cities = transactions
                .stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        for (String city : cities) {
            System.out.println(city);
        }
        
        //케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오
        List<Trader> traders = transactions
                .stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getTrader())
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        for (Trader trader : traders) {
            System.out.println("trader.toString() = " + trader.toString());
        }

        //모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오
        String reduce = transactions
                .stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a + b); //알파벳 순으로 정렬

        System.out.println("reduce = " + reduce);

        //밀라노에 거래자가 있는가??

        boolean milan = transactions
                .stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println("milan = " + milan);

        //케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오
        List<Integer> values = transactions
                .stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getValue())
                .collect(Collectors.toList());

        for (Integer value : values) {
            System.out.println("value = " + value);
        }


        //전체 트랜잭션 중 최댓값은 얼마인가
        Optional<Integer> max = transactions
                .stream()
                .map(t -> t.getValue())
                .reduce(Integer::max);
        System.out.println("max = " + max.get());

        //전체 트랜잭션 중 최솟값은 얼마인가
        Optional<Transaction> minTx = transactions
                .stream()
                .reduce((tr1, tr2) -> tr1.getValue() < tr2.getValue() ? tr1 : tr2);
        System.out.println("minTx.get().getValue() = " + minTx.get().getValue());
    }
}
