package com.tta.fitnessapplication.view.activity.map

import android.content.Intent
import android.net.Uri
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
        val receivedValue = intent.getIntExtra("id_location", 1)
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