package com.jincal.angellottogenerator.activities

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.jincal.angellottogenerator.R
import kotlinx.coroutines.*
import com.jincal.angellottogenerator.classes.Lotto
import com.jincal.angellottogenerator.functions.getLatestEpisode
import com.jincal.angellottogenerator.functions.getLatestEpisodeUserHas
import com.jincal.angellottogenerator.objects.InternetConnectionChecker
import com.jincal.angellottogenerator.objects.LottoApiGetter
import com.jincal.angellottogenerator.objects.LottoRealmObjectManager
import com.jincal.angellottogenerator.objects.ScreenSizeHolder
import io.realm.Realm
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 500 // 0.5 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        ScreenSizeHolder.screenWidth = displayMetrics.widthPixels
        ScreenSizeHolder.screenHeight = displayMetrics.heightPixels

        Realm.init(this)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            suspend fun checkAndGetLottoApiUntilTheLatest() {
                if (getLatestEpisodeUserHas() < getLatestEpisode() - 1) {
                    LottoRealmObjectManager.saveLottoToRealmWithIndex(
                        getLatestEpisodeUserHas() + 1, getLatestEpisode() - 1
                    )
                }
            }

            suspend fun checkAndGetTheLatestLottoApi() {
                if (getLatestEpisodeUserHas() == getLatestEpisode() - 1) {
                    var latestEpisodeLotto =
                        Lotto()
                    CoroutineScope(Dispatchers.IO).launch {
                        latestEpisodeLotto = LottoApiGetter.getLottoFromInternet(
                            getLatestEpisode()
                        )
                    }.join()
                    if (latestEpisodeLotto.returnValue == "success") {
                        LottoRealmObjectManager.saveLottoToRealmWithIndex(
                            getLatestEpisode(),
                            getLatestEpisode()
                        )
                    } else {
                        toast(R.string.main_api_not_uploaded)
                    }
                }
            }

            suspend fun refillTheLottoApiRealm() {
                CoroutineScope(Dispatchers.Main).launch {
                    if (InternetConnectionChecker.checkInternetConnection()) {
                        checkAndGetLottoApiUntilTheLatest()
                        checkAndGetTheLatestLottoApi()
                        toast(R.string.main_apidownload_success)
                    } else {
                        toast(R.string.main_internetConnection_fail)
                    }
                }.join()
            }

            GlobalScope.launch {
                CoroutineScope(Dispatchers.Default).launch {
                    refillTheLottoApiRealm()
                }.join()
                startActivity<MainActivity>()
                // close this activity
                finish()
            }
        }, SPLASH_TIME_OUT)
    }

    override fun onBackPressed() {
        // don't terminate the app when it's loading the lotto api.
    }
}