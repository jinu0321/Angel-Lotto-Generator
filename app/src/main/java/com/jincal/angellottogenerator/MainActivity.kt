package com.jincal.angellottogenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*
import kr.blindside.goodlotto.objects.LottoViewSetter
import kr.blindside.goodlotto.objects.ScreenSizeHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        ScreenSizeHolder.screenWidth = displayMetrics.widthPixels

        val ball1to45 = listOf(
            manualInputSelectBall1,
            manualInputSelectBall2,
            manualInputSelectBall3,
            manualInputSelectBall4,
            manualInputSelectBall5,
            manualInputSelectBall6,
            manualInputSelectBall7,
            manualInputSelectBall8,
            manualInputSelectBall9,
            manualInputSelectBall10,
            manualInputSelectBall11,
            manualInputSelectBall12,
            manualInputSelectBall13,
            manualInputSelectBall14,
            manualInputSelectBall15,
            manualInputSelectBall16,
            manualInputSelectBall17,
            manualInputSelectBall18,
            manualInputSelectBall19,
            manualInputSelectBall20,
            manualInputSelectBall21,
            manualInputSelectBall22,
            manualInputSelectBall23,
            manualInputSelectBall24,
            manualInputSelectBall25,
            manualInputSelectBall26,
            manualInputSelectBall27,
            manualInputSelectBall28,
            manualInputSelectBall29,
            manualInputSelectBall30,
            manualInputSelectBall31,
            manualInputSelectBall32,
            manualInputSelectBall33,
            manualInputSelectBall34,
            manualInputSelectBall35,
            manualInputSelectBall36,
            manualInputSelectBall37,
            manualInputSelectBall38,
            manualInputSelectBall39,
            manualInputSelectBall40,
            manualInputSelectBall41,
            manualInputSelectBall42,
            manualInputSelectBall43,
            manualInputSelectBall44,
            manualInputSelectBall45
        )
        LottoViewSetter.setLottoBallView1to45(ball1to45)

        var selectedBallSet = mutableSetOf<Int>()

        fun setSubmitButton() {
            if (selectedBallSet.size > 0 && selectedBallSet.size != 6) {
                mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_available)
            } else {
                mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_inavailable)
            }
        }
        setSubmitButton()

        fun setSelectedBallTextViews() {
            val list = selectedBallSet.toList().sorted()
            mainSelectedBallTextView.text = "선택: " + list.joinToString(", ")
        }

        fun toggle(ballNumber: Int) {
            if (!selectedBallSet.contains(ballNumber)) {
                selectedBallSet.add(ballNumber)
                LottoViewSetter.setLottoBallView(ball1to45[ballNumber - 1], ballNumber)
                setSelectedBallTextViews()
            } else if (selectedBallSet.contains(ballNumber)) {
                selectedBallSet.remove(ballNumber)
                ball1to45[ballNumber - 1].setBackgroundResource(R.drawable.background_ball_zero)
                setSelectedBallTextViews()
            }
        }

        for (ballIndex in 0..44) {
            ball1to45[ballIndex].setOnClickListener {
                toggle(ballIndex + 1)
                setSubmitButton()
            }
        }
    }
}