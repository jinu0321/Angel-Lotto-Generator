package com.jincal.angellottogenerator.objects

import android.view.View
import android.widget.TextView
import com.jincal.angellottogenerator.R
import com.jincal.angellottogenerator.functions.toDateString
import java.text.DecimalFormat

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

    private fun setLottoDateView(dateView: TextView, date: String) {
        dateView.text = toDateString(date)
    }

    private fun setLottoEpisodeView(episodeView: TextView, episode: Int) {
        episodeView.text = "${episode}회"
    }

    fun setLottoLayout(lottoEpisode: Int, dateView: TextView, episodeView: TextView, ballViewList: List<TextView>, firstWinnerCompaniesTextView: TextView, firstWinnerAmountTextView: TextView) {
        val lotto =
            LottoRealmObjectManager.getLottoFromRealm(
                lottoEpisode
            )
        setLottoDateView(
            dateView,
            lotto.episodeDate
        )
        setLottoEpisodeView(
            episodeView,
            lotto.episode
        )
        for (index in 0..6) {
            setLottoBallView(
                ballViewList[index],
                lotto.ballNumberList[index]
            )
        }
        setLottoBallViewsSize(ballViewList)
        firstWinnerCompaniesTextView.text = "1등: ${lotto.firstPrizeWinnerCompanies}명"
        firstWinnerAmountTextView.text = "당첨금: ${DecimalFormat("###,###").format(lotto.firstWinnerAmount)}원"
    }
}