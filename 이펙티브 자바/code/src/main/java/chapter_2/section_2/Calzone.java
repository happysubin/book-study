package chapter_2.section_2;

public class Calzone extends Pizza{

    private final boolean sauceInside;


    public static class Builder extends Pizza.Builder<Builder>{
        private boolean sauceInside = false;

        public Builder sauceInside(){
            sauceInside = true;
            return this;
        }

        @Override
        Pizza build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public Calzone(Builder builder) {
        super(builder);
        this.sauceInside = builder.sauceInside;
    }


    public static void main(String[] args) {
        Pizza pizza = new Calzone.Builder()
                .addTopping(Topping.SAUSAGE)
                .addTopping(Topping.ONION)
                .sauceInside()
                .build();
    }

}
