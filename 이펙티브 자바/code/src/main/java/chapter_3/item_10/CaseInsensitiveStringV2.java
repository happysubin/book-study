package chapter_3.item_10;

import java.util.Objects;

public class CaseInsensitiveStringV2 {

    private final String s;

    public CaseInsensitiveStringV2(String s) {
        this.s = Objects.requireNonNull(s);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof CaseInsensitiveStringV2 && ((CaseInsensitiveStringV2) o).s.equalsIgnoreCase(s);
    }

    public static void main(String[] args) {
        CaseInsensitiveStringV2 cis = new CaseInsensitiveStringV2("Polish");
        String s = "polish";

        System.out.println(cis.equals(s)); //false
        System.out.println(s.equals(cis)); //false
    }
}
