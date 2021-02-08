package com.example.balootcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.balootcalculator.score.Scores

class MainViewModel : ViewModel() {

    private val _scoreOne: MutableLiveData<Int> = MutableLiveData()
    fun setScoreOne(one: Int) {
        _scoreOne.value = one + scoreOne.value!!
    }

    var scoreOne: LiveData<Int> = _scoreOne
    private val _scoreTwo: MutableLiveData<Int> = MutableLiveData()
    fun setScoreTwo(two: Int) {
        _scoreTwo.value = two + scoreTwo.value!!
    }

    var scoreTwo: LiveData<Int> = _scoreTwo

    private val _allScores: MutableLiveData<ArrayList<Scores>> = MutableLiveData()
    var allScores: LiveData<ArrayList<Scores>> = _allScores
    fun addScore() {
        _allScores.value?.add(Scores(_scoreOne.value!!, _scoreTwo.value!!))
        _allScores.value = _allScores.value
        allScores = _allScores
    }

    fun isFinished(): Boolean {
        // makes sure that if both team over 152 the game doesn't finish
        if (scoreOne.value!! > 152 && scoreTwo.value!! > 152) {
            // checks if the both teams over 152 but one of them won
            return scoreOne.value!! > scoreTwo.value!!
                    ||
                    scoreTwo.value!! > scoreOne.value!!
        }
        // checks if any of the two teams wins
        return scoreOne.value!! > 152 || scoreTwo.value!! > 152
    }

    fun deleteLast() {
        if (!_allScores.value.isNullOrEmpty())
            _allScores.value?.removeLast()
    }

    fun reset() {
        startNewGame()
    }

    private fun startNewGame() {
        _allScores.value = ArrayList()
        _scoreOne.value = 0
        _scoreTwo.value = 0
    }

    init {
        startNewGame()
    }
}