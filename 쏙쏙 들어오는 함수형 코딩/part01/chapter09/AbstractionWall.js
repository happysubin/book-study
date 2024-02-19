
//배열로 만든 장바구니

function add_item(cart, item) {
    return add_element_last(cart, item)
}

function calc_total(cart) {
    let total = 0
    for (let i = 0; i < cart.length; i++) {
        const item = cart[i];
        total += item.price
    }
    return total
}


function setPriceByName(cart, name, price) {
    let cart_copy = cart.slice();
    for (let i = 0; i < cart_copy.length; i++) {
        if(cart_copy[i].name === name) {
            cart_copy[i] = setPrice(cart_copy[i], price)
        }
    }
    return cart_copy
}


function remove_item_by_name(cart, name) {
    const idx = indexOfItem(cart, name)
    if(idx !== null) {
        return splice(cart, idx, 1)
    }
    return cart
}

function indexOfItem(cart, name) {
    for (let i = 0; i < cart.length; i++) {
        if(cart[i].name === name) {
            return i
        }
    }
    return null
}

function isInCart(cart, name) {
    return indexOfItem(cart, name) !== null
}

//객체로 만든 장바구니

function add_item(cart, item) {
    return objectSet(cart, item.name, item)
}

function calc_total(cart) {
    let total = 0
    const names = Object.keys(cart)
    for (let i = 0; i < names.length; i++) {
        const item = cart[names[i]]
        total += item.price
    }
    return total
}

function setPriceByName(cart, name, price) {
    if(isInCart(cart, name)) {
        const item = cart[name]
        const copy = setPrice(item, price)
        return objectSet(cart, name, copy)
    }
    const item = make_item(name, price)
    return objectSet(cart, name, item)
}

function remove_item_by_name(cart, name) {
    return objectDelete(cart, name)
}

function isInCart(cart, name) {
    return cart.hasOwnProperty(name)
}