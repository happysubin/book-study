package start.ddd.chapter_4.section_3;

public class Receiver {

    private String name;
    private String phone;

    public Receiver(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    protected Receiver(){} //프록시를 위한 생성자
}
