package com.jincal.angellottogenerator.classes

import com.jincal.angellottogenerator.objects.RealmConfigurationSupplier
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where

open class MyLottoRealmObject(
    @PrimaryKey var id: Int = 0,
    var episode: Int = 0,
    var ball1: Int = 0,
    var ball2: Int = 0,
    var ball3: Int = 0,
    var ball4: Int = 0,
    var ball5: Int = 0,
    var ball6: Int = 0
) : RealmObject() {

    fun submit(episode: Int, selectedBallSet: MutableSet<Int>) {
        val realm = Realm.getInstance(RealmConfigurationSupplier.myLottoConfiguration)
        val nextId = realm.where<MyLottoRealmObject>().max("id")?.toInt() ?: 0
        val selectedBallList = selectedBallSet.toList().sorted()
        this.apply {
            id = nextId + 1
            this.episode = episode
            ball1 = selectedBallList[0]
            ball2 = selectedBallList[1]
            ball3 = selectedBallList[2]
            ball4 = selectedBallList[3]
            ball5 = selectedBallList[4]
            ball6 = selectedBallList[5]
        }
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(this)
        realm.commitTransaction()
        realm.close()
    }

    fun countAll(countMoney: Boolean): Int {
        val realm = Realm.getInstance(RealmConfigurationSupplier.myLottoConfiguration)
        val count = realm.where<MyLottoRealmObject>().findAll().count()
        realm.close()
        return if (countMoney) count * 1000 else count
    }

    fun countWeeksBoughtLotto(): Int {
        val realm = Realm.getInstance(RealmConfigurationSupplier.myLottoConfiguration)
        val episodeMutableSet = mutableSetOf<Int>()
        realm.where<MyLottoRealmObject>().findAll().apply {
            this.forEach {
                episodeMutableSet.add(it.episode)
            }
        }
        realm.close()
        return episodeMutableSet.count()
    }

    fun submit() {
        val realm = Realm.getInstance(RealmConfigurationSupplier.myLottoConfiguration)
        val nextId = realm.where<MyLottoRealmObject>().max("id")?.toInt() ?: 0
        id = nextId + 1
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(this)
        realm.commitTransaction()
        realm.close()
    }

    fun delete() {
        val realm = Realm.getInstance(RealmConfigurationSupplier.myLottoConfiguration)
        realm.beginTransaction()
        this.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    private fun toMyLottoRealmObjectFromStringFromQrcodeUrl(
        episode: String,
        ballNumbers: String
    ): MyLottoRealmObject {
        this.episode = episode.toInt()
        ball1 = (ballNumbers[0].toString() + ballNumbers[1].toString()).toInt()
        ball2 = (ballNumbers[2].toString() + ballNumbers[3].toString()).toInt()
        ball3 = (ballNumbers[4].toString() + ballNumbers[5].toString()).toInt()
        ball4 = (ballNumbers[6].toString() + ballNumbers[7].toString()).toInt()
        ball5 = (ballNumbers[8].toString() + ballNumbers[9].toString()).toInt()
        ball6 = (ballNumbers[10].toString() + ballNumbers[11].toString()).toInt()
        return this
    }

    fun splitUrlOfLottoResultFromQrcodeToMyLottoRealmObjectList(urlOfLottoResultFromQrcode: String): List<MyLottoRealmObject> {
        val episodeAndBallNumbers = urlOfLottoResultFromQrcode.substringAfter("/?v=")
        val splited = episodeAndBallNumbers.split(episodeAndBallNumbers[4]).toMutableList()
        splited[splited.lastIndex] = splited[splited.lastIndex].removeRange(12..21)
        val lottoCount = splited.size - 1

        var myLottoRealmObjectList = mutableListOf<MyLottoRealmObject>()
        for (lottoResultIndex in 1..lottoCount) {
            myLottoRealmObjectList.add(
                MyLottoRealmObject().toMyLottoRealmObjectFromStringFromQrcodeUrl(
                    splited[0],
                    splited[lottoResultIndex]
                )
            )
        }
        if (lottoCount < 5) {
            for (lottoResultIndex in lottoCount + 1..5) {
                myLottoRealmObjectList.add(MyLottoRealmObject())
            }
        }
        return myLottoRealmObjectList.toList()
    }
}