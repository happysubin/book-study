function freeTieClip(cart) {
    let hasTie = false
    let hasTieClip = false

    for (let i = 0; i < cart.length; i++) {
        const item = cart[i]
        if(item.name === "tie") //넥타이가 있는지 확인
            return true
        if(item.name === "tie clip") { //넥타이 클립이 있는지 확인
            return true
        }
    }

    if(hasTie && !hasTieClip) {
        const tieClip = make_item("tie clip", 0)
        return add_item(cart, tieClip) //넥타이 클립 추가
    }
    return cart
}


function freeTieClip_v2(cart) {
    let hasTie = isInCart(cart, "tie")
    let hasTieClip = isInCart(cart, "tie Clip")

    if(hasTie && !hasTieClip) {
        const tieClip = make_item("tie clip", 0)
        return add_item(cart, tieClip) //넥타이 클립 추가
    }
    return cart
}

function isInCart(cart, name) {
    for (let i = 0; i < cart.length; i++) {
        if(cart[i].name === name) return true
    }
    return false
}

function remove_item_by_name(cart, name) {
    let idx = null;
    for (let i = 0; i < cart.length; i++) {
        if(cart[i].name === name) {
            idx = i
        }
    }

    if(idx !== null) {
        return removeItems(cart, idx)
    }
    return cart
}

function remove_item_by_name_v2(cart, name) {
    let idx = indexOfItem(cart, name)

    if(idx !== null) {
        return removeItems(cart, idx)
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


function isInCart_v2(cart, name) {
    return indexOfItem(cart, name) !== null;
}



function setPriceByName(cart, name, price) {
    let cart_copy = cart.slice(); //배열 복사
    const i = indexOfItem(cart, name)
    if(i !== null) {
        return arraySet(cart, i, setPrice(cart[i], price))
    }
    return cart_copy
}