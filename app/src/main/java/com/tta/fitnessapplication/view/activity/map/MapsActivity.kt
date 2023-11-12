package com.tta.fitnessapplication.view.activity.map

import android.Manifest
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
import com.google.android.gms.maps.model.MarkerOptions
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.databinding.ActivityMapsBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private val PERMISSION_REQUEST_CODE = 123
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var raduis = 1
    private var location = "gym"
    private var locationType = 0

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
        checkLocationPermission()
        mainViewModel.getDataMap(lat, lng, "$raduis")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val radiusList = resources.getStringArray(R.array.Radius)
        val locationList = resources.getStringArray(R.array.Location)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.topAppBar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this@MapsActivity.finish()
        }
        binding.tvRaduis.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setOnClickListener {
            this.hideKeyboard()
        }
        val adapterRadiusList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, radiusList)
        val adapterLocationList =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        binding.tvRaduis.setAdapter(adapterRadiusList)
        binding.tvGender.setAdapter(adapterLocationList)
        binding.tvRaduis.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                raduis = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        binding.tvGender.onItemSelectedListener = object :
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
                            .title(item.name)
                            .snippet(item.address)
                    )
                }
            }
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
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the missing permissions.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // The permissions are already granted, so enable the "My Location" feature.
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            checkGPSStatus()
        }
    }

    private fun checkGPSStatus() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGPSEnabled) {
            // Xác định quyền truy cập vị trí đã được cấp chưa
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Xử lý vị trí trả về ở đây
                        if (location != null) {
                            lat = location.latitude
                            lng = location.longitude
                            // Sử dụng latitude và longitude cho mục đích của bạn
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,lng), 15f))
                        } else {
                            // Không thể lấy được vị trí hiện tại
                            // Xử lý khi không thể lấy được vị trí
                            showGPSDisabledDialog()
                        }
                    }
                    .addOnFailureListener { e ->
                        // Xử lý khi có lỗi xảy ra khi cố gắng lấy vị trí
                    }
            } else {
                // Nếu quyền truy cật vị trí chưa được cấp, hiển thị thông báo hoặc yêu cầu cấp quyền
                // Ví dụ:
                showLocationPermissionDialog()
            }
        } else {
            // Hiển thị dialog thông báo cần bật GPS
            // Ví dụ:
            showGPSDisabledDialog()
        }
    }

    private fun showGPSDisabledDialog() {
    // Hiển thị dialog thông báo cần bật GPS
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Vui lòng bật GPS để sử dụng tính năng này")
            .setPositiveButton("OK") { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Hủy") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                checkGPSStatus()
            } else {
                // Hiển thị dialog thông báo cần quyền truy cập vị trí
                // Ví dụ:
//                showLocationPermissionDialog()
            }
        }
    }

    private fun showLocationPermissionDialog() {
        // Hiển thị dialog thông báo cần quyền truy cập vị trí
        // Sử dụng AlertDialog.Builder để tạo dialog và thông báo cho người dùng
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Ứng dụng cần quyền truy cập vị trí để tiếp tục sử dụng")
            .setPositiveButton("OK") { dialog, which ->
                // Mở cài đặt để người dùng cấp quyền truy cập vị trí
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .setNegativeButton("Hủy") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
