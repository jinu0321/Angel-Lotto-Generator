package com.jincal.angellottogenerator.classes

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LottoRealmObject(
    @PrimaryKey var id: Int = 0,
    var totalSellAmount: Long = 0,   // 누적 금액
    var episode: Int = 0,  // 회차
    var episodeDate: String = "",   // 당첨일
    var firstWinnerAmount: Long = 0,  // 1등 당첨금
    var firstPrizeWinnerCompanies: Int = 0, // 1등 당첨 인원
    var firstAccumulatedAmount: Long = 0, // 1등 당첨금 총액
    var drawNumber1: Int = 0, // 당첨번호1
    var drawNumber2: Int = 0, // 당첨번호2
    var drawNumber3: Int = 0, // 당첨번호3
    var drawNumber4: Int = 0, // 당첨번호4
    var drawNumber5: Int = 0, // 당첨번호5
    var drawNumber6: Int = 0, // 당첨번호6
    var bonusNumber: Int = 0 // 보너스번호
) : RealmObject() {

    fun toLotto(): Lotto {
        return Lotto().apply {
            returnValue = "success"
            totalSellAmount = this@LottoRealmObject.totalSellAmount
            episode = this@LottoRealmObject.episode
            episodeDate = this@LottoRealmObject.episodeDate
            firstWinnerAmount = this@LottoRealmObject.firstWinnerAmount
            firstPrizeWinnerCompanies = this@LottoRealmObject.firstPrizeWinnerCompanies
            firstAccumulatedAmount = this@LottoRealmObject.firstAccumulatedAmount
            drawNumber1 = this@LottoRealmObject.drawNumber1
            drawNumber2 = this@LottoRealmObject.drawNumber2
            drawNumber3 = this@LottoRealmObject.drawNumber3
            drawNumber4 = this@LottoRealmObject.drawNumber4
            drawNumber5 = this@LottoRealmObject.drawNumber5
            drawNumber6 = this@LottoRealmObject.drawNumber6
            bonusNumber = this@LottoRealmObject.bonusNumber
        }
    }
}