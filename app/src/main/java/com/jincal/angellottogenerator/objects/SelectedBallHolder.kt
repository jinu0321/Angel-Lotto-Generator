package com.jincal.angellottogenerator.objects

object SelectedBallHolder {
    var selectedBallSet = mutableSetOf<Int>()

    val selectedBallList: List<Int>
        get() = selectedBallSet.toList().sorted()

    val selectedBallCounts: Int
        get() = selectedBallSet.size

    var resultCount = 0
}