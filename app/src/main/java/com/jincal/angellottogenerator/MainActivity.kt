package com.jincal.angellottogenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import com.jincal.angellottogenerator.objects.SelectedBallHolder
import com.jincal.angellottogenerator.objects.SelectedBallHolder.selectedBallSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.jincal.angellottogenerator.objects.InternetConnectionChecker
import kr.blindside.goodlotto.objects.ViewController
import kr.blindside.goodlotto.objects.ScreenSizeHolder
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        ScreenSizeHolder.screenWidth = displayMetrics.widthPixels
        ScreenSizeHolder.screenHeight = displayMetrics.heightPixels

        val balls = listOf(
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
            manualInputSelectBall45,
            manualInputEmptyBall1,
            manualInputEmptyBall2,
            manualInputEmptyBall3,
            manualInputEmptyBall4
        )
        ViewController.setLottoBallViewsSize(balls)
        val textViewSize = ScreenSizeHolder.screenHeight / 12
        ViewController.setViewHeight(mainNameTextView, textViewSize)
        ViewController.setViewHeight(mainResultCountEditText, textViewSize)

        var buttonClickable = false
        fun setSubmitButton() {
            if (selectedBallSet.size in 1..5) {
                if (mainResultCountEditText.text != null) {
                    try {
                        if (mainResultCountEditText.text.toString().toInt() <= getCombination(45-selectedBallSet.size, 6-selectedBallSet.size)
                            && mainResultCountEditText.text.toString().toInt() > 0) {
                            mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_available)
                            buttonClickable = true
                        } else {
                            mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_inavailable)
                            buttonClickable = false
                        }
                    } catch (e: Exception) {}
                }
            } else if (selectedBallSet.size > 6) {
                if (mainResultCountEditText.text != null) {
                    try {
                        if (mainResultCountEditText.text.toString().toInt() < getCombination(selectedBallSet.size, 6)
                            && mainResultCountEditText.text.toString().toInt() > 0) {
                            mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_available)
                            buttonClickable = true
                        } else {
                            mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_inavailable)
                            buttonClickable = false
                        }
                    } catch (e: Exception) {}
                }
            } else {
                mainSubmitButton.setBackgroundResource(R.drawable.background_manualsubmit_submitbutton_inavailable)
                buttonClickable = false
            }
        }
        setSubmitButton()

        fun setMainSelectedBallCountTextView() {
            mainSeletedBallCountTextView.text = "${SelectedBallHolder.selectedBallCounts}개"
        }

        fun toggle(ballNumber: Int) {
            if (!selectedBallSet.contains(ballNumber)) {
                selectedBallSet.add(ballNumber)
                ViewController.setLottoBallView(balls[ballNumber - 1], ballNumber)
                setMainSelectedBallCountTextView()
            } else if (selectedBallSet.contains(ballNumber)) {
                selectedBallSet.remove(ballNumber)
                balls[ballNumber - 1].setBackgroundResource(R.drawable.background_ball_zero)
                setMainSelectedBallCountTextView()
            }
        }

        for (ballIndex in 0..44) {
            balls[ballIndex].setOnClickListener {
                toggle(ballIndex + 1)
                setSubmitButton()
            }
        }

        mainSubmitButton.setOnClickListener {
            if (buttonClickable) {
                if (mainResultCountEditText.text != null) {
                    try {
                        if (mainResultCountEditText.text.toString().toInt() > 0) {
                            SelectedBallHolder.resultCount = mainResultCountEditText.text.toString().toInt()
                            startActivity<ResultActivity>()
                        }
                    } catch (e: Exception) {}
                }
            } else {
                toast("올바르게 입력해 주세요.")
            }
        }

        mainResultCountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setSubmitButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        mainClearButton.setOnClickListener {
            selectedBallSet.clear()
            for (index in 0..44) balls[index].background = getDrawable(R.drawable.background_ball_zero)
            setMainSelectedBallCountTextView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        selectedBallSet.clear()
    }
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