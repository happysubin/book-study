
function add_item_to_cart(name, price) {
    const item = make_cart_item(name, price)
    const cart = add_item(shopping_cart, item)
    const total = calc_total(shopping_cart);
    set_cart_total_dom(total)
    update_shipping_icons(cart)
    update_tax_dom(total)

    black_friday_promotion(shopping_cart)//추가된 코드
}

//데이터를 전달하기 전에 복사
function add_item_to_cart_v2(name, price) {
    const item = make_cart_item(name, price)
    const cart = add_item(shopping_cart, item)
    const total = calc_total(shopping_cart);
    set_cart_total_dom(total)
    update_shipping_icons(cart)
    update_tax_dom(total)

    const cart_copy = deepCopy(shopping_cart) //깊은 복사를 진행
    black_friday_promotion(shopping_cart)//추가된 코드
    shopping_cart = deepCopy(cart_copy) //깊은 복사를 진행
}


function add_item_to_cart_v3(name, price) {
    const item = make_cart_item(name, price)
    const cart = add_item(shopping_cart, item)
    const total = calc_total(shopping_cart);
    set_cart_total_dom(total)
    update_shipping_icons(cart)
    update_tax_dom(total)

    shopping_cart = black_firday_promotion_safe(cart)
}

function black_friday_promotion_safe(cart) {
    const cart_copy = deepCopy(cart) //깊은 복사를 진행
    black_friday_promotion(cart_copy)//추가된 코드
    return deepCopy(cart_copy) //깊은 복사를 진행
}