package com.example.mymanagement.view.xml.ui.transportation.subway

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentSubwaySearchBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import com.example.mymanagement.view.xml.ui.transportation.subway.arrival_info.BottomSheetArrivalInfo
import com.example.mymanagement.view.xml.ui.transportation.subway.destination_route.DestinationRouteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubwaySearchFragment :
    BaseViewModelFragment<FragmentSubwaySearchBinding, SubwaySearchViewModel2>(R.layout.fragment_subway_search) {

    override val viewModel: SubwaySearchViewModel2 by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            topTitle = "지하철 검색"
            vm = this@SubwaySearchFragment.viewModel
            fragment = this@SubwaySearchFragment

            adapter = SubwaySearchAdapter(
                startChangeListener = {
                    viewModel.startChangeListener(it)
                },
                endChangeListener = {
                    viewModel.endChangeListener(it)
                },
                onFavoriteClick = {
                    viewModel.toggleFavorite(it)
                },
                onStationClick = {
                    BottomSheetArrivalInfo(it.stationName).show(
                        requireActivity().supportFragmentManager,
                        null
                    )
                }
            )
            editSearch.setSearchListener {
                viewModel.fetchStationList(it)
            }
            viewNext.setOnClickListener {
                val startStation = viewModel.startStation.value
                val endStation = viewModel.endStation.value
                if (startStation == null || endStation == null) return@setOnClickListener
                findNavController().navigate(
                    R.id.action_subwayScheduleFragment_to_destinationRouteFragment,
                    bundleOf(
                        DestinationRouteFragment.StartStationName to startStation.stationName,
                        DestinationRouteFragment.StartStationCode to startStation.stationCode,
                        DestinationRouteFragment.EndStationName to endStation.stationName,
                        DestinationRouteFragment.EndStationCode to endStation.stationCode,
                    )
                )
            }
        }
    }
}