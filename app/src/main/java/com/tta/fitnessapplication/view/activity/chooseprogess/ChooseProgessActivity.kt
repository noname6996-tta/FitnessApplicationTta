package com.tta.fitnessapplication.view.activity.chooseprogess

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivityChooseProgessBinding
import com.tta.fitnessapplication.view.activity.register.DoneSingUpActivity
import com.tta.fitnessapplication.view.base.BaseActivity

class ChooseProgessActivity : BaseActivity<ActivityChooseProgessBinding>() {
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    private var progess = 0
    var receivedIntValue = 1
    override fun getDataBinding(): ActivityChooseProgessBinding {
        return ActivityChooseProgessBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        receivedIntValue = intent.getIntExtra("CHOOSE_PROCESS", 1)
        val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        binding.apply {
            handler = Handler(Looper.myLooper()!!)
            imageList = ArrayList()
            imageList.add(R.drawable.group_10302)
            imageList.add(R.drawable.group_10295)
            imageList.add(R.drawable.group_10304)
            adapter = ImageAdapter(imageList, viewPager)
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 3
            viewPager.clipToPadding = false
            viewPager.clipChildren = false
            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            indicator.setViewPager(viewPager)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    // The position of the currently selected item
                    // Write your logic here to handle the position
                    // It will be called whenever the page is scrolled or changed
                    progess = position
                    // 0 : Improve Shape
                    // 1 : Lean & Tone
                    // 2 : Lose a Fat
                    Log.e("viewpager", position.toString())
                }
            })

            btnConfirm.setOnClickListener {
                showLoading()
                mainViewModel.updateUserProgess(
                    emailUser, progess
                )
            }
        }
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.updateUser.observe(this@ChooseProgessActivity){
            if (it.isSuccessful) {
                hideLoading()
                when(receivedIntValue){
                    1 -> {
                        startActivity(Intent(this, DoneSingUpActivity::class.java))
                        finish()
                    }
                    0 -> {
                        onBackPressedDispatcher.onBackPressed()
                        finish()
                    }
                }

            } else {
                hideLoading()
                Log.e("tta", it.errorBody().toString())
            }
        }
    }
}