package com.jincal.angellottogenerator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jincal.angellottogenerator.R
import com.jincal.angellottogenerator.ResultRecyclerViewAdapter
import com.jincal.angellottogenerator.objects.AdManager
import com.jincal.angellottogenerator.objects.SelectedBallHolder
import com.jincal.valorantstory.RecyclerViewDecoration
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        AdManager.loadBannerAd(rAdView)

        val ballCount = SelectedBallHolder.selectedBallCounts
        val resultCount = SelectedBallHolder.resultCount

        var resultBallSet = mutableSetOf<Int>()
        var resultBallSortedList = listOf<Int>()
        var alreadyDoneList = mutableListOf<List<Int>>()

        var resultCombinationList = mutableListOf<List<Int>>()

        if (ballCount in 1..5) {
            for (result in 1..resultCount) {
                val leftBallSet = mutableSetOf<Int>()
                var leftBallSortedList = listOf<Int>()
                while (leftBallSet.size < 6 - ballCount) {
                    val randomNumber = Random().nextInt(45) + 1
                    if (!SelectedBallHolder.selectedBallSet.contains(randomNumber)) leftBallSet.add(
                        randomNumber
                    )
                    if (leftBallSet.size == 6 - ballCount) {
                        if (alreadyDoneList.contains(
                                leftBallSet.toList().sorted()
                            )
                        ) leftBallSet.clear()
                        else {
                            leftBallSortedList = leftBallSet.toList().sorted()
                            alreadyDoneList.add(leftBallSet.toList().sorted())
                            break
                        }
                    }
                }
                val resultCombination = SelectedBallHolder.selectedBallSet.toMutableList()
                resultCombination.addAll(leftBallSortedList)
                resultCombination.sort()
                resultCombinationList.add(resultCombination.toList())
            }
        } else {
            for (result in 1..resultCount) {
                val sixNumbers = mutableSetOf<Int>()
                while (sixNumbers.size < 6) {
                    val randomIndex = Random().nextInt(ballCount)
                    sixNumbers.add(SelectedBallHolder.selectedBallList[randomIndex])

                }
                resultCombinationList.add(sixNumbers.toList().sorted())
            }
        }

        resultRecyclerView.adapter =
            ResultRecyclerViewAdapter(
                resultCombinationList
            )
        resultRecyclerView.layoutManager = LinearLayoutManager(this)
        resultRecyclerView.addItemDecoration(RecyclerViewDecoration(3, 4))

        resultNameText.text = "선택한 번호: ${SelectedBallHolder.selectedBallList.joinToString()}"
    }
}