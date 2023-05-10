package com.tta.fitnessapplication.view.activity.doexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tta.fitnessapplication.R
import androidx.navigation.ui.setupActionBarWithNavController
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.databinding.ActivityDoExerciseBinding

class DoExerciseActivity : AppCompatActivity() {
    companion object {
        var listExercise = ArrayList<Exercise>()
        var numberExercise = 0
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityDoExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // get data form listExercise
        val list = intent.getParcelableArrayListExtra<Exercise>("listExercise")
        if (list != null) {
            listExercise = list
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }
}