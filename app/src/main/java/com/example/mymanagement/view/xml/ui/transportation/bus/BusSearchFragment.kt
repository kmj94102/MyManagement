package com.example.mymanagement.view.xml.ui.transportation.bus

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mymanagement.BuildConfig
import com.example.mymanagement.R
import com.example.mymanagement.databinding.FragmentBusSearchBinding
import com.example.mymanagement.view.base.BaseViewModelFragment
import com.example.mymanagement.view.xml.ui.transportation.bus.arrival_info.BusArrivalInfoFragment
import com.example.network.model.BusStop
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BusSearchFragment :
    BaseViewModelFragment<FragmentBusSearchBinding, BusSearchViewModel>(R.layout.fragment_bus_search),
    OnMapReadyCallback {

    override val viewModel: BusSearchViewModel by viewModels()
    private lateinit var naverMap: NaverMap
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                checkPermission()
            }
        }

    private val markerList = mutableListOf<Marker>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NaverMapSdk.getInstance(requireContext()).client =
            NaverCloudPlatformClient(BuildConfig.client_id)

        with(binding) {
            fragment = this@BusSearchFragment
            vm = viewModel
            topTitle = "버스 정류소 검색"
            naverMap.onCreate(savedInstanceState)
            naverMap.getMapAsync(this@BusSearchFragment)

            editSearch.setSearchListener {
                viewModel.searchBusStopListByKeyword(it)
            }
            btnMyLocation.setOnClickListener {
                checkPermission()
            }
            btnCurrentLocation.setOnClickListener {
                viewModel.searchBusStopListByLatLng()
            }
        }

        lifecycleScope.launch {
            viewModel.busStopList.collectLatest { list ->
                clearMarkers()
                setMarkers(list)
            }
        }

    }

    private fun checkPermission() {
        val context = requireContext()
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                try {
                    val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
                    val locationResult = fusedLocation.lastLocation
                    getDeviceLocation(locationResult)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getDeviceLocation(locationResult: Task<Location>) {
        locationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val lastKnowLocation = task.result
                viewModel.searchBusStopListByLatLng(
                    latLng = LatLng(
                        lastKnowLocation.latitude,
                        lastKnowLocation.longitude
                    )
                )
            }
        }
    }

    private fun setMarkers(busStopList: List<BusStop>) {
        busStopList.getOrNull(0)?.let { item ->
            naverMap.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(item.latitude, item.longitude)
                ).animate(CameraAnimation.Fly)
            )
        }

        busStopList.forEach { item ->
            val marker = Marker(LatLng(item.latitude, item.longitude)).also {
                it.map = naverMap
                it.captionText = item.nodeNm
                it.setOnClickListener {
                    findNavController()
                        .navigate(
                            R.id.action_busSearchFragment_to_busArrivalInfo,
                            bundleOf(
                                BusArrivalInfoFragment.CityCode to item.cityCode,
                                BusArrivalInfoFragment.NodeId to item.nodeId,
                                BusArrivalInfoFragment.NodeName to item.nodeNm
                            )
                        )
                    true
                }
            }
            markerList.add(marker)
        }
    }

    private fun clearMarkers() {
        markerList.forEach {
            it.map = null
        }
    }

    override fun onStart() {
        super.onStart()
        binding.naverMap.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.naverMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.naverMap.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.naverMap.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.naverMap.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.naverMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.naverMap.onLowMemory()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.addOnCameraIdleListener {
            viewModel.cameraPositionChange(naverMap.cameraPosition.target)
        }
    }
}