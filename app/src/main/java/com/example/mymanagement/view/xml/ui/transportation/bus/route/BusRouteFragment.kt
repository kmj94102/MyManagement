package com.example.mymanagement.view.xml.ui.transportation.bus.route

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentBusRouteBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusRouteFragment :
    BaseViewModelFragment<FragmentBusRouteBinding, BusRouteViewModel>(R.layout.fragment_bus_route) {
    override val viewModel: BusRouteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentStation = arguments?.getString(NodeName) ?: ""
        val cityCode = arguments?.getInt(CityCode) ?: 0
        val routeId = arguments?.getString(RouteId) ?: ""
        val busNumber = arguments?.getString(BusNumber) ?: ""
        viewModel.setInfo(cityCode, routeId, busNumber)

        with(binding) {
            fragment = this@BusRouteFragment
            title = busNumber
            adapter = BusRouteAdapter(currentStation = currentStation) { nodeName, nodeId ->
                viewModel.toggleBusStopFavoriteStatus(nodeName, nodeId)
            }
            vm = viewModel
        }
    }

    companion object {
        const val NodeName = "nodeName"
        const val CityCode = "cityCode"
        const val RouteId = "routeId"
        const val BusNumber = "busNumber"
    }
}