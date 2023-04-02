package com.tta.fitnessapplication.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding

class SleepTrackerActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySleepTrackerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpChart()
        setData(7, 10)
        addEvent()
    }

    private fun addEvent() {
        binding.view13.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpChart(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        title = "CubicLineChartActivity"
        binding.chart1.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.chart1.setBackgroundColor(Color.rgb(157, 206, 255))

        // no description text

        // no description text
        binding.chart1.description.isEnabled = false

        // enable touch gestures

        // enable touch gestures
        binding.chart1.setTouchEnabled(true)

        // enable scaling and dragging

        // enable scaling and dragging
        binding.chart1.isDragEnabled = true
        binding.chart1.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        binding.chart1.setPinchZoom(false)

        binding.chart1.setDrawGridBackground(false)
        binding.chart1.maxHighlightDistance = 300f

        val x: XAxis = binding.chart1.xAxis
        x.isEnabled = false

        val y: YAxis = binding.chart1.axisLeft
        y.setLabelCount(6, false)
        y.textColor = Color.WHITE
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE

        binding.chart1.axisRight.isEnabled = false


        // lower max, as cubic runs significantly slower than linear

        // lower max, as cubic runs significantly slower than linear
        binding.chart1.legend.isEnabled = false

        binding.chart1.animateXY(2000, 2000)

        // don't forget to refresh the drawing

        // don't forget to refresh the drawing
        binding.chart1.invalidate()
    }

    private fun setData(count: Int, range: Int) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = 8
            values.add(Entry(i.toFloat(), `val`.toFloat()))
        }
        val set1: LineDataSet
        if (binding.chart1.data != null &&
            binding.chart1.data.dataSetCount > 0
        ) {
            set1 = binding.chart1.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding.chart1.data.notifyDataChanged()
            binding.chart1.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillColor = Color.WHITE
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> binding.chart1.axisRight.axisMinimum }

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            binding.chart1.data = data
        }
    }
}