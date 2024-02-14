
o["price"] = 37

function objectSet(object, key, value) {
    let copy = Object.assign({}, object)
    copy[key] = value
    return copy
}


function setPrice(item, new_price) {
    return objectSet(item, "price", new_price)
}