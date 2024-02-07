
//함수 안에서 액션을 호출하므로 함수 전체가 액션
function figurePayout(affiliate) {
    const owed = affiliate.slaes * affiliate.commission
    id (owed > 100) { //백달러 이하면 송금 금지
        sendPayout(affiliate.bank_code, owed) // 액션
    } 
}


//액션을 호출하므로 여기도 액션
function affiliatePayout(affiliates) {
    for(let i = 0; i < affiliates.length; i++) {
        figurePayout(affiliates[i])
    }
}

//마찬가지로 여기도 액션
function main(affiliates) {
    affiliatePayout(affiliates)
}

//모든 함수가 전부 액션이다. 액션이 퍼져나가버림.