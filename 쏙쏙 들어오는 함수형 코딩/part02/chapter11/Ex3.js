


function tryCatch(f, errorHandler) {
    try {
        return f()
    } catch(error) {
        return errorHandler(error)
    }
}

function when(test, then) {
    if(test) {
        return then()
    }
}


function ex1() {
    try {
        saveUserData(user)
    } catch(error) {
        logToSnapErrors(error)
    }
}


function ex2() {
    try {
        fetchProduct(productId)
    } catch(error) {
        logToSnapErrors(error)
    }
}

//중복 해결

function withLogging(f) {
    try {
        f()
    } catch(error) {
        logToSnapErrors(error)
    }
}

function saveUserDataWithLogging(user) {
    try{
        saveUserDataNoLogging(user)
    } catch(error) {
        logToSnapErrors(error)
    }
}

function fetchProductWithLogging(productId) {
    try {
        fetchProductNoLogging(productId)
    } catch (error) {
        logToSnapErrors(error)
    }
}

function wrapLogging(f) {
    return function(arg) {
        try {
            f(arg)
        } catch (errors) {
            logToSnapErrors(error)
        }
    }
}

let saveUserDataWithLogging = wrapLogging(saveUserDataNoLogging)
let saveUserDataWithLogging = wrapLogging(fetchProductNoLogging)
