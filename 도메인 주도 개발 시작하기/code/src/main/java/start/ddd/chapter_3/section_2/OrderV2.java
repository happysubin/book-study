package start.ddd.chapter_3.section_2;




import java.util.List;

public class OrderV2 {

    private Money totalAmounts;
    private OrderLine orderLines; //일급 콜렉션 느낌

    public void changeOrderLines(List<OrderLine> newLines){
        orderLines.changeOrderLines(newLines);
        orderLines.getTotalAmounts();
    }
}
