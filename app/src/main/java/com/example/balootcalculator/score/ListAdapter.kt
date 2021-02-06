package com.example.balootcalculator.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.balootcalculator.R
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class ListAdapter(private val dataSet: ArrayList<Scores>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return MyViewHolder(view)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamOne: TextView = itemView.teamOneText
        val teamTwo: TextView = itemView.teamTwoText
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.teamOne.text = dataSet[position].teamOne.toString()
        holder.teamTwo.text = dataSet[position].teamTwo.toString()
    }

    override fun getItemCount(): Int = dataSet.size

}