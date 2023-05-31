package com.tta.fitnessapplication.view.activity.MainActivity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navHostFragment : NavHostFragment
    private lateinit var binding : ActivityMainBinding
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addListener()
        initUi()
    }

    private fun addListener() {
        val repositoryApi = RepositoryApi()
        val viewModelFactory = MainViewModelFactory(repositoryApi)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        loginPreferences = this.getSharedPreferences(
            Constant.LOGIN_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
        loginPrefsEditor = loginPreferences.edit();
        var token = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        if (token!=""){
            viewModel.getUserData(token)
        }
        viewModel.dataExercise.observe(this) {
            if (it.isSuccessful) {
                Log.e("tta", it.body().toString())
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
    }

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
}