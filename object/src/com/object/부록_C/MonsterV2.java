package com.object.부록_C;

public class MonsterV2 {

    private int health;
    private Breed breed;

    public MonsterV2(Breed breed) {
        this.breed = breed;
    }

    public String getAttack (){
        return breed.getAttack();
    }
}
