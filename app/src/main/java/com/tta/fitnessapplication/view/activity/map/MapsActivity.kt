package com.tta.fitnessapplication.view.activity.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.GpsStatusListener
import com.tta.fitnessapplication.data.utils.TurnOnGps
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.databinding.ActivityMapsBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var raduis = 1
    private var location = "gym"
    private var locationType = 0
    private val pinList = LatLngBounds.builder()

    override fun getDataBinding(): ActivityMapsBinding {
        return ActivityMapsBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.button.setOnClickListener {
            showLoading()
            mainViewModel.getDataMap(lat, lng, "$raduis")
        }
    }

    override fun initView() {
        super.initView()
        val gpsStatusListener = GpsStatusListener(this)
        val turnOnGps = TurnOnGps(this)
        var isGpsStatusChanged: Boolean? = null
        gpsStatusListener.observe(this) { isGpsOn ->

            if (isGpsStatusChanged == null) {

                if (!isGpsOn) {
                    //turn on the gps
                    turnOnGps.startGps(resultLauncher)
                }
                isGpsStatusChanged = isGpsOn

            } else {
                if (isGpsStatusChanged != isGpsOn) {
                    if (!isGpsOn) {
                        //turn on gps
                        turnOnGps.startGps(resultLauncher)
                    }
                    isGpsStatusChanged = isGpsOn
                }
            }
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val radiusList = resources.getStringArray(R.array.Radius)
        val locationList = resources.getStringArray(R.array.Location)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.topAppBar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this@MapsActivity.finish()
        }

        val adapterRadiusList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, radiusList)
        val adapterLocationList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        binding.spinnerRaduis.adapter = adapterRadiusList
        binding.spinnerTypeLocation.adapter = adapterLocationList
        binding.spinnerRaduis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle item selection here
                raduis = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected
            }
        }
        binding.spinnerTypeLocation.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                location = locationList[position]
                locationType = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.mapList.observe(this) {
            hideLoading()
            googleMap.clear()
            for (item in it.body()!!.data) {
                if (locationType == item.type) {
                    val location = LatLng(item.lat, item.lng)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(location)
                            .title("${item.id}-${item.name}")
                            .snippet(item.address)
                    )
                    pinList.include(location)
                }
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(pinList.build(), 50))
        }
        mainViewModel.error.observe(this) {
            hideLoading()
            Log.e("ssss", it.toString())
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        } else {
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun setMapMarkers() {
        googleMap.let { googleMap ->
            googleMap.setOnInfoWindowClickListener { marker ->
                val intent = Intent(this, LocationInfoActivity::class.java)
                val firstDigit = marker.title?.substring(0, 1)
                val intValue: Int =
                    firstDigit.toString().toInt()  // Replace 42 with your actual integer value
                intent.putExtra("id_location", intValue)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK) {
            Toast.makeText(this, "Gps is on", Toast.LENGTH_SHORT).show()
            // Kiểm tra quyền truy cập vị trí
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Lấy vị trí hiện tại
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        // Xử lý vị trí hiện tại ở đây
                        if (location != null) {
                            val latitude = location.latitude
                            val longitude = location.longitude
                            // Sử dụng latitude và longitude theo ý của bạn
                            lat = location.latitude
                            lng = location.longitude
                            hideLoading()
                            // Sử dụng currentLatitude và currentLongitude cho mục đích của bạn
                            pinList.include(LatLng(lat, lng))
                            googleMap.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lat, lng),
                                    15f
                                )
                            )
                        }
                    }
            }

            setMapMarkers()

        } else if (activityResult.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Request is Canceled", Toast.LENGTH_SHORT).show()
        }

    }
}
