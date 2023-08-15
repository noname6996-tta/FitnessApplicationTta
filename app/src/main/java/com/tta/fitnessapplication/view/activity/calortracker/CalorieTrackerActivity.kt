package com.tta.fitnessapplication.view.activity.calortracker

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityCalorTrackerBinding
import com.tta.fitnessapplication.view.HistoryViewModelGoogleData
import com.tta.fitnessapplication.view.base.BaseActivity
import java.util.Calendar

class CalorieTrackerActivity : BaseActivity<ActivityCalorTrackerBinding>() {
    private var mealEnum = MealEnum.Breakfast
    private var typeMeal = 0
    private lateinit var viewModel: HistoryViewModelGoogleData
    private lateinit var mealViewModel: MealViewModel
    private val mealAdapter = ItemTodayMealAdapter()
    private val somethingToEatAdapter = ItemFindSomethingToEatAdapter()
    override fun getDataBinding(): ActivityCalorTrackerBinding {
        return ActivityCalorTrackerBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listCaloriesExpended.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.tvKcalBurn.text = "No data"
            } else {
                binding.tvKcalBurn.text = it.last().value + "\nBURN"
            }
        }
        mealViewModel.readAllData.observe(this) { meal ->
            if (meal.isNotEmpty()) {
                binding.tvMeal.visibility = View.GONE
                binding.recMeal.visibility = View.VISIBLE
                mealAdapter.setListExercise(meal, this)
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = HistoryViewModelGoogleData()
//        viewModel.getData()
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
    }

    override fun initView() {
        super.initView()
        val mealArray = resources.getStringArray(R.array.Meal)
        val adapterActivityLevel =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, mealArray)
        val balloonProtein = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonProtein!")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        binding.tvProtein.setOnClickListener {
            binding.tvProtein.showAsDropDown(balloonProtein)
        }
        val balloonCarb = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonCarb!")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        binding.tvCarbohydrate.setOnClickListener {
            binding.tvCarbohydrate.showAsDropDown(balloonCarb)
        }

        val balloonFat = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonCarb!")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        binding.tvFat.setOnClickListener {
            binding.tvFat.showAsDropDown(balloonFat)
        }
        binding.recSuggestFood.adapter = somethingToEatAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recSuggestFood.layoutManager = linearLayoutManager

        binding.recMeal.adapter = mealAdapter
        val linearLayoutManagerVertical = LinearLayoutManager(this)
        linearLayoutManagerVertical.orientation = LinearLayoutManager.VERTICAL
        binding.recMeal.layoutManager = linearLayoutManagerVertical
        binding.tvBreakfast.setAdapter(adapterActivityLevel)
        binding.tvBreakfast.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> mealEnum = MealEnum.Breakfast
                    1 -> mealEnum = MealEnum.Lunch
                    2 -> mealEnum = MealEnum.Dinner
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                mealEnum = MealEnum.Breakfast
            }
        }
//        val cal = Calendar.getInstance()
//        when (cal.get(Calendar.HOUR)) {
//            in 0..10 -> {
//                typeMeal = 0
//                binding.tvBreakfast.setText(MealEnum.Breakfast.toString())
//            }
//
//            in 11..15 -> {
//                typeMeal = 1
//                binding.tvBreakfast.setText(MealEnum.Lunch.toString())
//            }
//
//            in 16..24 -> {
//                typeMeal = 2
//                binding.tvBreakfast.setText(MealEnum.Dinner.toString())
//            }
//        }
    }

    override fun initData() {
        super.initData()
        val listSomething = ArrayList<ItemFindSomethingToEatAdapter.SomeThingToEat>()
        val breakfast =
            ItemFindSomethingToEatAdapter.SomeThingToEat("BreakFast", "50+", R.drawable.ic_breafast)
        val lunch =
            ItemFindSomethingToEatAdapter.SomeThingToEat("Lunch", "50+", R.drawable.ic_breafast)
        val dinner =
            ItemFindSomethingToEatAdapter.SomeThingToEat("Dinner", "50+", R.drawable.ic_breafast)
        listSomething.add(breakfast)
        listSomething.add(lunch)
        listSomething.add(dinner)
        listSomething.isNotEmpty().let {
            somethingToEatAdapter.setListExercise(listSomething, this)
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }

        binding.view22.setOnClickListener {

        }
    }

    enum class MealEnum {
        Breakfast,
        Lunch,
        Dinner
    }
}

