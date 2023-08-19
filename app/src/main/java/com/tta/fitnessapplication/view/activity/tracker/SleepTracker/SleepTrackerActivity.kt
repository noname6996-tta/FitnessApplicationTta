package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding

class SleepTrackerActivity : AppCompatActivity(), OnSeekBarChangeListener,
    OnChartValueSelectedListener {
    private lateinit var binding: ActivitySleepTrackerBinding
    private val viewModel = SleepTrackerViewModel()
    private lateinit var chart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObsever()
//        setUpChart()
        addEvent()
    }

    private fun addObsever() {
        viewModel.getData()
        viewModel.listSleepTracker.observe(this) {
            if (it.isEmpty()) {
//                setData(7, 10)
                binding.tvTimeSleep.text = "No data"
            } else {

            }
        }
        viewModel.message.observe(this) {

        }
    }

    private fun addEvent() {
        binding.view13.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.view22.setOnClickListener {
            startActivity(Intent(this, SleepScheduleActivity::class.java))
        }
    }

//    private fun setUpChart() {
//        title = "BarChartActivity"
//        var chart = binding.chart1
//        chart.setOnChartValueSelectedListener(this)
//
//        chart.setDrawBarShadow(false)
//        chart.setDrawValueAboveBar(true)
//
//        chart.getDescription().setEnabled(false)
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        chart.setMaxVisibleValueCount(60)
//
//        // scaling can now only be done on x- and y-axis separately
//
//        // scaling can now only be done on x- and y-axis separately
//        chart.setPinchZoom(false)
//
//        chart.setDrawGridBackground(false)
//        // chart.setDrawYLabels(false);
//
//        // chart.setDrawYLabels(false);
//        val xAxisFormatter: IAxisValueFormatter = DayAxisValueFormatter(chart)
//
//        val xAxis: XAxis = chart.getXAxis()
//        xAxis.position = XAxisPosition.BOTTOM
//        xAxis.typeface = tfLight
//        xAxis.setDrawGridLines(false)
//        xAxis.granularity = 1f // only intervals of 1 day
//
//        xAxis.labelCount = 7
//        xAxis.setValueFormatter(xAxisFormatter)
//
//        val custom: IAxisValueFormatter = MyAxisValueFormatter()
//
//        val leftAxis: YAxis = chart.getAxisLeft()
//        leftAxis.typeface = tfLight
//        leftAxis.setLabelCount(8, false)
//        leftAxis.setValueFormatter(custom)
//        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
//        leftAxis.spaceTop = 15f
//        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
//
//
//        val rightAxis: YAxis = chart.getAxisRight()
//        rightAxis.setDrawGridLines(false)
//        rightAxis.typeface = tfLight
//        rightAxis.setLabelCount(8, false)
//        rightAxis.setValueFormatter(custom)
//        rightAxis.spaceTop = 15f
//        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
//
//
//        val l: Legend = chart.getLegend()
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
//        l.form = LegendForm.SQUARE
//        l.formSize = 9f
//        l.textSize = 11f
//        l.xEntrySpace = 4f
//
//        val mv = XYMarkerView(this, xAxisFormatter)
//        mv.setChartView(chart) // For bounds control
//
//        chart.setMarker(mv) // Set the marker to the chart
//        // chart.setDrawLegend(false);
//    }
//
//    private fun setData(count: Int, range: Float) {
//        val start = 1f
//        val values = ArrayList<BarEntry>()
//        var i = start.toInt()
//        while (i < start + count) {
//            val `val` = (Math.random() * (range + 1)).toFloat()
//            if (Math.random() * 100 < 25) {
//                values.add(BarEntry(i, `val`, resources.getDrawable(R.drawable.star)))
//            } else {
//                values.add(BarEntry(i.toFloat(), `val`))
//            }
//            i++
//        }
//        val set1: BarDataSet
//        if (chart.getData() != null &&
//            chart.getData().getDataSetCount() > 0
//        ) {
//            set1 = chart.getData().getDataSetByIndex(0)
//            set1.values = values
//            chart.getData().notifyDataChanged()
//            chart.notifyDataSetChanged()
//        } else {
//            set1 = BarDataSet(values, "The year 2017")
//            set1.setDrawIcons(false)
//            val startColor1 = ContextCompat.getColor(this, R.color.holo_orange_light)
//            val startColor2 = ContextCompat.getColor(this, R.color.holo_blue_light)
//            val startColor3 = ContextCompat.getColor(this, R.color.holo_orange_light)
//            val startColor4 = ContextCompat.getColor(this, R.color.holo_green_light)
//            val startColor5 = ContextCompat.getColor(this, R.color.holo_red_light)
//            val endColor1 = ContextCompat.getColor(this, R.color.holo_blue_dark)
//            val endColor2 = ContextCompat.getColor(this, R.color.holo_purple)
//            val endColor3 = ContextCompat.getColor(this, R.color.holo_green_dark)
//            val endColor4 = ContextCompat.getColor(this, R.color.holo_red_dark)
//            val endColor5 = ContextCompat.getColor(this, R.color.holo_orange_dark)
//            val gradientFills: MutableList<Fill> = ArrayList<Fill>()
//            gradientFills.add(Fill(startColor1, endColor1))
//            gradientFills.add(Fill(startColor2, endColor2))
//            gradientFills.add(Fill(startColor3, endColor3))
//            gradientFills.add(Fill(startColor4, endColor4))
//            gradientFills.add(Fill(startColor5, endColor5))
//            set1.setFills(gradientFills)
//            val dataSets = ArrayList<IBarDataSet>()
//            dataSets.add(set1)
//            val data = BarData(dataSets)
//            data.setValueTextSize(10f)
//            data.setValueTypeface(tfLight)
//            data.barWidth = 0.9f
//            chart.setData(data)
//        }
//    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }
}