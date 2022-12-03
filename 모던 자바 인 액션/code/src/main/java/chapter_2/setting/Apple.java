package chapter_2.setting;

public class Apple {
    private Color color;
    private int weight;

    public Apple(Color color) {
        this.color = color;
    }

    public Apple(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}
