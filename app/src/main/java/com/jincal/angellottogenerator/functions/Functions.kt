package com.jincal.angellottogenerator.functions

import android.util.Log
import com.jincal.angellottogenerator.classes.LottoRealmObject
import com.jincal.angellottogenerator.objects.RealmConfigurationSupplier
import io.realm.Realm
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.util.*

fun toDateString(date: String): String {
    return if (date.contains("-")) {
        val list = date.split("-")
        "${list[0]}년 ${list[1]}월 ${list[2]}일"
    } else date
}

fun getLatestEpisode(): Int {
    val drawEpisode896Date = "2020-02-01 20:50:00"
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    val currentDate = Date()
    val startDate = dateFormat.parse(drawEpisode896Date)
    val milliSecondDifference: Long = currentDate.time - startDate.time
    val latestEpisode: Long = (milliSecondDifference / (86400 * 1000 * 7)) + 896
    return latestEpisode.toInt()
}

fun getLatestEpisodeUserHas(): Int {
    val realm = Realm.getInstance(RealmConfigurationSupplier.lottoApiConfiguration)
    val savedLatestEpisode = realm.where<LottoRealmObject>().max("id")!!.toInt()
    Log.d("dedede", savedLatestEpisode.toString())
    realm.close()
    return savedLatestEpisode
}

fun getPermutation(n: Int, r: Int): Int {
    var nPr = 1
    for (i in 1..r) {
        nPr *= n + 1 - i
    }
    return nPr
}

fun getFactorial(n: Int): Int {
    var nFac = 1
    for (i in 2..n) {
        nFac *= i
    }
    return nFac
}

fun getCombination(n: Int, r: Int): Int {
    return getPermutation(n, r) / getFactorial(r)
}