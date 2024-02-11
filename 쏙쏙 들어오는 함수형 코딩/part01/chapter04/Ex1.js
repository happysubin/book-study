
let shopping_cart_total = 0;

function update_shipping_icons() {
    let buy_buttons = get_buy_buttons_dom()
    for (let i = 0; i < buy_buttons.length; i++) {
        let button = buy_buttons[i];
        let item = button.item();
        if(item.price + shopping_cart_total >= 20) {
            button.show_free_shipping_icon()
        }
        else {
            button.hide_free_shipping_icon()
        }
    }
}

function update_shipping_icons_v2() {
    let buy_buttons = get_buy_buttons_dom()
    for (let i = 0; i < buy_buttons.length; i++) {
        let button = buy_buttons[i];
        let item = button.item();
        if(get_free_shipping(item.price, shopping_cart_total)) {
            button.show_free_shipping_icon()
        }
        else {
            button.hide_free_shipping_icon()
        }
    }
}


function get_free_shipping(price, total) {
    return price + total >= 20
}
