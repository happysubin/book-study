
//아래는 구독자 데이터, 이메일과 초대 횟수
let subscriber = {
    email: "sam@pmail.com",
    rec_count: 16
}

//쿠폰등급
const rank1 = "best"
const rank2 = "good"

//데이터베이스에서 가져온 쿠폰 데이터
const coupon = {
    code: "10PERCENT",
    rank: "bad"
}

//이메일은 데이터다.
const message = {
    from: "newsletter@coupondog.co",
    to: "sam@pmail.com",
    subject: "Your weekly coupons inside",
    body: "Here are your coupons...."
}




//쿠폰 등급을 결정하는 것은 함수다.
function subCouponRank(subscriber) {
    if(subscriber.rec_count >= 10) {
        return "best";
    }
    return "good"
}

//특정 등급의 쿠폰 목록을 선택하는 계산은 함수다.
function selectCouponsByBank(coupons, rank) {
    let ret = []
    for (let c = 0; c < coupons.length; c++) {
        const coupon = coupons[c]
        if(coupon.rank === rank) {
            ret.push(coupon.code)
        }
    }
    return ret;
}



//구독자가 받을 이메일을 계획하는 계산

function emailForSubscriber(subscriber, goods, bests) {
    if(rank === "best") {
        return {
            from: "newsletter@coupondog.co",
            to: subscriber.email,
            subject: "Your best weekly coupons inside",
            body: "Here are the best coupons: " + bests.join(", ")
        }
    }
    return { //rank good
        from: "newsletter@coupondog.co",
        to: subscriber.email,
        subject: "Your good weekly coupons inside",
        body: "Here are the good coupons: " + goods.join(", ")
    }
}

//보낼 이메일 목록을 준비하기

function emailsForSubscribers(subscribers, goods, bests) {
    let emails = []
    for(let s = 0; s < subscribers.length; s++) {
        let subscriber = subscribers[s]
        email = emailForSubscriber(subscriber, goods, bests)
        emails.push(email)
    }
    return emails
}

//현재까지 계산은 함수로 구현, 데이터는 js 데이터 타입을 사용해 구현


//이메일 보내기는 액션이다. 액션도 함수로 구현. 따라서 함수를 보고 계산인지 액션인지 구분하기 어렵다.

function sendIssue() {
    //함수가 있다고 가정
    const coupons = fetchCouponsFromDB()
    const goodCoupons = selectCouponsByRank(coupons, "good")
    const bestCoupons = selectCouponsByRank(coupons, "best")
    const subscribers = fetchSubscribersFromDB()

    const emails = emailsForSubscribers(subscribers, goodCoupons, bestCoupons)

    for(let e = 0; e < emails.length; e++) {
        const email = emails[e]
        emailSystem.send(email)
    }
}