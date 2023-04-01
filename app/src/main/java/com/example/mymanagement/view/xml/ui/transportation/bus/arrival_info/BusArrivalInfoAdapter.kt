package com.example.mymanagement.view.xml.ui.transportation.bus.arrival_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.databinding.ItemBusArrivalInfoBinding
import com.example.network.model.BusEstimatedArrivalInfo

class BusArrivalInfoAdapter(
    val onFavoriteClick: (BusEstimatedArrivalInfo) -> Unit,
    val onRouteClick: (BusEstimatedArrivalInfo) -> Unit
) : ListAdapter<BusEstimatedArrivalInfo, BusArrivalInfoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemBusArrivalInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val arrivalInfo = currentList[absoluteAdapterPosition]
            info = arrivalInfo
            arrivalText = arrivalInfo.arrInfo.reduce { acc, s ->
                "$acc\n$s"
            }
            imgFavorite.setOnClickListener {
                onFavoriteClick(arrivalInfo)
            }
            imgRoute.setOnClickListener {
                onRouteClick(arrivalInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBusArrivalInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BusEstimatedArrivalInfo>() {
            override fun areItemsTheSame(oldItem: BusEstimatedArrivalInfo, newItem: BusEstimatedArrivalInfo): Boolean =
                oldItem.nodeId == newItem.nodeId

            override fun areContentsTheSame(oldItem: BusEstimatedArrivalInfo, newItem: BusEstimatedArrivalInfo): Boolean =
                oldItem == newItem
        }
    }

}