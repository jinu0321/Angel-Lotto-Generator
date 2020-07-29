package com.jincal.angellottogenerator.objects

import com.beust.klaxon.Klaxon
import com.jincal.angellottogenerator.classes.Lotto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.net.URL

object LottoApiGetter {

    private suspend fun getLottoApiFromInternet(episode: Int): String {
        return try {
            val lottoApi = CoroutineScope(Dispatchers.IO).async {
                URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=${episode}").readText()
            }.await()
            lottoApi
        } catch (e: Exception) {
            "failed"
        }
    }

    private suspend fun getLottoFromLottoApi(lottoApi: String): Lotto {
        if (lottoApi == "failed") return Lotto(
            episodeDate = "인터넷에 연결해주세요."
        )
        val lotto = CoroutineScope(Dispatchers.Default).async {
            Klaxon().parse<Lotto>(lottoApi) ?: Lotto()
        }.await()
        return if (lotto.returnValue == "success") lotto else Lotto(
            episodeDate = "해당 회차의 정보가 없어요."
        )
    }

    suspend fun getLottoFromInternet(episode: Int): Lotto {
        val lottoApi =
            getLottoApiFromInternet(
                episode
            )
        return getLottoFromLottoApi(
            lottoApi
        )
    }
}