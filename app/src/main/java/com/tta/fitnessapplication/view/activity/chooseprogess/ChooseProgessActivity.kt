package com.tta.fitnessapplication.view.activity.chooseprogess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.tta.fitnessapplication.R
import me.relex.circleindicator.CircleIndicator3
import kotlin.math.abs

class ChooseProgessActivity : AppCompatActivity() {
    private lateinit var indicator: CircleIndicator3
    private lateinit var  viewPager: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_progess)
        init()
    }

    private fun init(){
        viewPager = findViewById(R.id.viewPager)
        indicator = findViewById(R.id.indicator)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.group_10295)
        imageList.add(R.drawable.group_10302)
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
                Log.e("viewpager",position.toString())
            }
        })
    }
}