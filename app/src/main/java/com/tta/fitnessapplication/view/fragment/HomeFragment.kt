package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.content.IntentSender
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Value
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import com.google.android.gms.fitness.result.DataSourcesResult
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.activity.SleepTrackerActivity
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment(), OnDataPointListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener  {
    private val REQUEST_OAUTH = 1
    private val AUTH_PENDING = "auth_state_pending"
    private var authInProgress = false
    private lateinit var mApiClient: GoogleApiClient
    private lateinit var mImageDrawable: ClipDrawable
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING)
        }
        mApiClient = GoogleApiClient.Builder(requireActivity())
            .addApi(Fitness.SENSORS_API)
            .addScope(Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        initView()
        initProcess()
        addEvent()
    }

    private fun addEvent() {
        binding.cardViewSleep.setOnClickListener {
            startActivity(Intent(activity,SleepTrackerActivity::class.java))
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

    override fun onStart() {
        super.onStart()
        mApiClient?.connect()
    }

    override fun onDataPoint(dataPoint: DataPoint?) {
        for (field in dataPoint?.dataType!!.fields) {
            val value: Value = dataPoint.getValue(field)
            activity?.runOnUiThread {
//                Toast.makeText(
//                    requireContext(),
//                    "Field: " + field.name.toString() + " Value: " + value,
//                    Toast.LENGTH_SHORT
//                ).show()
                binding.tvHomeStep.text = "$value steps"
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        val dataSourceRequest = DataSourcesRequest.Builder()
            .setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .setDataSourceTypes(DataSource.TYPE_RAW)
            .build()
        val dataSourcesResultCallback: ResultCallback<DataSourcesResult?> =
            ResultCallback<DataSourcesResult?> { dataSourcesResult ->
                for (dataSource in dataSourcesResult.dataSources) {
                    if (DataType.TYPE_STEP_COUNT_CUMULATIVE.equals(dataSource.dataType)) {
                        registerFitnessDataListener(
                            dataSource,
                            DataType.TYPE_STEP_COUNT_CUMULATIVE
                        )
                    }
                }
            }
        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
            .setResultCallback(dataSourcesResultCallback)
    }

    private fun registerFitnessDataListener(dataSource: DataSource, dataType: DataType) {
        val request = SensorRequest.Builder()
            .setDataSource(dataSource)
            .setDataType(dataType)
            .setSamplingRate(3, TimeUnit.SECONDS)
            .build()

        Fitness.SensorsApi.add(mApiClient, request, this)
            .setResultCallback { status ->
                if (status.isSuccess) {
                    Log.e("GoogleFit", "SensorApi successfully added")
                }
            }
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true
                connectionResult.startResolutionForResult(activity, REQUEST_OAUTH)
            } catch (e: IntentSender.SendIntentException) {
            }
        } else {
            Log.e("GoogleFit", "authInProgress")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (!mApiClient!!.isConnecting && !mApiClient!!.isConnected) {
                    mApiClient!!.connect()
                }
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Log.e("GoogleFit", "RESULT_CANCELED")
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth")
        }
    }

    override fun onStop() {
        super.onStop()
        Fitness.SensorsApi.remove(mApiClient, this)
            .setResultCallback { status ->
                if (status.isSuccess) {
                    mApiClient!!.disconnect()
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(AUTH_PENDING, authInProgress)
    }
}