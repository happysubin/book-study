package chapter_2.section_2;

public class NyPizza extends Pizza{

    public enum Size{
        SMALL, MEDIUM, LARGE
    };

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder>{
        private final Size size;

        public Builder(Size size) {
            this.size = size;
        }

        @Override
        Pizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public NyPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
    }


    public static void main(String[] args) {
        Pizza pizza = new NyPizza.Builder(Size.SMALL)
                .addTopping(Topping.SAUSAGE)
                .addTopping(Topping.ONION)
                .build();
    }

}
