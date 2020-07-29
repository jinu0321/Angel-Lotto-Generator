package com.jincal.angellottogenerator.objects

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

object InternetConnectionChecker {

    suspend fun checkInternetConnection(): Boolean {
        var returnValue = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=1").readText()
                returnValue = true
            } catch (e: Exception) {
                returnValue = false
            }
        }.join()
        return returnValue
    }
}