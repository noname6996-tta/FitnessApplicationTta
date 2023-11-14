package com.tta.fitnessapplication.view.activity.map

import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityLocationInfoBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class LocationInfoActivity : BaseActivity<ActivityLocationInfoBinding>() {
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    override fun getDataBinding(): ActivityLocationInfoBinding {
        return ActivityLocationInfoBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        showLoading()
        val receivedValue = intent.getIntExtra("id_location", 1)
        mainViewModel.getLocation(receivedValue)
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.location.observe(this) {
            Glide.with(this)
                .load(it.body()?.image)
                .error(R.drawable.alarm_clock)
                .into(binding.imageView9)
            binding.tvNameLocation.text = it.body()?.name
            binding.tvAddressLocation.text = it.body()?.address
            binding.tvDescLocation.text = it.body()?.info
            lat = it.body()?.lat.toString().toDouble()
            lng = it.body()?.lng.toString().toDouble()
            hideLoading()
        }
        mainViewModel.error.observe(this){
            hideLoading()
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            view16.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
                this@LocationInfoActivity.finish()
            }

            btnGoToMap.setOnClickListener {
                val location = "${lat},${lng}"
                val gmmIntentUri = Uri.parse("geo:$location?q=$location&z=zoom_level")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }
    }
}