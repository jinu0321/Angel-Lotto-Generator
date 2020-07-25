package kr.blindside.goodlotto.objects

import android.widget.TextView
import com.jincal.angellottogenerator.R

object LottoViewSetter {

    private fun setLottoBallViewSize(ball: TextView) {
        ball.layoutParams.height = ScreenSizeHolder.lottoBallViewSideLength
        ball.layoutParams.width = ScreenSizeHolder.lottoBallViewSideLength
    }

    fun setLottoBallViewNumber(ball: TextView, number: Int) {
        when (number) {
            0 -> ball.setBackgroundResource(R.drawable.background_ball_zero)
            in 1..10 -> ball.setBackgroundResource(R.drawable.background_ball_yellow)
            in 11..20 -> ball.setBackgroundResource(R.drawable.background_ball_blue)
            in 21..30 -> ball.setBackgroundResource(R.drawable.background_ball_red)
            in 31..40 -> ball.setBackgroundResource(R.drawable.background_ball_gray)
            in 41..45 -> ball.setBackgroundResource(R.drawable.background_ball_green)
        }
        ball.text = "${number}"
    }

    fun setLottoBallView1to45(ball1to45: List<TextView>) {
        for (ball in ball1to45) {
            setLottoBallViewSize(ball)
        }
    }

    fun setLottoBallView(ball: TextView, number: Int) {
        when (number) {
            0 -> ball.setBackgroundResource(R.drawable.background_ball_zero)
            in 1..10 -> ball.setBackgroundResource(R.drawable.background_ball_yellow)
            in 11..20 -> ball.setBackgroundResource(R.drawable.background_ball_blue)
            in 21..30 -> ball.setBackgroundResource(R.drawable.background_ball_red)
            in 31..40 -> ball.setBackgroundResource(R.drawable.background_ball_gray)
            in 41..45 -> ball.setBackgroundResource(R.drawable.background_ball_green)
        }
        ball.text = "${number}"
    }

}