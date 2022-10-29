package start.ddd.chapter_4.section_3;

import javax.persistence.Column;
import java.io.Serializable;

public class Address implements Serializable {

    @Column(name = "shipping_zipcode")
    private String zipcode;

    @Column(name = "shipping_addr1")
    private String address1;

    @Column(name = "shipping_addr2")
    private String address2;
}
