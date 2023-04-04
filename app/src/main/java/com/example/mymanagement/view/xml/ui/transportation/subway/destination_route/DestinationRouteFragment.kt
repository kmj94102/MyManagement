package com.example.mymanagement.view.xml.ui.transportation.subway.destination_route

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentDestinationRouteBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DestinationRouteFragment :
    BaseViewModelFragment<FragmentDestinationRouteBinding, DestinationRouteViewModel>(R.layout.fragment_destination_route) {

    override val viewModel: DestinationRouteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            topTitle = "지하철 목적지 경로"
            fragment = this@DestinationRouteFragment
            vm = viewModel
            startStationName = arguments?.getString(StartStationName)
            endStationName = arguments?.getString(EndStationName)
            adapter = DestinationAdapter()
            txtDay.setOnClickListener { viewModel.updateWeek(DestinationRouteViewModel.Day) }
            txtSaturday.setOnClickListener { viewModel.updateWeek(DestinationRouteViewModel.Saturday) }
            txtHoliday.setOnClickListener { viewModel.updateWeek(DestinationRouteViewModel.Holiday) }
        }
    }

    companion object {
        const val StartStationName = "startStationName"
        const val EndStationName = "endStationName"
        const val StartStationCode = "startStationCode"
        const val EndStationCode = "endStationCode"
    }
}