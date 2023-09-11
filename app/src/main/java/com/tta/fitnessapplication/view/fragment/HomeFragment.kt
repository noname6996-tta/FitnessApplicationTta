package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ClipDrawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.activity.history.HistoryAdapter
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.SleepTrackerActivity
import com.tta.fitnessapplication.view.activity.tracker.calortracker.CalorieTrackerActivity
import com.tta.fitnessapplication.view.base.BaseFragment
import java.time.LocalDate
import java.util.Calendar

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var mImageDrawable: ClipDrawable
    private val today = LocalDate.now()
    private val eventsAdapter = HistoryAdapter()
    override fun getDataBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        //
//        val viewModel = HistoryViewModelGoogleData()
//        viewModel.getData()
//        viewModel.listStepsCount.observe(viewLifecycleOwner) {
//            binding.tvHomeStep.text = it.last().value + " steps"
//        }
//        viewModel.listWeight.observe(viewLifecycleOwner) {
//
//        }
//        viewModel.listCaloriesExpended.observe(viewLifecycleOwner) {
//            binding.tvCalor.text = it.last().value + "Calr"
//        }
//        viewModel.listHeartMinutes.observe(viewLifecycleOwner) {
//            binding.tvHeartRate.text = it.last().value + " BPM"
//        }
//        viewModel.listSleepTracker.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                binding.textView27.text = it.last().value + " h"
//            } else {
//                binding.textView27.text = "No data"
//            }
//        }
//        viewModel.message.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
        //

        val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        val idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        if (emailUser != ""&&isConnect) {
            mainViewModel.getUserData(emailUser)
        }
        mainViewModel.dataExercise.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                var dataProfile = mainViewModel.dataExercise.value?.body()?.data
                Log.e("dataProfile", dataProfile.toString())
                binding.textView2.text =
                    "${dataProfile!![0].firstname} ${dataProfile!![0].lastname}"
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
        if (isConnect){
            mainViewModel.getHistoryByDate(idUser, fullDateFormatter.format(today))
        }
        mainViewModel.listHistoryByDate.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                if (it.body()?.response == 0) {
                    binding.tvNoDataRecycle.visibility = View.VISIBLE
                    binding.tvNoDataRecycle.text = it.body()?.message
                } else {
                    if (!it.body()?.data.isNullOrEmpty()) {
                        binding.tvNoDataRecycle.visibility = View.GONE
                        eventsAdapter.events.clear()
                        for (i in 0 until it.body()?.data!!.size) {
                            if (i <= 2) {
                                eventsAdapter.events.add(it.body()?.data!![i])
                            }
                        }
                        eventsAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun addEvent() {
        binding.cardViewSleep.setOnClickListener {
            startActivity(Intent(activity, SleepTrackerActivity::class.java))
        }
        binding.cardViewWater.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_waterTrackerActivity)
        }
        binding.cardViewEat.setOnClickListener {
            startActivity(Intent(activity, CalorieTrackerActivity::class.java))
        }

        binding.tvSeeMoreHistory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyActivity)
        }

        binding.imgNotifiHome.setOnClickListener {
            // go to noti activity
        }
    }

    override fun initView() {

        mImageDrawable = binding.imgToScroll.drawable as ClipDrawable
        mImageDrawable.level = 5000

        initCalender()

        initPieChart()

        initRecycleViewHistory()
    }

    private fun initRecycleViewHistory() {
        binding.recLatesProgess.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
        }
    }

    private fun initPieChart() {
        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        binding.pieChart.dragDecelerationFrictionCoef = 0.95f

        // on below line we are setting hole
        // and hole color for pie chart
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        binding.pieChart.setTransparentCircleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        binding.pieChart.holeRadius = 0f
        binding.pieChart.transparentCircleRadius = 0f

        // on below line we are setting center text
        binding.pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        binding.pieChart.rotationAngle = 0f

        // enable rotation of the binding.pieChart by touch
        binding.pieChart.isRotationEnabled = true
        binding.pieChart.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.setEntryLabelColor(Color.WHITE)
        binding.pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(70f))
        entries.add(PieEntry(30f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 1f
        dataSet.iconsOffset = MPPointF(0f, 10f)
        dataSet.selectionShift = 1f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.white))
        colors.add(resources.getColor(R.color.pink))

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        binding.pieChart.data = data

        // undo all highlights
        binding.pieChart.highlightValues(null)

        // loading chart
        binding.pieChart.invalidate()
    }

    private fun initCalender() {
        val arrayDay = ArrayList<String>()
        val calendar = Calendar.getInstance()
        // Set the calendar to the current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        // Print each day number of the week
        for (i in 1..7) {
            println(calendar.get(Calendar.DAY_OF_MONTH))
            arrayDay.add(calendar.get(Calendar.DAY_OF_MONTH).toString())
            calendar.add(Calendar.DAY_OF_WEEK, 1) // Move to the next day
        }
        val dayNumber = calendar.get(Calendar.DAY_OF_MONTH).toString()
        binding.tvDay1.text = arrayDay[0]
        binding.tvDay2.text = arrayDay[1]
        binding.tvDay3.text = arrayDay[2]
        binding.tvDay4.text = arrayDay[3]
        binding.tvDay5.text = arrayDay[4]
        binding.tvDay6.text = arrayDay[5]
        binding.tvDay7.text = arrayDay[6]
        when (dayNumber) {
            arrayDay[0] -> {
                binding.tvDay1.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[1] -> {
                binding.tvDay2.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[2] -> {
                binding.tvDay3.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[3] -> {
                binding.tvDay4.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[4] -> {
                binding.tvDay5.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[5] -> {
                binding.tvDay6.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[6] -> {
                binding.tvDay7.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }
        }
    }
}