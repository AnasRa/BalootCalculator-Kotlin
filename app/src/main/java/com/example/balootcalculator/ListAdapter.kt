package com.example.balootcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.balootcalculator.databinding.ListItemBinding
import com.example.balootcalculator.score.Scores
import java.util.*

class ListAdapter(private val dataSet: ArrayList<Scores>,var mOnItemListener: onItemListener) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MyViewHolder(binding,mOnItemListener)
    }


    class MyViewHolder(binding: ListItemBinding,var onItemListener: onItemListener) : RecyclerView.ViewHolder(binding.root) , View.OnClickListener{
        val teamOne: TextView = binding.teamOneText
        val teamTwo: TextView = binding.teamTwoText
        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            onItemListener.onItemClicked(adapterPosition)
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.teamOne.text = dataSet[position].teamOne.toString()
        holder.teamTwo.text = dataSet[position].teamTwo.toString()
    }

    override fun getItemCount(): Int = dataSet.size

    public interface onItemListener{
        fun onItemClicked(position: Int)
    }

}
