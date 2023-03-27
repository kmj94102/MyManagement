package com.example.mymanagement.view.xml.ui.transportation

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymanagement.R
import com.example.mymanagement.database.entity.Favorite
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.databinding.ItemFavoriteTransportationBinding

class TransportationAdapter : ListAdapter<Favorite, TransportationAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemFavoriteTransportationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            currentList.getOrNull(absoluteAdapterPosition)?.let {
                favorite = it
                imgIcon.setImageResource(getIconByType(it.type))
                viewBg.setBackgroundColor(getBackgroundByType(it.type, viewBg.context))
                getTextByType(type = it.type, name = it.name, linearLayout = linear)
            }
            binding.executePendingBindings()
        }
    }

    private fun getIconByType(type: String): Int = when (type) {
        FavoriteEntity.TypeBusStop -> {
            R.drawable.ic_busstop
        }
        FavoriteEntity.TypeSubway, FavoriteEntity.TypeSubwayDestination -> {
            R.drawable.ic_subway
        }
        else -> {
            R.drawable.ic_bus
        }
    }

    private fun getBackgroundByType(type: String, context: Context): Int = when (type) {
        FavoriteEntity.TypeBusStop, FavoriteEntity.TypeBus -> ContextCompat.getColor(
            context,
            R.color.blue
        )
        else -> ContextCompat.getColor(context, R.color.green)
    }

    private fun getTextByType(type: String, name: String, linearLayout: LinearLayout) {
        when (type) {
            FavoriteEntity.TypeBusStop, FavoriteEntity.TypeSubway -> {
                linearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = name
                        it.textSize = 16f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.typeface = Typeface.DEFAULT_BOLD
                    }
                )
            }
            FavoriteEntity.TypeBus -> {
                val items = name.split(FavoriteEntity.Separator)
                linearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = items.getOrNull(0)
                        it.textSize = 16f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.typeface = Typeface.DEFAULT_BOLD
                    }
                )
                linearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = items.getOrNull(1)
                        it.textSize = 12f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                    }
                )
            }
            else -> {
                val items = name.split(FavoriteEntity.Separator)
                val startLinearLayout = LinearLayout(linearLayout.context)
                val endLinearLayout = LinearLayout(linearLayout.context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ).also {
                    it.setMargins(0, 0, 5, 0)
                }

                startLinearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = "출발"
                        it.textSize = 12f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.layoutParams = params
                        it.ellipsize = TextUtils.TruncateAt.END
                    }
                )
                startLinearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = items.getOrNull(0)
                        it.textSize = 16f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.typeface = Typeface.DEFAULT_BOLD
                    }
                )
                startLinearLayout.gravity = Gravity.CENTER_VERTICAL

                endLinearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = "도착"
                        it.textSize = 12f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.gravity = Gravity.CENTER
                        it.layoutParams = params
                    }
                )
                endLinearLayout.addView(
                    TextView(linearLayout.context).also {
                        it.text = items.getOrNull(1)
                        it.textSize = 16f
                        it.setTextColor(ContextCompat.getColor(it.context, R.color.black))
                        it.typeface = Typeface.DEFAULT_BOLD
                    }
                )
                endLinearLayout.gravity = Gravity.CENTER_VERTICAL

                linearLayout.addView(startLinearLayout)
                linearLayout.addView(endLinearLayout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFavoriteTransportationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                oldItem == newItem
        }
    }
}