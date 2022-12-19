package chapter_2.section_2;

public class BuilderNutritionFacts {

    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder{
        private final int servingSize;
        private final int servings;

        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val){
            this.calories = val;
            return this;
        }

        public Builder fat(int val){
            this.fat = val;
            return this;
        }

        public Builder sodium(int val){
            this.sodium = val;
            return this;
        }

        public Builder carbohydrate(int val){
            this.carbohydrate = val;
            return this;
        }
        public BuilderNutritionFacts build(){
            return new BuilderNutritionFacts(this);
        }
    }

    public BuilderNutritionFacts(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args) {
        BuilderNutritionFacts nutritionFacts = new Builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(27)
                .build();

    }
}
