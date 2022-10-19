package start.ddd.chapter_1.section_5;

public class OrderLine {

    private Product product;
    private Money price;
    private int quantity;
    private int amounts;

    public OrderLine(Product product, Money price, int quantity, int amounts) {
        this.product = product;

        //참조 투명성을 위해 데이터를 복사한 새로운 객체를 생성함. 그러나 만약 Money 클래스가 불변이면 해당 코드처럼 작성하지 않아도 된다.
        this.price = new Money(price.getValue());

        this.quantity = quantity;
        this.amounts = amounts;
    }

    //    public OrderLine(Product product, Money price, int quantity, int amounts) {
//        this.product = product;
//        this.price = price;
//        this.quantity = quantity;
//        this.amounts = amounts;
//    }

    private int calculateAmounts(){
        return price.getValue() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmounts() {
        return amounts;
    }
}
