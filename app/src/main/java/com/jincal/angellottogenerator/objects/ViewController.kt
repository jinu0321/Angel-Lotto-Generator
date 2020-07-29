package kr.blindside.goodlotto.objects

import android.view.View
import android.widget.TextView
import com.jincal.angellottogenerator.R

object ViewController {

    private fun setLottoBallViewSize(ball: TextView) {
        ball.layoutParams.height = ScreenSizeHolder.lottoBallViewSideLength
        ball.layoutParams.width = ScreenSizeHolder.lottoBallViewSideLength
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

    fun setLottoBallViewsSize(balls: List<TextView>) {
        for (ball in balls) {
            setLottoBallViewSize(ball)
        }
    }

    fun setViewHeight(view: View, length: Int) {
        view.layoutParams.height = length
    }

}