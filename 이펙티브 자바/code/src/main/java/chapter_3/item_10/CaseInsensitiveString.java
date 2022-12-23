package chapter_3.item_10;

import java.util.Objects;

public class CaseInsensitiveString {

    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof CaseInsensitiveString){
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if(o instanceof String){ //한 방향으로만 동작한다.
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";

        System.out.println(cis.equals(s)); //true
        System.out.println(s.equals(cis)); //false
    }
}
