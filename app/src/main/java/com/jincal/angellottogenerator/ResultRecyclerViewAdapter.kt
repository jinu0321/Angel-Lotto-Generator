package com.jincal.angellottogenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview_result.view.*

class ResultRecyclerViewAdapter(val resultList: MutableList<List<Int>>): RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultRecyclerViewHolder>() {

    class ResultRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_result, parent, false)
        return ResultRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: ResultRecyclerViewHolder, position: Int) {
        holder.itemView.resultItemTextView.text = resultList[position].joinToString(", ")
    }

}