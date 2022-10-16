package com.object.부록_3;

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
