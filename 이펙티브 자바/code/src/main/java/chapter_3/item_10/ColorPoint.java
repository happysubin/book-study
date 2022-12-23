package chapter_3.item_10;


public class ColorPoint extends Point{

    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }


    /**
     * 잘못된 코드 대칭성을 위배한다.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint)) return false;
        return super.equals(o) && ((ColorPoint) o).color == color;
    }

    public static void main(String[] args) {
        Point point = new Point(1, 2);
        ColorPoint colorPoint = new ColorPoint(1, 2, Color.RED);

        System.out.println(point.equals(colorPoint)); //true
        System.out.println(colorPoint.equals(point)); //false

    }
}
