package com.jincal.angellottogenerator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.jincal.angellottogenerator.R
import com.jincal.angellottogenerator.objects.AdManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.id_interstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
        AdManager.loadBannerAd(mAdView)

        mainSelectGeneratorTextView.setOnClickListener {
            startActivity<GeneratorActivity>()
            if (AdManager.interstitialAdCount == 3) {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    AdManager.interstitialAdCount = 0
                }
            } else AdManager.interstitialAdCount ++
        }

        mainSelectInstructionTextView.setOnClickListener {
            startActivity<InstructionActivity>()
        }

        mainSelectHistoryTextView.setOnClickListener {
            startActivity<HistoryActivity>()
            if (AdManager.interstitialAdCount == 3) {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                    AdManager.interstitialAdCount = 0
                }
            } else AdManager.interstitialAdCount ++
        }
    }

    override fun onDestroy() {
        AdManager.interstitialAdCount = 2
        super.onDestroy()
    }
}