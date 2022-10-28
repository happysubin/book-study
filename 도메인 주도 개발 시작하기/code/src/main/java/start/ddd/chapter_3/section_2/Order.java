package start.ddd.chapter_3.section_2;

import start.ddd.chapter_1.section_5.Money;
import start.ddd.chapter_1.section_5.OrderLine;
import start.ddd.chapter_1.section_5.ShippingInfo;

import java.util.List;
import java.util.stream.IntStream;

public class Order {

    private Money totalAmounts;
    private List<OrderLine> orderLines;

    private void calculateTotalAmounts(){
        int sum = orderLines.stream()
                .mapToInt(ol -> ol.getPrice() * ol.getQuantity())
                .sum();
        this.totalAmounts = new Money(sum);
    }
}
