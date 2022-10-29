package start.ddd.chapter_4.section_3;

import lombok.Getter;

@Getter
public class Email {

    private String address;

    public Email(String address) {
        this.address = address;
    }
}
