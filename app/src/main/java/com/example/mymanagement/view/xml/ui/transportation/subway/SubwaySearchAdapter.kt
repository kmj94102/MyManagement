package com.example.mymanagement.view.xml.ui.transportation.subway

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.databinding.ItemSubwayBinding
import com.example.mymanagement.view.xml.model.Station

class SubwaySearchAdapter(
    val startChangeListener: (Station?) -> Unit,
    val endChangeListener: (Station?) -> Unit,
    val onStationClick: (Station) -> Unit,
    val onFavoriteClick: (Station) -> Unit
): ListAdapter<Station, SubwaySearchAdapter.ViewHolder>(diffUtil) {
    private var startIndex = -1
    private var endIndex = -1

    inner class ViewHolder(val binding: ItemSubwayBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            item = currentList[absoluteAdapterPosition]
            adapter = this@SubwaySearchAdapter
            executePendingBindings()
        }
    }

    fun startChange(station: Station) {
        val index = currentList.indexOf(station)
        if (startIndex == index) return

        currentList.getOrNull(startIndex)?.isStartSelect = false
        currentList[index].isStartSelect = true
        notifyItemChanged(startIndex)
        notifyItemChanged(index)
        startIndex = index
        startChangeListener(station)

        if (startIndex != endIndex) return
        currentList.getOrNull(endIndex)?.isEndSelect = false
        notifyItemChanged(endIndex)
        endIndex = -1
        endChangeListener(null)
    }

    fun endChange(station: Station) {
        val index = currentList.indexOf(station)
        if (endIndex == index) return

        currentList.getOrNull(endIndex)?.isEndSelect = false
        currentList[index].isEndSelect = true
        notifyItemChanged(endIndex)
        notifyItemChanged(index)
        endIndex = index
        endChangeListener(station)

        if (endIndex != startIndex) return
        currentList.getOrNull(startIndex)?.isStartSelect = false
        notifyItemChanged(startIndex)
        startIndex = -1
        startChangeListener(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSubwayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Station>() {
            override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean =
                oldItem.stationId == newItem.stationId

            override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean =
                oldItem == newItem
        }
    }

}