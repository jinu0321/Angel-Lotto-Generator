package com.jincal.angellottogenerator.objects

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*

object AdManager {
    // count 3일 때 광고 게재, 0으로 만듦
    // 2가 아닐 때는 increment
    var interstitialAdCount = 2

    // 배너광고 띄우기
    fun loadBannerAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}