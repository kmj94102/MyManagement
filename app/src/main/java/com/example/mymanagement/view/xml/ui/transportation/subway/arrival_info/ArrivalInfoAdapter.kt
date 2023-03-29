package com.example.mymanagement.view.xml.ui.transportation.subway.arrival_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.databinding.ItemArrivalInfoBinding

class ArrivalInfoAdapter : ListAdapter<String, ArrivalInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemArrivalInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val regex = Regex("(.*행 )(.*)")
            val result = regex.matchEntire(currentList[absoluteAdapterPosition])?.destructured
            result?.let { (destinationName, arrTime) ->
                this.destinationName = destinationName
                this.arrTime = arrTime
            } ?: {
                destinationName = currentList[absoluteAdapterPosition]
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemArrivalInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}