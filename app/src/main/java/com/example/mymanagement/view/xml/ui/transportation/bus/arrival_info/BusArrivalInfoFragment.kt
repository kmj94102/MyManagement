package com.example.mymanagement.view.xml.ui.transportation.bus.arrival_info

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentBusArrivalInfoBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import com.example.mymanagement.view.xml.ui.transportation.bus.route.BusRouteFragment
import com.example.network.model.BusEstimatedArrivalInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BusArrivalInfoFragment :
    BaseViewModelFragment<FragmentBusArrivalInfoBinding, BusArrivalInfoViewModel>(R.layout.fragment_bus_arrival_info) {

    override val viewModel: BusArrivalInfoViewModel by viewModels()
    private var nodeId = ""
    private var nodeName = ""
    private var cityCode = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nodeId = arguments?.getString(NodeId) ?: ""
        nodeName = arguments?.getString(NodeName) ?: ""
        cityCode = arguments?.getInt(CityCode) ?: 0

        viewModel.setCityCodeAndNodeId(cityCode, nodeId)

        with(binding) {
            fragment = this@BusArrivalInfoFragment
            title = nodeName
            adapter = BusArrivalInfoAdapter(
                onFavoriteClick = {
                    viewModel.toggleBusFavoriteStatus(it)
                },
                onRouteClick = {
                    goToRoute(it)
                }
            )
        }

        lifecycleScope.launch {
            viewModel.arrivalInfoList.collect {
                if (it.size == 1) {
                    singleInfoUiSetting(it[0])
                }
            }
        }
    }

    private fun singleInfoUiSetting(info: BusEstimatedArrivalInfo) = with(binding) {
        txtBusNumber.text = info.busNumber
        imgFavorite.setImageResource(
            if (info.isFavorite) R.drawable.ic_star else R.drawable.ic_star_empty
        )
        imgFavorite.setOnClickListener {
            viewModel.toggleBusFavoriteStatus(info)
        }
        btnRoute.setOnClickListener { goToRoute(info) }

        if (info.arrInfo.size == 1) {
            txtFirstArrivalInfo.text = info.arrInfo[0]
            txtSecondArrivalInfo.isVisible = false
        } else if (info.arrInfo.size >= 2) {
            txtFirstArrivalInfo.text = info.arrInfo[0]
            txtSecondArrivalInfo.text = info.arrInfo[1]
            txtSecondArrivalInfo.isVisible = true
        }
    }

    private fun goToRoute(info: BusEstimatedArrivalInfo) {
        findNavController().navigate(
            R.id.action_busArrivalInfo_to_busRoute,
            bundleOf(
                BusRouteFragment.NodeName to info.nodeName,
                BusRouteFragment.CityCode to cityCode,
                BusRouteFragment.RouteId to info.routeId,
                BusRouteFragment.BusNumber to info.busNumber
            )
        )
    }

    companion object {
        const val NodeName = "nodeNm"
        const val CityCode = "cityCode"
        const val NodeId = "nodeId"
    }

}