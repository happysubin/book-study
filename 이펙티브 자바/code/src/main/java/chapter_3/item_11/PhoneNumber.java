package chapter_3.item_11;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber {

    private final int areaCode;
    private final int prefix;
    private final int lineNum;


    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (areaCode != that.areaCode) return false;
        if (prefix != that.prefix) return false;
        return lineNum == that.lineNum;
    }

//    @Override
//    public int hashCode() {
//        int result = areaCode;
//        result = 31 * result + prefix;
//        result = 31 * result + lineNum;
//        return result;
//    }

    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>();
        map.put(new PhoneNumber(111,222,333), "subin");

        System.out.println(map.get(new PhoneNumber(111,222,333)));
    }
}
