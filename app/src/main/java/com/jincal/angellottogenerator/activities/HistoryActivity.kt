package com.jincal.angellottogenerator.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.jincal.angellottogenerator.R
import kotlinx.android.synthetic.main.activity_history.*
import com.jincal.angellottogenerator.functions.getLatestEpisode
import com.jincal.angellottogenerator.objects.ViewController
import org.jetbrains.anko.startActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        var selectedEpisode =
            getLatestEpisode()
        lottoHistoryEpisodeEditText.text = Editable.Factory().newEditable("$selectedEpisode")

        val lottoHistoryBallList = listOf(
            lottoHistoryLottoBall1,
            lottoHistoryLottoBall2,
            lottoHistoryLottoBall3,
            lottoHistoryLottoBall4,
            lottoHistoryLottoBall5,
            lottoHistoryLottoBall6,
            lottoHistoryLottoBallBonus
        )
        ViewController.setLottoBallViewsSize(lottoHistoryBallList)

        fun setLottoLayout(episode: Int) {
            ViewController.setLottoLayout(
                episode,
                lottoHistoryLottoDate,
                lottoHistoryLottoEpisode,
                lottoHistoryBallList,
                lottoHistoryFirstWinnerCompanies,
                lottoHistoryFirstWinnerAmount
            )
        }
        setLottoLayout(selectedEpisode)

        lottoHistoryPreviousEpisodeButton.setOnClickListener {
            if (selectedEpisode > 1) {
                selectedEpisode--
                setLottoLayout(selectedEpisode)
                lottoHistoryEpisodeEditText.text =
                    Editable.Factory().newEditable("$selectedEpisode")
                lottoHistoryEpisodeEditText.hint = "$selectedEpisode"
            }
        }
        lottoHistoryNextEpisodeButton.setOnClickListener {
            if (selectedEpisode < getLatestEpisode()) {
                selectedEpisode++
                setLottoLayout(selectedEpisode)
                lottoHistoryEpisodeEditText.text =
                    Editable.Factory().newEditable("$selectedEpisode")
                lottoHistoryEpisodeEditText.hint = "$selectedEpisode"
            }
        }
        lottoHistoryEpisodeEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val episode = try {
                    s.toString().toInt()
                } catch (e: NumberFormatException) {
                    getLatestEpisode()
                }
                selectedEpisode =
                    if (episode in 1..getLatestEpisode()) episode else getLatestEpisode()
                lottoHistoryEpisodeEditText.hint = "$selectedEpisode"
                setLottoLayout(selectedEpisode)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        lottoHistorySelectManualCheckTextView.setOnClickListener {
            startActivity<HistoryManualCheckActivity>()
        }
    }
}
