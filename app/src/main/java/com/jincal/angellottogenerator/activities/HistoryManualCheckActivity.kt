package com.jincal.angellottogenerator.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jincal.angellottogenerator.R
import com.jincal.angellottogenerator.classes.LottoRealmObject
import com.jincal.angellottogenerator.objects.RealmConfigurationSupplier
import com.jincal.angellottogenerator.objects.SelectedBallHolder
import kotlinx.android.synthetic.main.activity_history_manual_check.*
import com.jincal.angellottogenerator.objects.ViewController
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_generator.*
import org.jetbrains.anko.textColor

class HistoryManualCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_manual_check)

        val balls = listOf(
            manualSelectBall1,
            manualSelectBall2,
            manualSelectBall3,
            manualSelectBall4,
            manualSelectBall5,
            manualSelectBall6,
            manualSelectBall7,
            manualSelectBall8,
            manualSelectBall9,
            manualSelectBall10,
            manualSelectBall11,
            manualSelectBall12,
            manualSelectBall13,
            manualSelectBall14,
            manualSelectBall15,
            manualSelectBall16,
            manualSelectBall17,
            manualSelectBall18,
            manualSelectBall19,
            manualSelectBall20,
            manualSelectBall21,
            manualSelectBall22,
            manualSelectBall23,
            manualSelectBall24,
            manualSelectBall25,
            manualSelectBall26,
            manualSelectBall27,
            manualSelectBall28,
            manualSelectBall29,
            manualSelectBall30,
            manualSelectBall31,
            manualSelectBall32,
            manualSelectBall33,
            manualSelectBall34,
            manualSelectBall35,
            manualSelectBall36,
            manualSelectBall37,
            manualSelectBall38,
            manualSelectBall39,
            manualSelectBall40,
            manualSelectBall41,
            manualSelectBall42,
            manualSelectBall43,
            manualSelectBall44,
            manualSelectBall45,
            manualEmptyBall1,
            manualEmptyBall2,
            manualEmptyBall3,
            manualEmptyBall4
        )
        ViewController.setLottoBallViewsSize(balls)

        val selectedBallList = mutableListOf<Int>()

        fun checkResult() {
            if (selectedBallList.size == 6) {
                val realm = Realm.getInstance(RealmConfigurationSupplier.lottoApiConfiguration)
                val temp = realm.where<LottoRealmObject>().findAll().map { it.toLotto() }

                var equalCount = 0
                manualFirstPrizeExistTextVIewTmp.textColor = Color.BLACK
                manualFirstPrizeExistTextVIewTmp.text = "1등 당첨 이력이 없는 번호입니다."
                for (lotto in temp) {
                    for (i in 0..5) {
                        if (lotto.ballNumberList[i] == selectedBallList[i]) equalCount++
                    }
                    if (equalCount == 6) {
                        manualFirstPrizeExistTextVIewTmp.textColor = Color.RED
                        manualFirstPrizeExistTextVIewTmp.text = "${lotto.episode}회 1등 번호입니다."
                        break
                    }
                    equalCount = 0
                }
            } else {
                manualFirstPrizeExistTextVIewTmp.textColor = Color.GRAY
                manualFirstPrizeExistTextVIewTmp.text = "번호를 6개 선택해주세요."
            }
        }
        checkResult()

        fun toggle(ballNumber: Int) {
            if (!selectedBallList.contains(ballNumber)) {
                selectedBallList.add(ballNumber)
                ViewController.setLottoBallView(balls[ballNumber - 1], ballNumber)
            } else if (selectedBallList.contains(ballNumber)) {
                selectedBallList.remove(ballNumber)
                balls[ballNumber - 1].setBackgroundResource(R.drawable.background_ball_zero)
            }
            selectedBallList.sort()
            checkResult()
        }

        for (ballIndex in 0..44) {
            balls[ballIndex].setOnClickListener {
                toggle(ballIndex + 1)
            }
        }
    }
}