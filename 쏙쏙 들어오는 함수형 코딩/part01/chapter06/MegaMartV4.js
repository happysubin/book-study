
let shopping_cart = []
let shopping_cart_total = 0


function add_item_to_cart(name, price) {
    const item = make_cart_item(name, price)
    const cart = add_item(shopping_cart, item)
    const total = calc_total(shopping_cart);
    set_cart_total_dom(total) //웹 페이지를 변경하기 위해 DOM을 업데이트하는 코드
    update_shipping_icons_v2(cart) //총 금액에 따라 무료 배송 아이콘 추카 코드를 더함
    update_tax_dom(total)//페이지에 세금을 업데이트 하기위한 코드 추가
}

//cart에 대한 동작과 item에 대한 동작을 알고 있음
function add_item(cart, item) {
    return add_element_last(cart, item)
}

function add_element_last(array, elem) {
    let new_array = array.slice();
    new_array.push(elem)
    return new_array
}

//이 함수는 cart, item 구조를 알고 MegaMart에서 합계를 결정하는 비즈니스 규칙도 담고 있음
function calc_total(cart) {
    let total = 0;
    for (let i = 0; i < cart.length; i++) {
        let item = cart[i]
        total += item.price
    }
    return total
}

function update_tax_dom(total) {
    set_tax_dom(calc_tax(total))
}

//비즈니스 규칙
function calc_tax(amount) {
    return amount * 0.10
}


function update_shipping_icons_v2(cart) {
    let buy_buttons = get_buy_buttons_dom()
    for (let i = 0; i < buy_buttons.length; i++) {
        let button = buy_buttons[i];
        let item = button.item();
        const new_cart = add_item(cart, item.name, item.price)
        const hasFreeShipping = gets_free_shipping_with_item(new_cart, item);
        set_free_shipping_icon(button, hasFreeShipping)
    }
}

function gets_free_shipping_with_item(cart, item) {
    const new_cart = add_item(cart, item)
    return gets_free_shipping(cart)
}

function set_free_shipping_icon(button, isShown) {
    if(isShown) {
        button.show_free_shipping_icon()
    }
    else {
        button.hide_free_shipping_icon()
    }
}

//비즈니스 규칙
function gets_free_shipping(cart) {
    return calc_total(cart) >= 20
}

function set_cart_total_dom(total) {
    //total 사용
}

function main() {
    add_item_to_cart("shoes", 3.45)
}

function make_cart_item(name, price) {
    return {
        name,
        price
    }
}


function delete_handler(name) {
    remove_item_by_name(shopping_cart, name)
    const total = calc_total(shopping_cart)
    set_cart_total_dom(total)
    update_shipping_icons_v2(shopping_cart)
    update_tax_dom(total)
}

//전역변수 cart를 넘기면 전역변수 cart가 수정된다.
function remove_item_by_name(cart, name) {
    let idx = null;
    for (let i = 0; i < cart.length; i++) {
        if(cart[i] === name) {
            idx = i;
        }
    }

    if(idx != null) {
        cart.splice(idx, 1) //배열의 idx위치에 있는 항목을 1개 삭제
    }
}

function delete_handler_v2(name) {
    shopping_cart_total = remove_item_by_name_v2(shopping_cart, name)
    const total = calc_total(shopping_cart)
    set_cart_total_dom(total)
    update_shipping_icons_v2(shopping_cart)
    update_tax_dom(total)
}

function remove_item_by_name_v2(cart, name) {
    let idx = null;
    for (let i = 0; i < cart.length; i++) {
        if(cart[i] === name) {
            idx = i;
        }
    }

    if(idx != null) {
        removeItems(cart, idx, 1) //idx가 존재하는 경우만 카피-온-라이트 동작을 실행
    }
    return cart
}

function removeItems(array, idx, count) {
    let copy = array.slice()
    copy.splice(idx, count)
    return copy
}

function setPriceByName(cart, name, price) {
    let cart_copy = cart.slice(); //배열 복사
    for (let i = 0; i < cart_copy.length; i++) {
        if(cart_copy[i] === name) {
            cart_copy[i] = setPrice(cart_copy[i], price)
        }
    }
    return cart_copy
}

function setPrice(item, new_price) {
    let copy = Object.assign({}, item) //복사본 생김
    copy.price = new_price
    return copy
}