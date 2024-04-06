function biggestPurchasesBestCustomers(customers) {
    const bestCustomers = filter(customers, function(customer) {
        return customer.purchases.length >= 3
    })

    const biggestPurchases = map(bestCustomers, function(customer) {
        return;
    })
}