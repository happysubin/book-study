
let shopping_cart = []
let shopping_cart_total = 0

function add_item_to_cart(name, price) {
    shopping_cart.push({
        name: name,
        price: price
    })

    calc_cart_total();
}

function calc_cart_total() {
    shopping_cart_total = 0;

    for (let i = 0; i < shopping_cart.length; i++) {
        let item = shopping_cart[i];
        shopping_cart_total += item.price
    }
    set_cart_total_dom() //웹 페이지를 변경하기 위해 DOM을 업데이트하는 코드
    update_shipping_icons() //총 금액에 따라 무료 배송 아이콘 추카 코드를 더함
    update_tax_dom()//페이지에 세금을 업데이트 하기위한 코드 추가
}

function update_shipping_icons() {
    let buy_buttons = get_buy_buttons_dom(); //페이지의 모든 구매 버튼을 가져와 반복문 적용
    for (let i = 0; i < buy_buttons.length; i++) {
        let button = buy_buttons[i];
        let item = button.item;

        if(item.price + shopping_cart_total >= 20) { //무료 배송이 가능한지 확인
            button.show = show_free_shipping_icon() //배송 아이콘을 보여줌
        }
        else {
            button.hide_free_shipping_icon() //배송 아이콘을 숨김
        }
    }
}

function update_tax_dom() {
        set_tax_dom(shopping_cart_total * 0.10)
    }

