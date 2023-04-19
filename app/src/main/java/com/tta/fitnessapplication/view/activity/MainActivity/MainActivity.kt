package com.tta.fitnessapplication.view.activity.MainActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationBarView
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.databinding.ActivityMainBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navHostFragment : NavHostFragment
    private lateinit var binding : ActivityMainBinding
    private lateinit var account : GoogleSignInAccount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        addListener()
        initUi()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build()
//
//            val googleSignInClient = GoogleSignIn.getClient(this, signInOptions)
//
//            val signInIntent: Intent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, 0)
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == 0) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account1: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
//            Log.w("tta", account1.account.toString())
//            // Signed in successfully, show authenticated UI.
//            val fitnessOptions = FitnessOptions.builder()
//                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
//                .build()
//
//            account = GoogleSignIn.getAccountForExtension(this, fitnessOptions)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                addDataFit()
//            }
//            settingReadData()
//            showAccountSetting()
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("tta", "signInResult:failed code=" + e.message)
//            Log.w("tta", "signInResult:failed code=" + e.printStackTrace())
//            Log.w("tta", "signInResult:failed code=" + e.stackTraceToString())
//        }
//    }
//
//    private fun addListener() {
//        val repositoryApi = RepositoryApi()
//        val viewModelFactory = MainViewModelFactory(repositoryApi)
//        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
//        viewModel.getData()
//        viewModel.dataExercise.observe(this) {
//            if (it.isSuccessful){
//
//            } else {
//                Log.e("tta",it.errorBody().toString())
//            }
//        }
//    }

    private fun initUi() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frameMain) as NavHostFragment
        navController = navHostFragment.findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.discoverFragment, R.id.historyFragment, R.id.settingFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }

        val mNavigationItemSelected = object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.homeFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }
                    R.id.discoverFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }
                    R.id.historyFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }
                    R.id.settingFragment -> {
                        NavigationUI.onNavDestinationSelected(
                            item,
                            navController
                        )
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.apply {
            setOnItemSelectedListener(mNavigationItemSelected)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

    private fun settingReadData() {
        Fitness.getRecordingClient(
            this,
            account
        )
            // This example shows subscribing to a DataType, across all possible data
            // sources. Alternatively, a specific DataSource can be used.
            .subscribe(DataType.TYPE_STEP_COUNT_DELTA)
            .addOnSuccessListener {
                Log.i("tta", "Successfully subscribed!")
            }
            .addOnFailureListener { e ->
                Log.w("tta", "There was a problem subscribing.", e)
            }
    }

    private fun showAccountSetting() {
        Fitness.getRecordingClient(
            this,
            account
        )
            .listSubscriptions()
            .addOnSuccessListener { subscriptions ->
                for (sc in subscriptions) {
                    val dt = sc.dataType
                    Log.i("tta", "Active subscription for data type: ${dt?.name}")
                }
            }
    }

    private fun getHistoryClient() {


    }

    private fun DataPoint.getStartTimeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getStartTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    private fun DataPoint.getEndTimeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochSecond(this.getEndTime(TimeUnit.SECONDS))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addDataFit() {
        // Read the data that's been collected throughout the past week.
        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val startTime = endTime.minusWeeks(1)
        Log.i("tta", "Range Start: $startTime")
        Log.i("tta", "Range End: $endTime")

        val readRequest =
            DataReadRequest.Builder()
                // The data request can specify multiple data types to return,
                // effectively combining multiple data queries into one call.
                // This example demonstrates aggregating only one data type.
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be
                // aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession allows
                // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()

        Fitness.getHistoryClient(
            this,
            account
        )
            .readData(readRequest)
            .addOnSuccessListener { response ->
                // The aggregate query puts datasets into buckets, so flatten into a
                // single list of datasets
                for (dataSet in response.buckets.flatMap { it.dataSets }) {
                    dumpDataSet(dataSet)
                }
            }
            .addOnFailureListener { e ->
                Log.w("tta", "There was an error reading data from Google Fit", e)
            }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i("tta", "Data returned for Data type: ${dataSet.dataType.name}")
        for (dp in dataSet.dataPoints) {
            Log.i("tta", "Data point:")
            Log.i("tta", "\tType: ${dp.dataType.name}")
            Log.i("tta", "\tStart: ${dp.getStartTimeString()}")
            Log.i("tta", "\tEnd: ${dp.getEndTimeString()}")
            for (field in dp.dataType.fields) {
                Log.i("tta", "\tField: ${field.name.toString()} Value: ${dp.getValue(field)}")
            }
        }
    }
}