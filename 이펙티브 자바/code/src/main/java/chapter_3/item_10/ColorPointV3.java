
package chapter_3.item_10;


public class ColorPointV3 {

    private Point point;
    private final Color color;

    public ColorPointV3(int x, int y, Color color) {
        this.point = new Point(x, y);
        this.color = color;
    }

    public Point asPoint(){
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof ColorPointV3)) return false;
        ColorPointV3 cp = (ColorPointV3) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    public static void main(String[] args) {
        ColorPointV3 p1 = new ColorPointV3(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPointV3 p3 = new ColorPointV3(1, 2, Color.BLUE);

        System.out.println(p1.equals(p2)); //false
        System.out.println(p2.equals(p3)); //false
        System.out.println(p3.equals(p1)); //false
    }

}
