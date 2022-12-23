
package chapter_3.item_10;


public class ColorPointV2 extends Point{

    private final Color color;

    public ColorPointV2(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }


    /**
     * 대칭성은 지켜주나 추이성을 깨버린다.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;

        if(!(o instanceof ColorPointV2)){
            return o.equals(this);
        }
        return super.equals(o) && ((ColorPointV2) o).color == color;
    }

    public static void main(String[] args) {
        ColorPointV2 p1 = new ColorPointV2(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPointV2 p3 = new ColorPointV2(1, 2, Color.BLUE);

        System.out.println(p1.equals(p2)); //true
        System.out.println(p2.equals(p3)); //true
        System.out.println(p3.equals(p1)); //false
    }

}
