package com.example.mymanagement.view.xml.ui.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.databinding.ItemSubwayLineBinding
import com.example.mymanagement.util.SubwayLine

class LineChipAdapter(val onClickListener: ((Int) -> Unit)? = null) :
    ListAdapter<SubwayLine, LineChipAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemSubwayLineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            info = currentList[absoluteAdapterPosition]
            onClickListener?.let { listener ->
                binding.root.setOnClickListener {
                    listener.invoke(absoluteAdapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSubwayLineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SubwayLine>() {
            override fun areItemsTheSame(oldItem: SubwayLine, newItem: SubwayLine): Boolean =
                oldItem.code == newItem.code

            override fun areContentsTheSame(oldItem: SubwayLine, newItem: SubwayLine): Boolean =
                oldItem == newItem

        }
    }
}