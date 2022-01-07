package com.jgiovannysn.movies.ui.main.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.jgiovannysn.movies.MoviesApplication
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.base.BaseFragment
import com.jgiovannysn.movies.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_maps.*

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
open class MapsFragment: BaseFragment(), OnMapReadyCallback {

    private var mainViewModel: MainViewModel? = null

    private var mMap: GoogleMap? = null
    var mCurrLocationMarker: Marker? = null
    private var isFirstTime = true

    companion object {
        @JvmStatic
        fun newInstance() = MapsFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_maps

    override fun init() {
        MoviesApplication.appComponent.inject(this)

        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (map as MapView).onCreate(savedInstanceState)
        (map as MapView).getMapAsync(this)
    }

    override fun initViewModel() {
        mainViewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }
    }

    override fun listeners() {

    }

    override fun observers() {

        mainViewModel?.userLocation?.observe(this, {user ->
            mMap?.let { map ->
                if(isFirstTime) {
                    mainViewModel?.setCurrentLocationMarker(user, map)
                    isFirstTime = false
                }
            }
        })

        mainViewModel?.userLocationList?.observe(this, {
            mMap?.let { map ->
                map.clear()
                mainViewModel?.userLocation?.value?.let {user ->
                    mainViewModel?.setCurrentLocationMarker(user, map)
                }

                for (userLocation in it) {
                    mainViewModel?.setLocationMarker(userLocation, map)
                }

            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                mMap!!.isMyLocationEnabled = true
            }
        }
        else {
            mMap!!.isMyLocationEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        (map as MapView).onResume()
    }

    override fun onStart() {
        super.onStart()
        (map as MapView).onStart()
    }

    override fun onStop() {
        super.onStop()
        (map as MapView).onStop()
    }

    override fun onPause() {
        super.onPause()
        (map as MapView).onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (map as MapView).onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        (map as MapView).onLowMemory()
    }
}