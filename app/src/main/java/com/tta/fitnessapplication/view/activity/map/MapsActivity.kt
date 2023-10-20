package com.tta.fitnessapplication.view.activity.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

/**
 * package com.vnc.checkindemo.ui.road
 *
 * import android.Manifest
 * import android.annotation.SuppressLint
 * import android.app.Activity
 * import android.app.Dialog
 * import android.content.*
 * import android.content.pm.PackageManager
 * import android.graphics.Bitmap
 * import android.graphics.BitmapFactory
 * import android.graphics.drawable.BitmapDrawable
 * import android.location.Address
 * import android.location.Geocoder
 * import android.location.Location
 * import android.location.LocationManager
 * import android.os.Build
 * import android.os.Bundle
 * import android.os.Looper
 * import android.provider.Settings
 * import android.util.Log
 * import android.widget.Button
 * import android.widget.Toast
 * import androidx.appcompat.app.AlertDialog
 * import androidx.appcompat.app.AppCompatActivity
 * import androidx.core.app.ActivityCompat
 * import androidx.core.content.ContextCompat
 * import androidx.lifecycle.ViewModelProvider
 * import androidx.recyclerview.widget.LinearLayoutManager
 * import androidx.recyclerview.widget.RecyclerView
 * import com.google.android.gms.common.api.GoogleApiClient
 * import com.google.android.gms.common.api.PendingResult
 * import com.google.android.gms.common.api.Status
 * import com.google.android.gms.location.*
 * import com.google.android.gms.maps.CameraUpdateFactory
 * import com.google.android.gms.maps.GoogleMap
 * import com.google.android.gms.maps.OnMapReadyCallback
 * import com.google.android.gms.maps.SupportMapFragment
 * import com.google.android.gms.maps.model.*
 * import com.vnc.checkindemo.R
 * import com.vnc.checkindemo.adapter.ItemStoreAdapter
 * import com.vnc.checkindemo.databinding.ActivityMapsBinding
 * import com.vnc.checkindemo.data.model.Store
 * import com.vnc.checkindemo.ui.checkin.CheckInActivity
 * import com.vnc.checkindemo.ui.demo.MapsDemoLineActivity
 * import com.vnc.checkindemo.util.Constants
 * import kotlinx.coroutines.CoroutineScope
 * import kotlinx.coroutines.Dispatchers
 * import kotlinx.coroutines.launch
 * import kotlinx.coroutines.withContext
 * import java.util.*
 *
 * class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
 *     private lateinit var binding: ActivityMapsBinding
 *     lateinit var mGoogleMap: GoogleMap
 *     var mapFrag: SupportMapFragment? = null
 *     lateinit var mLocationRequest: LocationRequest
 *     var mLastLocation: Location? = null
 *     internal var mCurrLocationMarker: Marker? = null
 *     private var mFusedLocationClient: FusedLocationProviderClient? = null
 *     private lateinit var viewModel: RoadViewModel
 *     private var radius = 1
 *     private val permissionId = 2
 *     private lateinit var address: String
 *     private  var lat: String = "0"
 *     private  var lng: String = "0"
 *     private val height = 60
 *     private val width = 60
 *     lateinit var smallMarker: Bitmap
 *     private lateinit var token: String
 *     private lateinit var phone: String
 *     private var listAll = ArrayList<Store>()
 *     private val mAdapter = ItemStoreAdapter()
 *     private var googleApiClient: GoogleApiClient? = null
 *     private val REQUESTLOCATION = 199
 *     private lateinit var loginPreferences: SharedPreferences
 *     private val pinList = LatLngBounds.builder()
 *
 *     //
 *     private val respository = Respository()
 *     private val viewModelFactory = MapViewModelFactory(respository)
 *     private lateinit var mapViewModel: MapViewModel
 *
 *     //
 *     private var canChangeRadius = true
 *
 *     //
 *     private var dialogShow = true
 *
 *
 *     private var mLocationCallback: LocationCallback = object : LocationCallback() {
 *         override fun onLocationResult(locationResult: LocationResult) {
 *             val locationList = locationResult.locations
 *             if (locationList.isNotEmpty()) {
 *                 //The last location in the list is the newest
 *                 val location = locationList.last()
 *                 mLastLocation = location
 *                 if (mCurrLocationMarker != null) {
 *                     mCurrLocationMarker?.remove()
 *                 }
 *
 *                 //Place current location marker
 *                 val latLng = LatLng(location.latitude, location.longitude)
 *                 //move map camera
 *                 pinList.include(latLng)
 *             }
 *         }
 *     }
 *
 *     @SuppressLint("MissingInflatedId")
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         binding = ActivityMapsBinding.inflate(layoutInflater)
 *         setContentView(binding.root)
 * //        enableLoc()
 *         mapViewModel = ViewModelProvider(this, viewModelFactory)[MapViewModel::class.java]
 *         mapFrag =
 *             supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
 *         mapFrag?.getMapAsync(this)
 *         viewModel = RoadViewModel()
 *
 *         loginPreferences = getSharedPreferences(Constants.LOGIN_PREFS, MODE_PRIVATE)
 *         token = loginPreferences.getString(Constants.TOKEN, "").toString()
 *         phone = loginPreferences.getString(Constants.PHONE_USER, "").toString()
 *         //
 *         val width = resources.displayMetrics.widthPixels /10
 *         var height = resources.displayMetrics.heightPixels /20
 *         val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.marker)
 *         smallMarker = Bitmap.createScaledBitmap(b, width, height, false)
 *         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
 *
 *
 *         mapViewModel.store.observe(this@MapsActivity) {
 *             listAll.clear()
 *             listAll.addAll(it)
 *             mGoogleMap.clear()
 *             val scope = CoroutineScope(Dispatchers.Main)
 *             scope.launch {
 *                 withContext(Dispatchers.Main) {
 *                     for (i: Int in it.indices) {
 *                         val location =
 *                             LatLng(it[i].lat.trim().toDouble(), it[i].lng.trim().toDouble())
 *                         mGoogleMap.addMarker(
 *                             MarkerOptions()
 *                                 .position(location)
 *                                 .snippet(it[i].address+"............:"+it[i].id)
 *                                 .title(it[i].store_name)
 *                                 .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
 *                         )
 *                         pinList.include(location)
 *                     }
 *                 }
 *                 mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(pinList.build(), 50))
 *                 binding.tvThisLocation.text =
 *                     "Phạm vi danh sách cửa hàng hiển thị trong bán kính: $radius km"
 *             }
 *         }
 *         addEvent()
 *     }
 *
 *     @SuppressLint("MissingInflatedId")
 *     private fun addEvent() {
 *         binding.tvRadius1.setOnClickListener {
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 1, phone, lat, lng)
 *                     radius = 1
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius2.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 2, phone, lat, lng)
 *                     radius = 2
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius3.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 3, phone, lat, lng)
 *                     radius = 3
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius4.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 4, phone, lat, lng)
 *                     radius = 4
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius5.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 5, phone, lat, lng)
 *                     radius = 5
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius6.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 6, phone, lat, lng)
 *                     radius = 6
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius7.setOnClickListener {
 *
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     mapViewModel.storeList(token, 7, phone, lat, lng)
 *                     radius = 7
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *         binding.tvRadius8.setOnClickListener {
 *             if (checkPermissions() && canChangeRadius) {
 *                 if (isLocationEnabled()) {
 *                     binding.tvRadius1.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius2.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius3.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius4.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius5.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius6.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius7.setBackgroundResource(R.drawable.ic_baseline_circle_24)
 *                     binding.tvRadius8.setBackgroundResource(R.drawable.ic_baseline_circle_green_24)
 *                     mapViewModel.storeList(token, 8, phone, lat, lng)
 *                     radius = 8
 *                 } else {
 *                     Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                     val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                     startActivity(intent)
 *                 }
 *             } else {
 *                 requestPermissions()
 *             }
 *         }
 *
 *
 *
 *         binding.topAppBar.setOnMenuItemClickListener { menuItem ->
 *             when (menuItem.itemId) {
 *                 R.id.favorite -> {
 *                     if (dialogShow) {
 *                         dialogShow = false
 *                         val mAlertDialogBuilder = AlertDialog.Builder(this)
 *
 *                         // Row layout is inflated and added to ListView
 *                         val mRowList =
 *                             layoutInflater.inflate(R.layout.dialog_listview, null)
 *                         val mListView =
 *                             mRowList.findViewById<RecyclerView>(R.id.recStore)
 *                         val linearLayoutManager = LinearLayoutManager(this)
 *                         linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
 *                         mListView.layoutManager = linearLayoutManager
 *                         // Adapter is created and applied to ListView
 *                         mListView.adapter = mAdapter
 *                         Log.e("fffffff", listAll.toString())
 *                         mAdapter.setListStore(listAll, this)
 *
 *                         // Row item is set as view in the Builder and the
 *                         // ListView is displayed in the Alert Dialog
 *                         mAlertDialogBuilder.setView(mRowList)
 *                         val dialog = mAlertDialogBuilder.create()
 *                         dialog.show()
 *                         mAdapter.setClickSendData {
 *                             dialog.dismiss()
 *                             val storeLocation = listAll[it]
 *                             val latStorePick = storeLocation.lat
 *                             val lngStorePick = storeLocation.lng
 *                             val location =
 *                                 LatLng(latStorePick.toDouble(), lngStorePick.toDouble())
 *                             mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 50f))
 *                         }
 *
 *                         val button = mRowList.findViewById<Button>(com.vnc.checkindemo.R.id.view12)
 *                         button.setOnClickListener {
 *                             if (pinList != null) {
 *                                 mGoogleMap.animateCamera(
 *                                     CameraUpdateFactory.newLatLngBounds(
 *                                         pinList.build(),
 *                                         50
 *                                     )
 *                                 )
 *                                 dialog.dismiss()
 *                                 dialogShow = true
 *                             } else {
 *                                 Toast.makeText(
 *                                     this@MapsActivity,
 *                                     "Không có dữ liệu",
 *                                     Toast.LENGTH_SHORT
 *                                 ).show()
 *                             }
 *
 *                         }
 *                     } else {
 *
 *                     }
 *
 *                     true
 *                 }
 *
 *                 else -> false
 *             }
 *         }
 *
 *         binding.topAppBar.setNavigationOnClickListener {
 *             onBackPressedDispatcher.onBackPressed()
 *             finish()
 *         }
 *     }
 *
 *     public override fun onPause() {
 *         super.onPause()
 *         //stop location updates when Activity is no longer active
 *         mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
 *     }
 *
 *     override fun onMapReady(googleMap: GoogleMap) {
 *         mGoogleMap = googleMap
 *         getLocation()
 * //        mGoogleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
 *         mLocationRequest = LocationRequest()
 *         mLocationRequest.interval = 120000 // two minute interval
 *         mLocationRequest.fastestInterval = 120000
 *         mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
 *
 *         //
 *         val uiSettings = googleMap.uiSettings
 *         uiSettings.isCompassEnabled = true
 *         uiSettings.isZoomControlsEnabled = true
 *         uiSettings.isMyLocationButtonEnabled = true
 *         uiSettings.isMapToolbarEnabled = true
 *         uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = true
 *         //
 *         //
 *         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
 *             if (ContextCompat.checkSelfPermission(
 *                     this,
 *                     Manifest.permission.ACCESS_FINE_LOCATION
 *                 ) == PackageManager.PERMISSION_GRANTED
 *             ) {
 *                 //Location Permission already granted
 *                 mFusedLocationClient?.requestLocationUpdates(
 *                     mLocationRequest,
 *                     mLocationCallback,
 *                     Looper.myLooper()
 *                 )
 *                 mGoogleMap.isMyLocationEnabled = true
 *             } else {
 *                 //Request Location Permission
 *                 checkLocationPermission()
 *             }
 *         } else {
 *             mFusedLocationClient?.requestLocationUpdates(
 *                 mLocationRequest,
 *                 mLocationCallback,
 *                 Looper.myLooper()
 *             )
 *             mGoogleMap.isMyLocationEnabled = true
 *         }
 *         googleMap.let { googleMap ->
 *             googleMap.setOnInfoWindowClickListener { marker ->
 *                 val dialog = Dialog(this)
 *                 dialog.setContentView(R.layout.dialog_show_option_map)
 *                 dialog.show()
 *
 *                 val btnCheckIn = dialog.findViewById<Button>(R.id.btnCheckIn)
 *                 val btnStartGo = dialog.findViewById<Button>(R.id.btnStartGo)
 *
 *                 btnCheckIn.setOnClickListener {
 *                     var strs = marker.snippet.toString().split(":")[1].toInt()
 *                     var intent = Intent(this, CheckInActivity::class.java)
 *                     intent.putExtra("id_store", strs)
 *                     intent.putExtra("lat", marker.position.latitude)
 *                     intent.putExtra("Lng", marker.position.longitude)
 *                     startActivity(intent)
 *                     dialog.dismiss()
 *                 }
 *
 *                 btnStartGo.setOnClickListener {
 *                     var intent = Intent(this, MapsDemoLineActivity::class.java)
 *                     intent.putExtra("address", marker.snippet.toString())
 *                     intent.putExtra("title", marker.title.toString())
 *                     intent.putExtra("lat", marker.position.latitude)
 *                     intent.putExtra("Lng", marker.position.longitude)
 *                     startActivity(intent)
 *                     dialog.dismiss()
 *                 }
 *                 false
 *             }
 *         }
 *     }
 *
 *     private fun checkLocationPermission() {
 *         if (ActivityCompat.checkSelfPermission(
 *                 this,
 *                 Manifest.permission.ACCESS_FINE_LOCATION
 *             ) != PackageManager.PERMISSION_GRANTED
 *         ) {
 *             // Should we show an explanation?
 *             if (ActivityCompat.shouldShowRequestPermissionRationale(
 *                     this,
 *                     Manifest.permission.ACCESS_FINE_LOCATION
 *                 )
 *             ) {
 *                 // Show an explanation to the user *asynchronously* -- don't block
 *                 // this thread waiting for the user's response! After the user
 *                 // sees the explanation, try again to request the permission.
 *                 AlertDialog.Builder(this)
 *                     .setTitle("Location Permission Needed")
 *                     .setMessage("This app needs the Location permission, please accept to use location functionality")
 *                     .setPositiveButton(
 *                         "OK"
 *                     ) { _, _ ->
 *                         //Prompt the user once explanation has been shown
 *                         ActivityCompat.requestPermissions(
 *                             this@MapsActivity,
 *                             arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
 *                             MY_PERMISSIONS_REQUEST_LOCATION
 *                         )
 *                     }
 *                     .create()
 *                     .show()
 *             } else {
 *                 // No explanation needed, we can request the permission.
 *                 ActivityCompat.requestPermissions(
 *                     this,
 *                     arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
 *                     MY_PERMISSIONS_REQUEST_LOCATION
 *                 )
 *             }
 *         }
 *     }
 *
 *     override fun onRequestPermissionsResult(
 *         requestCode: Int,
 *         permissions: Array<String>, grantResults: IntArray
 *     ) {
 *         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
 *         when (requestCode) {
 *             MY_PERMISSIONS_REQUEST_LOCATION -> {
 *                 // If request is cancelled, the result arrays are empty.
 *                 if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 *
 *                     // permission was granted, yay! Do the
 *                     // location-related task you need to do.
 *                     if (ContextCompat.checkSelfPermission(
 *                             this,
 *                             Manifest.permission.ACCESS_FINE_LOCATION
 *                         ) == PackageManager.PERMISSION_GRANTED
 *                     ) {
 *
 *                         mFusedLocationClient?.requestLocationUpdates(
 *                             mLocationRequest,
 *                             mLocationCallback,
 *                             Looper.myLooper()
 *                         )
 *                         mGoogleMap.isMyLocationEnabled = true
 *                     }
 *                     getLocation()
 *
 *                 } else {
 *
 *                     // permission denied, boo! Disable the
 *                     // functionality that depends on this permission.
 *                     val builder: AlertDialog.Builder = AlertDialog.Builder(this)
 *                     builder.setMessage("Vui lòng vào phần cài đặt cấp quyền vị trí để sử dụng ứng dụng")
 *                     builder.setPositiveButton(
 *                         "Ok",
 *                         DialogInterface.OnClickListener { _, _ ->
 *
 *                         })
 *                     val alert: AlertDialog = builder.create()
 *                     alert.show()
 *                 }
 *                 return
 *             }
 *         }// other 'case' lines to check for other
 *         // permissions this app might request
 *     }
 *
 *     companion object {
 *         val MY_PERMISSIONS_REQUEST_LOCATION = 99
 *     }
 *
 *     @SuppressLint("MissingPermission", "SetTextI18n")
 *     private fun getLocation() {
 *         if (checkPermissions()) {
 *             if (isLocationEnabled()) {
 *                 mFusedLocationClient?.lastLocation?.addOnCompleteListener(this) { task ->
 *                     val location: Location? = task.result
 * //                    if (location != null) {
 * //                        val geocoder = Geocoder(this, Locale.getDefault())
 * //                        val list: List<Address> =
 * //                            geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
 * //                        lat = list[0].latitude.toString()
 * //                        lng = list[0].longitude.toString()
 * //                        address = list[0].getAddressLine(0).toString()
 * //                        binding.tvNowLocation.text = address.trim()
 * //                        mapViewModel.storeList(token, radius, phone, lat, lng)
 * //                    }
 *
 *                     //
 *                     if (Build.VERSION.SDK_INT <= 31) {
 *                         if (location != null) {
 *                             if (!location.isFromMockProvider) {
 *                                 val geocoder = Geocoder(this, Locale.getDefault())
 *                                 val list: List<Address> =
 *                                     geocoder.getFromLocation(
 *                                         location.latitude,
 *                                         location.longitude,
 *                                         1
 *                                     )!!
 *                                 lat = list[0].latitude.toString()
 *                                 lng = list[0].longitude.toString()
 *                                 address = list[0].getAddressLine(0).toString()
 *                                 binding.tvNowLocation.text = address.trim()
 *                                 mapViewModel.storeList(token, radius, phone, lat, lng)
 *                             } else {
 *                                 canChangeRadius = false
 *                                 // dang fake gps
 *                                 val builder: android.app.AlertDialog.Builder =
 *                                     android.app.AlertDialog.Builder(this)
 *                                 builder.setMessage("Vui lòng sử dụng vị trí thực để lấy vị trí, việc sử dụng ứng dụng khác để tác động lên GPS là không được phép")
 *                                 builder.setPositiveButton(
 *                                     "Ok",
 *                                     DialogInterface.OnClickListener { dialogInterface, i ->
 *
 *                                     })
 *                                 val alert: android.app.AlertDialog = builder.create()
 *                                 alert.show()
 *                             }
 *                         }
 *                     } else {
 *                         if (location != null && !location.isMock) {
 *                             val geocoder = Geocoder(this, Locale.getDefault())
 *                             val list: List<Address> =
 *                                 geocoder.getFromLocation(location.latitude, location.longitude, 1)!!
 *                             lat = list[0].latitude.toString()
 *                             lng = list[0].longitude.toString()
 *                             address = list[0].getAddressLine(0).toString()
 *                             binding.tvNowLocation.text = address.trim()
 *                             mapViewModel.storeList(token, radius, phone, lat, lng)
 *                         } else {
 *                             canChangeRadius = false
 *                             // dang fake gps
 *                             val builder: android.app.AlertDialog.Builder =
 *                                 android.app.AlertDialog.Builder(this)
 *                             builder.setMessage("Vui lòng sử dụng vị trí thực để lấy vị trí, việc sử dụng ứng dụng khác để tác động lên GPS là không được phép")
 *                             builder.setPositiveButton(
 *                                 "Ok",
 *                                 DialogInterface.OnClickListener { dialogInterface, i ->
 *
 *                                 })
 *                             val alert: android.app.AlertDialog = builder.create()
 *                             alert.show()
 *                         }
 *                     }
 *                     //
 *                 }
 *             } else {
 *                 Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
 *                 val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
 *                 startActivity(intent)
 *             }
 *         } else {
 *             requestPermissions()
 *         }
 *     }
 *
 *     private fun isLocationEnabled(): Boolean {
 *         val locationManager: LocationManager =
 *             getSystemService(Context.LOCATION_SERVICE) as LocationManager
 *         return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
 *             LocationManager.NETWORK_PROVIDER
 *         )
 *     }
 *
 *     private fun checkPermissions(): Boolean {
 *         if (ActivityCompat.checkSelfPermission(
 *                 this,
 *                 Manifest.permission.ACCESS_COARSE_LOCATION
 *             ) == PackageManager.PERMISSION_GRANTED &&
 *             ActivityCompat.checkSelfPermission(
 *                 this,
 *                 Manifest.permission.ACCESS_FINE_LOCATION
 *             ) == PackageManager.PERMISSION_GRANTED
 *         ) {
 *             return true
 *             getLocation()
 *         }
 *         return false
 *     }
 *
 *     private fun requestPermissions() {
 *         ActivityCompat.requestPermissions(
 *             this,
 *             arrayOf(
 *                 Manifest.permission.ACCESS_COARSE_LOCATION,
 *                 Manifest.permission.ACCESS_FINE_LOCATION
 *             ),
 *             permissionId
 *         )
 *     }
 *
 *     private fun enableLoc() {
 *         googleApiClient = GoogleApiClient.Builder(this)
 *             .addApi(LocationServices.API)
 *             .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
 *                 override fun onConnected(bundle: Bundle?) {}
 *                 override fun onConnectionSuspended(i: Int) {
 *                     googleApiClient?.connect()
 *                 }
 *             })
 *             .addOnConnectionFailedListener {
 *             }.build()
 *         googleApiClient?.connect()
 *         val locationRequest = LocationRequest.create()
 *         locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
 *         locationRequest.interval = 30 * 1000.toLong()
 *         locationRequest.fastestInterval = 5 * 1000.toLong()
 *         val builder = LocationSettingsRequest.Builder()
 *             .addLocationRequest(locationRequest)
 *         builder.setAlwaysShow(true)
 *         val result: PendingResult<LocationSettingsResult> =
 *             LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
 *         result.setResultCallback { result ->
 *             val status: Status = result.status
 *             when (status.statusCode) {
 *                 LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
 *                     status.startResolutionForResult(
 *                         this,
 *                         REQUESTLOCATION
 *                     )
 *                 } catch (_: IntentSender.SendIntentException) {
 *                 }
 *             }
 *         }
 *     }
 *
 *     override fun onActivityResult(
 *         requestCode: Int,
 *         resultCode: Int,
 *         data: Intent?
 *     ) {
 *         super.onActivityResult(requestCode, resultCode, data)
 *         when (requestCode) {
 *             REQUESTLOCATION -> when (resultCode) {
 *                 Activity.RESULT_OK -> Log.d("abc", "OK")
 *                 Activity.RESULT_CANCELED -> Log.d("abc", "CANCEL")
 *             }
 *         }
 *     }
 * }
 *
 */