
//추상화 벽에 만들기

function getsWatchDiscount(cart) {
    let total = 0
    const names = Object.keys(cart)
    for (let i = 0; i < names.length; i++) {
        const item = cart[names[i]]
        total += item.price
    }
    return total > 100 && cart.hasOwnProperty("watch")
}

// 추상화 벽 위에 만들기

function getsWatchDiscount(cart) {
    const total = calc_total(cart)
    const hasWatch = isInCart("watch")
    return total > 100 && hasWatch
}


function logAddToCart(user_id, item) {
    //로그 남김. 이건 액션 코드
}

function add_item(cart, item) {
    logAddToCart(global_user_id, item)
    return objectSet(cart, item.name, item)
}


function add_item_to_cart_v3(name, price) {
    const item = make_cart_item(name, price)
    const cart = add_item(shopping_cart, item)
    const total = calc_total(shopping_cart);
    set_cart_total_dom(total)
    update_shipping_icons(cart)
    update_tax_dom(total)
    logAddToCart()
}