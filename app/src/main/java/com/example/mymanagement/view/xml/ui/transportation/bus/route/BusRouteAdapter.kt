package com.example.mymanagement.view.xml.ui.transportation.bus.route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.R
import com.example.mymanagement.databinding.ItemBusStopBinding
import com.example.network.model.BusStopRouteItem

class BusRouteAdapter(val currentStation: String, val onItemClick: (String, String) -> Unit) :
    ListAdapter<BusStopRouteItem, BusRouteAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemBusStopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val item = currentList[absoluteAdapterPosition]
            busStop = item
            isCurrentStation = item.nodeName == currentStation
            isLastStation = currentList.size == absoluteAdapterPosition - 1

            cardCircle.setCardBackgroundColor(
                cardCircle.context.getColor(
                    circleColor(
                        position = absoluteAdapterPosition,
                        size = currentList.size,
                        isCurrentStation = item.nodeName == currentStation
                    )
                )
            )

            imgFavorite.setOnClickListener {
                onItemClick(item.nodeName, item.nodeId)
            }
        }
    }

    private fun circleColor(position: Int, size: Int, isCurrentStation: Boolean) =
        if (position == 0 || position == size - 1) {
            R.color.gray
        } else if (isCurrentStation) {
            R.color.green
        } else {
            R.color.white
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBusStopBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BusStopRouteItem>() {
            override fun areItemsTheSame(
                oldItem: BusStopRouteItem,
                newItem: BusStopRouteItem
            ): Boolean = oldItem.nodeId == newItem.nodeId

            override fun areContentsTheSame(
                oldItem: BusStopRouteItem,
                newItem: BusStopRouteItem
            ): Boolean = oldItem == newItem

        }
    }
}