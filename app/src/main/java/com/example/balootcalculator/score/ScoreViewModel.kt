package com.example.balootcalculator.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ScoreViewModel() : ViewModel() {

    private val _allScores: MutableLiveData<Stack<Scores>> by lazy {
        MutableLiveData<Stack<Scores>>()
    }
    var allScores: LiveData<Stack<Scores>> = _allScores

    fun addScore(teamOne: Int, teamTwo: Int) = _allScores.value?.push(Scores(teamOne, teamTwo))

}
