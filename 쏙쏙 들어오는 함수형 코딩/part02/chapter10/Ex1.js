function incrementQuantityByName(cart, name) {
    const item = cart[name]
    const quantity = item['quantity']
    const newQuantity = quantity + 1
    const newItem = objectSet(item, 'quantity', newQuantity)
    const newCart = objectSet(cart, name, newItem)
    return newCart
}

function incrementSizeByName(cart, name) {
    const item = cart[name]
    const size = item['size']
    const newQuantity = size + 1
    const newItem = objectSet(item, 'quantity', newQuantity)
    const newCart = objectSet(cart, name, newItem)
    return newCart
}

function incrementFieldByName(cart, name, field) {
    const item = cart[name]
    const value = item[field]
    const newValue = value + 1
    const newItem = objectSet(item, 'quantity', newValue)
    const newCart = objectSet(cart, name, newItem)
    return newCart
}