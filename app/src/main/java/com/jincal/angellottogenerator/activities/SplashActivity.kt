package com.jincal.angellottogenerator.activities

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.jincal.angellottogenerator.R
import com.jincal.angellottogenerator.classes.Lotto
import com.jincal.angellottogenerator.functions.getLatestEpisode
import com.jincal.angellottogenerator.functions.getLatestEpisodeUserHas
import com.jincal.angellottogenerator.objects.InternetConnectionChecker
import com.jincal.angellottogenerator.objects.LottoApiGetter
import com.jincal.angellottogenerator.objects.LottoRealmObjectManager
import com.jincal.angellottogenerator.objects.ScreenSizeHolder
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 500 // 0.5 sec
    private val MY_REQUEST_CODE = 100
    private var mAppUpdateManager: AppUpdateManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /////////////////////////////////////////////// app update
        mAppUpdateManager = AppUpdateManagerFactory.create(this)
        // 업데이트 사용 가능 상태인지 체크
        val appUpdateInfoTask: Task<AppUpdateInfo> = mAppUpdateManager!!.getAppUpdateInfo()
        // 사용가능 체크 리스너를 달아준다
        appUpdateInfoTask.addOnSuccessListener {
            fun onSuccess(appUpdateInfo: AppUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    &&  // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    // 업데이트가 사용 가능한 상태 (업데이트 있음) -> 이곳에서 업데이트를 요청해주자
                    try {
                        mAppUpdateManager!!.startUpdateFlowForResult(
                            appUpdateInfo,  // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                            AppUpdateType.FLEXIBLE,  // 현재 Activity
                            this@SplashActivity,  // 전역변수로 선언해준 Code
                            MY_REQUEST_CODE
                        )
                    } catch (e: SendIntentException) {}
                }
            }
        }


        ///////////////////////////////////////////////

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