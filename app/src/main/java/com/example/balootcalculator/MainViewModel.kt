package com.example.balootcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.balootcalculator.score.Scores

class MainViewModel : ViewModel() {

    private val _scoreOne: MutableLiveData<Int> = MutableLiveData<Int>()
    fun setScoreOne(one: Int) {
        _scoreOne.value = one + scoreOne.value!!
    }

    var scoreOne: LiveData<Int> = _scoreOne
    private val _scoreTwo: MutableLiveData<Int> = MutableLiveData<Int>()
    fun setScoreTwo(two: Int) {
        _scoreTwo.value = two + scoreTwo.value!!
    }

    var scoreTwo: LiveData<Int> = _scoreTwo

    private val _allScores: MutableLiveData<ArrayList<Scores>> = MutableLiveData<ArrayList<Scores>>()
    var allScores: LiveData<ArrayList<Scores>> = _allScores
    fun addScore() {
        _allScores.value?.add(Scores(_scoreOne.value!!, _scoreTwo.value!!))
        _allScores.value = _allScores.value
    }

    init {
        _allScores.value = ArrayList()
        _scoreOne.value = 0
        _scoreTwo.value = 0
    }
}