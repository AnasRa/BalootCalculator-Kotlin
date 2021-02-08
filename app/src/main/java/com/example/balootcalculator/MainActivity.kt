package com.example.balootcalculator

import android.app.ActionBar.DISPLAY_SHOW_CUSTOM
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.balootcalculator.databinding.ActivityMainBinding
import com.example.balootcalculator.databinding.DialogLayoutBinding
import com.example.balootcalculator.databinding.LastDialogLayoutBinding


class MainActivity : AppCompatActivity(), ListAdapter.onItemListener {
    lateinit var adapter: ListAdapter
    lateinit var binding: ActivityMainBinding
    var arrowFlag = 0
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // for custom action bar
        supportActionBar?.displayOptions = DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)

        binding.count.setOnClickListener {
            count()
        }

        viewModel.allScores.observe(this) {
            binding.recycleView.layoutManager = LinearLayoutManager(this)
            adapter = ListAdapter(it, this)
            binding.recycleView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        binding.roll.setOnClickListener {
            setRoll()
        }
    }

    private fun count(){
        when {
            binding.teamOneEdit.text.isNullOrBlank() && binding.teamTwoEdit.text.isNullOrBlank() -> return
            binding.teamOneEdit.text.isNullOrBlank() -> {
                viewModel.setScoreTwo(binding.teamTwoEdit.text.toString().toInt())
            }
            binding.teamTwoEdit.text.isNullOrBlank() -> {
                viewModel.setScoreOne(binding.teamOneEdit.text.toString().toInt())
            }
            else -> {
                viewModel.setScoreOne(binding.teamOneEdit.text.toString().toInt())
                viewModel.setScoreTwo(binding.teamTwoEdit.text.toString().toInt())
            }
        }

        binding.teamOneEdit.text.clear()
        binding.teamTwoEdit.text.clear()
        viewModel.addScore()

        // For dismissing the keyboard
        val inputManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.count.windowToken, 0)

        // change roll
        setRoll()
        if (viewModel.isFinished())
            lastDialog(viewModel.scoreOne.value!!, viewModel.scoreTwo.value!!)
    }

    private fun setRoll() {
        when (arrowFlag) {
            0 -> {
                arrowFlag = 1
                binding.roll.setImageResource(R.drawable.right)
            }
            1 -> {
                arrowFlag = 2
                binding.roll.setImageResource(R.drawable.up)
            }
            2 -> {
                arrowFlag = 3
                binding.roll.setImageResource(R.drawable.left)
            }
            3 -> {
                arrowFlag = 0
                binding.roll.setImageResource(R.drawable.down)
            }
        }
    }

    private fun lastDialog(s1: Int, s2: Int): AlertDialog? {
        val dialogBinding = LastDialogLayoutBinding.inflate(LayoutInflater.from(this))
        val myQuittingDialogBox = AlertDialog.Builder(this)
        dialogBinding.teamOneFinalScore.text = s1.toString()
        dialogBinding.teamTwoFinalScore.text = s2.toString()
        return myQuittingDialogBox.setView(dialogBinding.root)
            .setPositiveButton("كرها") { dialog, _ ->
                viewModel.reset()
                dialog.dismiss()
            }
            .setNegativeButton("إلغاء") { dialog, _ -> dialog.dismiss() }.show()
    }

    private fun scoreDetails(s1: Int, s2: Int): Dialog? {
        val dialogBinding = DialogLayoutBinding.inflate(LayoutInflater.from(this))
        var myQuittingDialogBox = AlertDialog.Builder(this)
        myQuittingDialogBox = AlertDialog.Builder(this)
        dialogBinding.teamTwoScore.text = s1.toString()
        dialogBinding.teamOneScore.text = s2.toString()
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        myQuittingDialogBox.setView(dialogBinding.root) // Add action buttons
            .setPositiveButton(
                "تمام"
            ) { dialog, _ -> dialog.dismiss() }
        return myQuittingDialogBox.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.reset) {
            viewModel.reset()
        }
        if (id == R.id.del) {
            viewModel.deleteLast()
            adapter.notifyDataSetChanged()
        }
        return true
    }

    override fun onItemClicked(position: Int) {
        scoreDetails(
            viewModel.allScores.value?.get(position)?.teamOne!!,
            viewModel.allScores.value?.get(position)?.teamTwo!!
        )
    }

}


