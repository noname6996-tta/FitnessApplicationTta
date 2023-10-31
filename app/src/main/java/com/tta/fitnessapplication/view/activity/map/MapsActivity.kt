package com.tta.fitnessapplication.view.activity.map

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityMapsBinding
import com.tta.fitnessapplication.view.base.BaseActivity
import kotlin.math.ln

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private val PERMISSION_REQUEST_CODE = 123
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var lat : Double = 0.0
    private var lng : Double = 0.0
    private var raduis = "2"

    override fun getDataBinding(): ActivityMapsBinding {
        return ActivityMapsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.topAppBar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this@MapsActivity.finish()
        }
        binding.edtRadius.setText(raduis)
        binding.button.setOnClickListener {
            var range = binding.edtRadius.text.trim().toString()
            if (range.isNullOrEmpty()){
                mainViewModel.getDataMap(lat, lng, raduis)
            } else {
                mainViewModel.getDataMap(lat, lng, range)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, enable user location and set map markers
                enableUserLocation()
                setMapMarkers()
            } else {
                // Permission denied, display a message or take appropriate action
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val myLocation = LatLng(location.latitude, location.longitude)
                    lat = location.latitude
                    lng = location.longitude
                    mainViewModel.getDataMap(location.latitude, location.longitude, raduis)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12f))
                }
            }
        }
    }

    private fun setMapMarkers() {
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

    override fun addObservers() {
        super.addObservers()
        mainViewModel.mapList.observe(this) {
            googleMap.clear()
            for (item in it.body()!!.data) {
                Log.e("ssss", item.toString())
                val location = LatLng(item.lat, item.lng)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(item.name)
                )
            }
        }
        mainViewModel.error.observe(this){
            Log.e("ssss", it.toString())
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Check if the app has the required location permission
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, enable user location and set map markers
            enableUserLocation()
            checkGpsStatus()
            setMapMarkers()
        }
    }

    private fun checkGpsStatus() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS is already enabled
            Toast.makeText(this, "GPS is already enabled.", Toast.LENGTH_SHORT).show()
        } else {
            // GPS is disabled, prompt the user to enable it
            val enableGpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(enableGpsIntent, 5)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // User enabled GPS
                Toast.makeText(this, "GPS is enabled.", Toast.LENGTH_SHORT).show()
            } else {
                // User did not enable GPS
                Toast.makeText(this, "GPS is not enabled.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
