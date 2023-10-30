package com.tta.fitnessapplication.view.activity.map

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityMapsBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun getDataBinding(): ActivityMapsBinding {
        return ActivityMapsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.getDataMap(
            "21.010112441500187, 105.80591323169317", "1500", "gym",
            R.string.map_api_key.toString()
        )
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.mapList.observe(this) {
            Log.e("ssss", it.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.let { googleMap ->
            googleMap.setOnInfoWindowClickListener { marker ->
                val location = "${marker.position.latitude},${marker.position.longitude}"
                val gmmIntentUri = Uri.parse("geo:$location?q=$location&z=zoom_level")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }
    }

    override fun addEvent() {
        super.addEvent()

    }
}
