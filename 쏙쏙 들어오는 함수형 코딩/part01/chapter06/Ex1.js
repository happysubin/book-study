let mailing_list = []

function add_contact(email) {
    mailing_list.push(email)
}

function submit_form_handler(event) {
    const form = event.target
    const email = form.elements["email"].value
    add_contact(email)
}

function submit_form_handler_v2(event) {
    const form = event.target
    const email = form.elements["email"].value
    mailing_list = add_contact(email)
}


function add_contact_v2(email_list, email) {
    const new_email_list = email_list.slice()
    new_email_list.push(email)
    return new_email_list
}

let a = [1, 2, 3, 4]
const b = a.pop()


//읽기와 함수 분리
function last_element(array) {
    return array[array.length - 1]
}

function drop_last(array) {
    array.pop()
}

//2개의 값 리턴

function pop(array) {
    const array_copy = array.slice()
    const first =  array_copy.pop()
    return {
        array_copy,
        first
    }
}

function push(array, elem) {
    let new_array = array.slice()
    new_array.push(elem)
    return new_array
}

function add_contact(mailing_list, email) {
    let list_copy = mailing_list.slice();
    list_copy.push(email)
    return list_copy
}

function add_contact_v2(mailing_list, email) {
    return push(mailing_list, email)
}


function arraySet(array, idx, value) {
    let copy = array.slice();
    copy[idx] = value
    return copy
}