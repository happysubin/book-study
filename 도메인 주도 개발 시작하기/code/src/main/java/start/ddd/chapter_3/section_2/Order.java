package start.ddd.chapter_3.section_2;



import java.util.List;

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
