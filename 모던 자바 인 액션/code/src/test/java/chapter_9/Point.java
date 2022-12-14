package chapter_9;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point moveRightBy(int x){
        return new Point(this.x + x, this.y);
    }

    public final static Comparator<Point> compareByXAndThenY =
            Comparator.comparing(Point::getX).thenComparing(Point::getY);

    public static List<Point> moveAllPointsRightBy(List<Point> points, int x){
        return points.stream()
                .map(p -> new Point(p.getX() + x, p.getY()))
                .collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (getX() != point.getX()) return false;
        return getY() == point.getY();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    public static void main(String[] args) {
        List<Point> points = Arrays.asList(new Point(1, 2), null);
        points.stream()
                .map(Point::getX)
                .forEach(System.out::println);
    }
}
