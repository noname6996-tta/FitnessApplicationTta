package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModel
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModelFactory
import com.tta.fitnessapplication.view.activity.SleepTracker.SleepTrackerActivity
import com.tta.fitnessapplication.view.activity.login.LoginActivity
import com.tta.fitnessapplication.view.activity.watertracker.WaterTrackerActivity

class HomeFragment : Fragment() {
    private lateinit var mImageDrawable: ClipDrawable
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addObsever()
        addDataProfile()
        initView()
        initProcess()
        addEvent()
    }

    private fun addDataProfile() {
        val repositoryApi = RepositoryApi()
        val viewModelFactory = MainViewModelFactory(repositoryApi)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        loginPreferences = requireActivity().getSharedPreferences(
            Constant.LOGIN_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
        loginPrefsEditor = loginPreferences.edit();
        var token = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        if (token !=""){
            mainViewModel.getUserData(token)
        }
        mainViewModel.dataExercise.observe(requireActivity()) {
            if (it.isSuccessful) {
                var dataProfile = mainViewModel.dataExercise.value?.body()?.data
                Log.e("dataProfile",dataProfile.toString())
                binding.textView2.text = "${dataProfile!![0].firstname} ${dataProfile!![0].lastname}"
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
    }

    private fun addObsever() {
        val viewModel = HistoryViewModelGoogleData()
        viewModel.getData()
        viewModel.listStepsCount.observe(viewLifecycleOwner) {
            binding.tvHomeStep.text = it.last().value + " steps"
        }
        viewModel.listWeight.observe(viewLifecycleOwner) {

        }
        viewModel.listCaloriesExpended.observe(viewLifecycleOwner) {
            binding.tvCalor.text = it.last().value + "Calr"
        }
        viewModel.listHeartMinutes.observe(viewLifecycleOwner) {
            binding.tvHeartRate.text = it.last().value + " BPM"
        }
        viewModel.listSleepTracker.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.textView27.text = it.last().value + " h"
            }
            else {
                binding.textView27.text = "No data"
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addEvent() {
        binding.cardViewSleep.setOnClickListener {
            startActivity(Intent(activity, SleepTrackerActivity::class.java))
        }
        binding.cardViewWater.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    WaterTrackerActivity::class.java
                )
            )
        }
    }

    private fun initProcess() {
        mImageDrawable = binding.imgToScroll.drawable as ClipDrawable
        mImageDrawable.level = 5000

    }

    private fun initView() {
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


}