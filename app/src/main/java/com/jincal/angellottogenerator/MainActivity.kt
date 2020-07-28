package com.jincal.angellottogenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import com.jincal.angellottogenerator.objects.SelectedBallHolder
import com.jincal.angellottogenerator.objects.SelectedBallHolder.selectedBallSet
import kotlinx.android.synthetic.main.activity_main.*
import kr.blindside.goodlotto.objects.LottoViewSetter
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
        LottoViewSetter.setLottoBallViewsSize(balls)

        var buttonClickable = false
        fun setSubmitButton() {
            if (selectedBallSet.size in 1..5) {
                if (mainResultCountEditText.text != null) {
                    try {
                        if (mainResultCountEditText.text.toString().toInt() < getCombination(45-selectedBallSet.size, 6-selectedBallSet.size)) {
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
                        if (mainResultCountEditText.text.toString().toInt() < getCombination(selectedBallSet.size, 6)) {
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

        fun setSelectedBallTextViews() {
            val list = selectedBallSet.toList().sorted()
            mainSelectedBallTextView.text = "선택: " + list.joinToString(", ")
        }

        fun toggle(ballNumber: Int) {
            if (!selectedBallSet.contains(ballNumber)) {
                selectedBallSet.add(ballNumber)
                LottoViewSetter.setLottoBallView(balls[ballNumber - 1], ballNumber)
                setSelectedBallTextViews()
            } else if (selectedBallSet.contains(ballNumber)) {
                selectedBallSet.remove(ballNumber)
                balls[ballNumber - 1].setBackgroundResource(R.drawable.background_ball_zero)
                setSelectedBallTextViews()
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
                    } catch (e: Exception) {
                        toast("숫자를 입력해 주세요.")
                    }
                }
            } else {
                toast("번호를 선택해 주세요.")
            }
        }

        mainResultCountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setSubmitButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
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