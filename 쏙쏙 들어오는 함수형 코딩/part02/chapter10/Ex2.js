
let foods = []

function cookAndEatFoods() {
    for (let i = 0; i < foods.length; i++) {
        const food = foods[i]
        cook(food)
        eat(food)
    }
}

function cleanDishes() {
    for (let i = 0; i < foods.length; i++) {
        const dish = dishes[i]
        wash(dish)
        dry(dish)
        putAways(dish)
    }
}

//리팩토링

function forEach(array, f) {
    for (let i = 0; i < array.length; i++) {
        const item = array[i]
        f(item)
    }
}

function cookAndEat(food) {
    cook(food)
    eat(food)
}

function clean(dish) {
    wash(dish)
    dry(dish)
    putAway(dish)
}

forEach(foods, cookAndEat)
forEach(foods, clean)

