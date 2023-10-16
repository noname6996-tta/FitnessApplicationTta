package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ClipDrawable
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.activity.history.HistoryAdapter
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.activity.tracker.calortracker.calculateBMI
import com.tta.fitnessapplication.view.base.BaseFragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var mImageDrawable: ClipDrawable
    private val eventsAdapter = HistoryAdapter()
    private val entries: ArrayList<PieEntry> = ArrayList()
    private lateinit var dailyWater: String
    override fun getDataBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        historyViewModel.getWaterListByDate(Constant.DATE.fullDateFormatter.format(today))
        Fitness.getRecordingClient(requireActivity(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnSuccessListener {
                Log.i("TAG","Subscription was successful!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "There was a problem subscribing ", e)
            }

        Fitness.getHistoryClient(requireActivity(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener { result ->
                val totalSteps =
                    result.dataPoints.firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
                // Do something with totalSteps
                Log.i("TAG dataPoints", result.dataPoints.toString())
                binding.tvHomeStep.text = "$totalSteps Steps"
            }
            .addOnFailureListener { e ->
                Log.i("TAG", "There was a problem getting steps.", e)
            }
        getHeartRate()
//        getStepDataByDate()
    }

    private fun getHeartRate(){
        val startTime = LocalDateTime.of(2023, 9, 29, 0, 0)
        val endTime = LocalDateTime.of(2023, 9, 29, 0, 0).plusDays(1)

        val startDateTime = startTime.atZone(ZoneId.systemDefault())
        val endDateTime = endTime.atZone(ZoneId.systemDefault())
//        val request = DataReadRequest.Builder()
//            .read(DataType.TYPE_HEART_RATE_BPM)
//            .setTimeRange(startDateTime.toEpochSecond(), endDateTime.toEpochSecond(), TimeUnit.MILLISECONDS)
//            .build()

        val request = DataReadRequest.Builder()
            .aggregate(DataSource.Builder()
                .setType(DataSource.TYPE_DERIVED)
                .setDataType(DataType.TYPE_HEART_RATE_BPM)
                .setAppPackageName("com.google.android.gms")
                .setStreamName("resting_heart_rate<-merge_heart_rate_bpm")
                .build()).read(DataType.TYPE_HEART_RATE_BPM)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startDateTime.toEpochSecond(), endDateTime.toEpochSecond(), TimeUnit.SECONDS).build()

        Fitness.getHistoryClient(requireActivity(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
            .readData(request)
            .addOnSuccessListener { response ->
                val heartRateList = ArrayList<Int>()
                val totalSteps = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .fold("") { accumulator, dataPoint -> accumulator + dataPoint.getValue(Field.FIELD_BPM).asString() }
                for (bucket in response.buckets) {
                    for (dataSet in bucket.dataSets) {
                        for (dataPoint in dataSet.dataPoints) {
                            for (field in dataPoint.dataType.fields) {
                                if (field.name == Field.FIELD_BPM.name) {
                                    val heartRate = dataPoint.getValue(field).asInt()
                                    heartRateList.add(heartRate)
                                }
                            }
                        }
                    }
                }
                Log.i("TAG", "Heart rate values: $heartRateList")
                // Process the heart rate data as needed
                Log.i("TAG", "Heart rate values: $totalSteps")
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "There was a problem retrieving heart rate data.", exception)
            }
    }

    private fun getStepDataByDate(){
        val startTime = LocalDateTime.of(2023, 9, 1, 0, 0)
        val endTime = LocalDateTime.of(2023, 9, 26, 0, 0).plusDays(1)

        val startDateTime = startTime.atZone(ZoneId.systemDefault())
        val endDateTime = endTime.atZone(ZoneId.systemDefault())

//        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
//        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())

        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startDateTime.toEpochSecond(), endDateTime.toEpochSecond(), TimeUnit.SECONDS)
            .build()

        Fitness.getHistoryClient(requireActivity(), GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions))
            .readData(request)
            .addOnSuccessListener { response ->
                val totalSteps = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumBy { it.getValue(Field.FIELD_STEPS).asInt() }
                Log.i("TAG", "Total steps: $totalSteps")
                Log.i("TAG buckets", "Total buckets: ${response.buckets.flatMap { it.dataSets }.flatMap { it.dataPoints }.sumBy { it.getValue(Field.FIELD_STEPS).asInt() }}")
            }
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
        mainViewModel.getUserData(emailUser)
        mainViewModel.dataExercise.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                val dataProfile = mainViewModel.dataExercise.value?.body()?.data
                binding.textView2.text =
                    "${dataProfile!![0].firstname} ${dataProfile!![0].lastname}"
                Log.e("aaa", dataProfile[0].toString())
                var bmi =
                    calculateBMI(dataProfile[0].weight.toDouble(), dataProfile[0].tall.toDouble())
                val y = 100f - bmi.toFloat()
                val x = bmi.toFloat()
                entries.clear()
                entries.add(PieEntry(y))
                entries.add(PieEntry(x))
                initPieChart()
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
        if (isConnect) {
            mainViewModel.getHistoryByDate(idUser, fullDateFormatter.format(today))
        }

        historyViewModel.historyList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.tvNoDataRecycle.visibility = View.GONE
                eventsAdapter.events.clear()
                eventsAdapter.events.addAll(it.take(5))
                eventsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun addEvent() {
        with(binding) {
            cardViewSleep.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_sleepTrackerActivity)
            }
            cardViewWater.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_waterTrackerActivity)
            }
            cardViewEat.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_calorieTrackerActivity)
            }

            tvSeeMoreHistory.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_historyActivity)
            }

            binding.imgNotifiHome.setOnClickListener {
                // go to noti activity
                // 0 type all, 1 water, 2 sleep, 3 eat
                val action = HomeFragmentDirections.actionHomeFragmentToManagerNotification(0)
                findNavController().navigate(action)
            }
            view6.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_todayTarget)
            }
            view4.setOnClickListener {
                val url = "${Constant.BASE_URL}/fitnessweb/aboutUs"
                val intent = Intent(requireActivity(), WebViewActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        }

    }

    override fun initView() {
        mImageDrawable = binding.imgToScroll.drawable as ClipDrawable
        mImageDrawable.level = 5000

        initCalender()

        initRecycleViewHistory()
        dailyWater = loginPreferences.getString(Constant.PREF.WATER_INNEED, "2000").toString()
        binding.textView17.text = "$dailyWater ml"

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
//        entries.add(PieEntry(70f))
//        entries.add(PieEntry(30f))

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
                binding.tvDay1.setTextColor(Color.RED)
            }

            arrayDay[1] -> {
                binding.tvDay2.setTextColor(Color.RED)
            }

            arrayDay[2] -> {
                binding.tvDay3.setTextColor(Color.RED)
            }

            arrayDay[3] -> {
                binding.tvDay4.setTextColor(Color.RED)
            }

            arrayDay[4] -> {
                binding.tvDay5.setTextColor(Color.RED)
            }

            arrayDay[5] -> {
                binding.tvDay6.setTextColor(Color.RED)
            }

            arrayDay[6] -> {
                binding.tvDay7.setTextColor(Color.RED)
            }
        }
    }
}