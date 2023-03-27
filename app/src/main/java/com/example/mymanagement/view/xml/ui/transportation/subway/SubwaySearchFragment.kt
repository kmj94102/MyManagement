package com.example.mymanagement.view.xml.ui.transportation.subway

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentSubwaySearchBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubwaySearchFragment :
    BaseViewModelFragment<FragmentSubwaySearchBinding, SubwaySearchViewModel2>(R.layout.fragment_subway_search) {

    override val viewModel: SubwaySearchViewModel2 by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            topTitle = "지하철 검색"
            vm = this@SubwaySearchFragment.viewModel
            fragment = this@SubwaySearchFragment

            adapter = SubwaySearchAdapter(
                startChangeListener = {
                    viewModel.startChangeListener(it)
                },
                endChangeListener = {
                    viewModel.endChangeListener(it)
                }
            )
            editSearch.setSearchListener {
                viewModel.fetchStationList(it)
            }
        }
    }
}