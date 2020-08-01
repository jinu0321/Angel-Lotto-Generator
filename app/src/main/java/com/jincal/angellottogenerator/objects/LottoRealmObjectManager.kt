package com.jincal.angellottogenerator.objects

import com.jincal.angellottogenerator.classes.Lotto
import com.jincal.angellottogenerator.classes.LottoRealmObject
import com.jincal.angellottogenerator.functions.getLatestEpisode
import io.realm.Realm
import io.realm.kotlin.where

object LottoRealmObjectManager {

    private fun saveLottoRealmObjectToRealm(realm: Realm, lottoRealmObject: LottoRealmObject) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(lottoRealmObject)
        realm.commitTransaction()
    }

    private fun saveLottoToRealm(lotto: Lotto, realmId: Int) {
        val realm = Realm.getInstance(RealmConfigurationSupplier.lottoApiConfiguration)
        val lottoRealmObject = lotto.toLottoRealmObject(realmId)
        saveLottoRealmObjectToRealm(
            realm,
            lottoRealmObject
        )
        realm.close()
    }

    fun getLottoFromRealm(episode: Int): Lotto {
        val realm = Realm.getInstance(RealmConfigurationSupplier.lottoApiConfiguration)
        val temp = realm.where<LottoRealmObject>().equalTo("id", episode).findFirst()
        val lottoRealmObject = if (temp != null) realm.copyFromRealm(temp) else {
            LottoRealmObject(
                episodeDate = "미방영된 회차입니다.",
                episode = episode
            ).apply {
                if (episode <= getLatestEpisode()) episodeDate = "다운로드가 필요한 회차입니다."
            }
        }
        val lotto = lottoRealmObject.toLotto()
        realm.close()
        return lotto
    }

    suspend fun saveLottoToRealmWithIndex(startIndex: Int, endIndex: Int) {
        for (index in startIndex..endIndex) {
            val lotto =
                LottoApiGetter.getLottoFromInternet(
                    index
                )
            saveLottoToRealm(
                lotto,
                index
            )
        }
    }
}