package com.tta.fitnessapplication.view.activity.watertracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.data.utils.showAnimatedAlertDialog
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.view.activity.HistoryActivity.HistoryViewModel
import com.tta.fitnessapplication.view.activity.watertracker.waterHistory.WaterHistoryActivity
import com.tta.fitnessapplication.view.activity.watertracker.watercaculate.WaterCaculateActivity

class WaterTrackerActivity : AppCompatActivity() {
    private var _binding: ActivityWaterTrackerBinding? = null
    private val binding get() = _binding!!
    private var viewModel = HistoryViewModel()
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var idUser: String
    private var drink = 0
    // just for now
    private val dailyWater = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPreferences = getSharedPreferences(Constant.LOGIN_PREFS, MODE_PRIVATE)
        idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        addObserver()
        initUi()
        addEvent()
    }

    private fun addObserver() {
        viewModel.getListHistoryByDateAndTypr(idUser, fullDateFormatter.format(today), "1")
        viewModel.message.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.isDone.observe(this){
            if (it){
                viewModel.getListHistoryByDateAndTypr(idUser, fullDateFormatter.format(today), "1")
                showAnimatedAlertDialog(this,"Successful","Good jobs my friend")
            } else {
                showAnimatedAlertDialog(this,"Notification","Oh some thing went wrong!")
            }
        }
        viewModel.list.observe(this) {
            var value = 0
            if (it != null) {
                for (i in 0 until it.size) {
                    value += it[i].value!!.toInt()
                }
                binding.textView60.text = "$value ml"
                var percent : Float = ((value / dailyWater * 100).toFloat())
                binding.tvPercentDrink.text = "$percent%"
                binding.seekBar.progress = percent.toInt()
            }
        }
    }

    private fun initUi() {
        binding.seekBar.max = 100
        binding.seekBar.isEnabled = false

    }

    private fun addEvent() {
        binding.view13.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
        binding.cardViewWaterCaculate.setOnClickListener {
            startActivity(Intent(this, WaterCaculateActivity::class.java))
        }
        binding.cardViewHistoryWaterTracker.setOnClickListener {
            startActivity(Intent(this, WaterHistoryActivity::class.java))
        }
        binding.imgCup100ml.setOnClickListener {
            drink = 100
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup125ml.setOnClickListener {
            drink = 125
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup150ml.setOnClickListener {
            drink = 150
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup200ml.setOnClickListener {
            drink = 200
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup250ml.setOnClickListener {
            drink = 250
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup300ml.setOnClickListener {
            drink = 300
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup350ml.setOnClickListener {
            drink = 350
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCup400ml.setOnClickListener {
            drink = 400
            binding.appCompatButton.text = "+$drink ml"
        }
        binding.imgCupCustomize.setOnClickListener {
            drink = 500
            binding.appCompatButton.text = "+$drink ml"
        }

        binding.appCompatButton.setOnClickListener {
            viewModel.createHistory(
                idUser,
                fullDateFormatter.format(today),
                getCurrentTime(),
                "Drink water",
                "1",
                drink.toString()
            )
        }
    }
}