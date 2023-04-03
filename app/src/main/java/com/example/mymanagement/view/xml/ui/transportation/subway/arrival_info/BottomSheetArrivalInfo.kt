package com.example.mymanagement.view.xml.ui.transportation.subway.arrival_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mymanagement.R
import com.example.mymanagement.databinding.BottomSheetSubwayArrivalInfoBinding
import com.example.mymanagement.view.base.BaseBottomSheet
import com.example.mymanagement.view.xml.ui.custom.LineChipAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetArrivalInfo(
    private val stationName: String
) : BaseBottomSheet<BottomSheetSubwayArrivalInfoBinding>(R.layout.bottom_sheet_subway_arrival_info) {

    private val viewModel: ArrivalInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            upLineAdapter = ArrivalInfoAdapter()
            downLineAdapter = ArrivalInfoAdapter()
            lineAdapter = LineChipAdapter(
                onClickListener = {
                    viewModel.onChipSelect(it)
                }
            )
            currentStation = stationName
            vm = viewModel
        }

        viewModel.fetchArrivals(stationName = stationName)
    }
}