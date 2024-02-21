function arraySet(array, idx, value) {
    const copy = array.slice()
    copy[idx] = value
    return copy
}

function push(array, elem) {
    const copy = array.slice()
    copy.push(elem)
    return copy
}

function drop_last(array) {
    const copy = array.slice()
    copy.pop()
    return copy
}

function drop_first(array) {
    const copy = array.slice()
    copy.shift()
    return copy
}

//리팩토링

function arraySet(array, idx, value) {
    return withArrayCopy(
        array,
        function(copy) {
            copy[idx] = value
        }
    )
}

function withArrayCopy(array, modify) {
    let copy = array.slice()
    modify(copy)
    return copy
}


function arrayPush(array, elem) {
    return withArrayCopy(
        array,
        function(copy) {
            copy.push(elem)
        }
    )
}

function drop_last(array) {
    return withArrayCopy(
        array,
        function(copy) {
            copy.pop()
        }
    )
}

function drop_first(array) {
    return withArrayCopy(
        array,
        function (copy) {
            copy.shift()
        }
    )
}