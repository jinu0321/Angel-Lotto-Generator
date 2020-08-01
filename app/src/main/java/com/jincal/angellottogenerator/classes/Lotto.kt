package com.jincal.angellottogenerator.classes

import com.beust.klaxon.Json
import java.util.Random

open class Lotto(
    @Json(name = "returnValue")
    var returnValue: String = "fail",
    @Json(name = "totSellamnt")
    var totalSellAmount: Long = 0,   // 누적 금액
    @Json(name = "drwNo")
    var episode: Int = 0,  // 회차
    @Json(name = "drwNoDate")
    var episodeDate: String = "",   // 당첨일
    @Json(name = "firstWinamnt")
    var firstWinnerAmount: Long = 0,  // 1등 당첨금
    @Json(name = "firstPrzwnerCo")
    var firstPrizeWinnerCompanies: Int = 0, // 1등 당첨 인원
    @Json(name = "firstAccumamnt")
    var firstAccumulatedAmount: Long = 0, // 1등 당첨금 총액
    @Json(name = "drwtNo1")
    var drawNumber1: Int = 0, // 당첨번호1
    @Json(name = "drwtNo2")
    var drawNumber2: Int = 0, // 당첨번호2
    @Json(name = "drwtNo3")
    var drawNumber3: Int = 0, // 당첨번호3
    @Json(name = "drwtNo4")
    var drawNumber4: Int = 0, // 당첨번호4
    @Json(name = "drwtNo5")
    var drawNumber5: Int = 0, // 당첨번호5
    @Json(name = "drwtNo6")
    var drawNumber6: Int = 0, // 당첨번호6
    @Json(name = "bnusNo")
    var bonusNumber: Int = 0 // 보너스번호
) {

    val ballNumberList: List<Int>
        get() = listOf(
            drawNumber1,
            drawNumber2,
            drawNumber3,
            drawNumber4,
            drawNumber5,
            drawNumber6,
            bonusNumber
        )

    fun toLottoRealmObject(realmId: Int): LottoRealmObject {
        return LottoRealmObject().apply {
            id = realmId
            totalSellAmount = this@Lotto.totalSellAmount
            episode = this@Lotto.episode
            episodeDate = this@Lotto.episodeDate
            firstWinnerAmount = this@Lotto.firstWinnerAmount
            firstPrizeWinnerCompanies = this@Lotto.firstPrizeWinnerCompanies
            firstAccumulatedAmount = this@Lotto.firstAccumulatedAmount
            drawNumber1 = this@Lotto.drawNumber1
            drawNumber2 = this@Lotto.drawNumber2
            drawNumber3 = this@Lotto.drawNumber3
            drawNumber4 = this@Lotto.drawNumber4
            drawNumber5 = this@Lotto.drawNumber5
            drawNumber6 = this@Lotto.drawNumber6
            bonusNumber = this@Lotto.bonusNumber
        }
    }

    fun getRandomLottoOnlyNumbers(): Lotto {
        val randomNumberSet = mutableSetOf<Int>()
        while (randomNumberSet.size < 6) {
            randomNumberSet.add(Random().nextInt(45) + 1)
        }
        val randomNumberList = randomNumberSet.toList().sorted()
        return Lotto().apply {
            drawNumber1 = randomNumberList[0]
            drawNumber2 = randomNumberList[1]
            drawNumber3 = randomNumberList[2]
            drawNumber4 = randomNumberList[3]
            drawNumber5 = randomNumberList[4]
            drawNumber6 = randomNumberList[5]
        }
    }
}