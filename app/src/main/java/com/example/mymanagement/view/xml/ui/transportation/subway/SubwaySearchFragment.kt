package com.example.mymanagement.view.xml.ui.transportation.subway

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentSubwaySearchBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import com.example.mymanagement.view.xml.ui.transportation.subway.arrival_info.BottomSheetArrivalInfo
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
                    BottomSheetArrivalInfo(it.stationName).show(requireActivity().supportFragmentManager, null)
                }
            )
            editSearch.setSearchListener {
                viewModel.fetchStationList(it)
            }
        }
    }
}