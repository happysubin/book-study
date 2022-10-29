package start.ddd.chapter_4.section_3;

import start.ddd.chapter_3.section_5.ProductId;

import javax.persistence.Embeddable;

@Embeddable
public class OrderLine {

    private ProductId productId;

    private Money price;

    private int quantity;

    private Money amounts;
}
