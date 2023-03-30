package com.example.mymanagement.view.xml.ui.transportation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentTransportationBinding
import com.example.mymanagement.util.GridSpacingItemDecoration
import com.example.mymanagement.util.dpToPx
import com.example.mymanagement.view.base.BaseViewModelFragment
import com.example.mymanagement.view.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransportationFragment :
    BaseViewModelFragment<FragmentTransportationBinding, TransportationViewModel2>(R.layout.fragment_transportation) {

    override val viewModel: TransportationViewModel2 by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = viewModel
            adatper = TransportationAdapter()
            imgSetting.setOnClickListener {

            }
            layoutSubway.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.subwayScheduleFragment,
                    null
                )
            )
            layoutBus.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.busSearchFragment,
                    null
                )
            )
            recyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = 2,
                    verticalSpacing = dpToPx(context, 10),
                    horizontalSpacing = dpToPx(context, 16),
                    includeEdge = false
                )
            )
        }
    }
}