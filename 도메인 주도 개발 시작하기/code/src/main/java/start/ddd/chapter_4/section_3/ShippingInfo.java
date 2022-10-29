package start.ddd.chapter_4.section_3;

import javax.persistence.*;

@Embeddable
public class ShippingInfo {

    @Embedded
    private Address address;

    @Column(name = "shipping_message")
    private String message;

    @Embedded
    private Receiver receiver;

}
