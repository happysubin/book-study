function objectSet(object, key, value) {
    const copy = Object.assign({}, object)
    copy[key] = value
    return copy
}

function objectDelete(object, key) {
    const copy = Object.assign({}, object)
    delete copy[key]
    return copy
}

//리팩토링

function objectSet(object, key, value) {
    return withObjectCopy(object, function(copy) {
        copy[key] = value
    })
}

function objectDelete(object, key) {
    return withObjectCopy(object, function(copy) {
        delete copy[key]
    })
}

function withObjectCopy(object, modify){
    let copy = Object.assign({}, object)
    modify(copy)
    return copy
}