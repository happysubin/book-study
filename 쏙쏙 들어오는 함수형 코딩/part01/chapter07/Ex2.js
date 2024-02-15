
function payrollCalcSafe(employees) {
    const copy = deepCopy(employees)
    const payrollChecks = payrollCalc(copy)
    return deepCopy(payrollChecks)
}

userChanges.subscribe(function(user) {
    const userCopy = deepCopy(user)
    processUser(userCopy)
})