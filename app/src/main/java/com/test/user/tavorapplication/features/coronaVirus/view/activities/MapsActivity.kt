package com.test.user.tavorapplication.features.coronaVirus.view.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.test.user.tavorapplication.R
import com.test.user.tavorapplication.features.coronaVirus.viewModel.CoronaVirusByCountryViewState
import com.test.user.tavorapplication.features.coronaVirus.viewModel.CoronaVirusViewModel

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mCoronaVirusViewModel : CoronaVirusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initView()
    }

    private fun initView() {
        mCoronaVirusViewModel = CoronaVirusViewModel()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(32.109333, 34.855499)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.setMinZoomPreference(8.0f)


        mCoronaVirusViewModel.makeUserToSupplier("spain").observe(this, Observer<CoronaVirusByCountryViewState> {
            if (it is CoronaVirusByCountryViewState.Error) {
               // hideProgressBar()
                Log.d("asdfsadf","sdfasdf")
            }
            if (it is CoronaVirusByCountryViewState.Success) {
               // hideProgressBar()
                Log.d("asdfsadf","sdfasdf")
                val response = it.response
                for ( item in response){
                    val lat = item.lat
                    val lon = item.lon
                    val case = LatLng(lat!!, lon!!)
                    mMap.addMarker(MarkerOptions().position(case).title("Corona virus"))
                }
            }
            if(it is CoronaVirusByCountryViewState.Loading){
              //  showProgressBar()
                Log.d("asdfsadf","sdfasdf")
            }
        })
    }
}
