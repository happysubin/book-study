
let shopping_cart = []
let shopping_cart_total = 0


function add_item_to_cart(name, price) {
    shopping_cart = add_item(shopping_cart, name, price)
    calc_cart_total();
}

function add_item(cart, name, price) {
    let new_cart = cart.slice()
    new_cart.push({
        name: name,
        price: price
    })

    return new_cart
}

function calc_cart_total() {
    const shopping_cart_total = calc_total(shopping_cart);
    set_cart_total_dom() //웹 페이지를 변경하기 위해 DOM을 업데이트하는 코드
    update_shipping_icons_v2() //총 금액에 따라 무료 배송 아이콘 추카 코드를 더함
    update_tax_dom()//페이지에 세금을 업데이트 하기위한 코드 추가
}

function calc_total(cart) {
    let total = 0;
    for (let i = 0; i < cart.length; i++) {
        let item = cart[i]
        total += item.price
    }
    return total
}

function update_tax_dom() {
    set_tax_dom(calc_tax(shopping_cart_total))
}

function calc_tax(amount) {
    return amount * 0.10
}


function update_shipping_icons_v2() {
    let buy_buttons = get_buy_buttons_dom()
    for (let i = 0; i < buy_buttons.length; i++) {
        let button = buy_buttons[i];
        let item = button.item();
        if(gets_free_shipping(item.price, shopping_cart_total)) {
            button.show_free_shipping_icon()
        }
        else {
            button.hide_free_shipping_icon()
        }
    }
}


function gets_free_shipping(price, total) {
    return price + total >= 20
}