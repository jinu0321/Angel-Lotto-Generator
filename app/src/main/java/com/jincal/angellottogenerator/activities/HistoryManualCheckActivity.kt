package com.jincal.angellottogenerator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jincal.angellottogenerator.R
import kotlinx.android.synthetic.main.activity_history_manual_check.*
import com.jincal.angellottogenerator.objects.ViewController

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
    }
}