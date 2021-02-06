package com.example.balootcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.balootcalculator.score.ListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        count.setOnClickListener {
            when {
                teamOneEdit.text.isNullOrBlank() && teamTwoEdit.text.isNullOrBlank() -> return@setOnClickListener
                teamOneEdit.text.isNullOrBlank() -> {
                    viewModel.setScoreTwo(teamTwoEdit.text.toString().toInt())
                }
                teamTwoEdit.text.isNullOrBlank() -> {
                    viewModel.setScoreOne(teamOneEdit.text.toString().toInt())
                }
                else -> {
                    viewModel.setScoreOne(teamOneEdit.text.toString().toInt())
                    viewModel.setScoreTwo(teamTwoEdit.text.toString().toInt())
                }
            }
            teamOneEdit.text.clear()
            teamTwoEdit.text.clear()
            viewModel.addScore()
        }
        viewModel.allScores.observe(this, {
            recycleView.layoutManager = LinearLayoutManager(this)
            recycleView.adapter = ListAdapter(it)
        })
    }
}
