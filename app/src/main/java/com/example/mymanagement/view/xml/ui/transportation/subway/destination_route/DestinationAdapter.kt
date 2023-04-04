package com.example.mymanagement.view.xml.ui.transportation.subway.destination_route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.databinding.ItemSubwayEndPointBinding
import com.example.mymanagement.databinding.ItemSubwayRouteBinding
import com.example.mymanagement.databinding.ItemSubwayTransferBinding
import com.example.mymanagement.view.xml.model.SubwayRouteRecyclerItem

class DestinationAdapter : ListAdapter<SubwayRouteRecyclerItem, RecyclerView.ViewHolder>(diffUtil) {

    inner class EndPointViewHolder(val binding: ItemSubwayEndPointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.item =
                (currentList[absoluteAdapterPosition] as? SubwayRouteRecyclerItem.SubwayEndPoint)
                    ?: return
        }
    }

    inner class MidPointViewMolder(val binding: ItemSubwayRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.item =
                (currentList[absoluteAdapterPosition] as? SubwayRouteRecyclerItem.SubwayMidPoint)
                    ?: return
        }
    }

    inner class TransferViewHolder(val binding: ItemSubwayTransferBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SubwayRouteRecyclerItem.SubwayEndPoint -> TypeEndPoint
            is SubwayRouteRecyclerItem.SubwayMidPoint -> TypeMidPoint
            else -> TypeTransfer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TypeEndPoint -> {
                EndPointViewHolder(
                    ItemSubwayEndPointBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TypeMidPoint -> {
                MidPointViewMolder(
                    ItemSubwayRouteBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                TransferViewHolder(
                    ItemSubwayTransferBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EndPointViewHolder -> {
                holder.bind()
            }
            is MidPointViewMolder -> {
                holder.bind()
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SubwayRouteRecyclerItem>() {
            override fun areItemsTheSame(
                oldItem: SubwayRouteRecyclerItem,
                newItem: SubwayRouteRecyclerItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SubwayRouteRecyclerItem,
                newItem: SubwayRouteRecyclerItem
            ): Boolean = oldItem == newItem
        }

        const val TypeEndPoint = 0
        const val TypeMidPoint = 1
        const val TypeTransfer = 2
    }

}