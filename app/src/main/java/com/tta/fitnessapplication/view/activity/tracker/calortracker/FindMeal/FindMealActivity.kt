package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.CategoryFood
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivityMealTrackerBinding
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.CategoryMealAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemPopularFoodAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemRecommendFoodAdapter
import com.tta.fitnessapplication.view.base.BaseFragment


class FindMealActivity : BaseFragment<ActivityMealTrackerBinding>() {
    val args: FindMealActivityArgs by navArgs()
    var time = 0
    val categoryAdapter = CategoryMealAdapter()
    val recommendMealAdapter = ItemRecommendFoodAdapter()
    val mealPopular = ItemPopularFoodAdapter()
    val listFood = ArrayList<Food>()
    override fun getDataBinding(): ActivityMealTrackerBinding {
        return ActivityMealTrackerBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewback.setOnClickListener {
            findNavController().popBackStack()
        }

        recommendMealAdapter.findSomethingToEat {
            val action = FindMealActivityDirections.actionFindMealActivityToMealInfoActivity(it)
            findNavController().navigate(action)
        }

        mealPopular.findSomethingToEat {
            val action = FindMealActivityDirections.actionFindMealActivityToMealInfoActivity(it)
            findNavController().navigate(action)
        }

        categoryAdapter.findSomethingToEat {
            val action = FindMealActivityDirections.actionFindMealActivityToFindMealByCategory(it)
            findNavController().navigate(action)
        }
    }

    override fun initView() {
        super.initView()
        with(binding) {
            when (time) {
                1 -> textView25.text = "Breakfast"
                2 -> textView25.text = "Lunch"
                3 -> textView25.text = "Dinner"
            }
            recCategory.adapter = categoryAdapter
            val layoutManagerCategory = LinearLayoutManager(requireContext())
            layoutManagerCategory.orientation = LinearLayoutManager.HORIZONTAL
            recCategory.layoutManager = layoutManagerCategory

            recRecommned.adapter = recommendMealAdapter
            val layoutManagerRecommned = LinearLayoutManager(requireContext())
            layoutManagerRecommned.orientation = LinearLayoutManager.HORIZONTAL
            recRecommned.layoutManager = layoutManagerRecommned

            recPopularFood.adapter = mealPopular
            val layoutManagerPopularFood = LinearLayoutManager(requireContext())
            layoutManagerPopularFood.orientation = LinearLayoutManager.VERTICAL
            recPopularFood.layoutManager = layoutManagerPopularFood
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        time = args.time
        var progess = loginPreferences.getString(Constant.PREF.PROCESS_USER, "0").toString()
        if (progess != "0") {
            mainViewModel.getSuggestFood(progess.toInt())
        }
        showLoading()
        mainViewModel.getCategoryFood()
        mainViewModel.getSuggestFood(1)
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listCategory.observe(viewLifecycleOwner) {
            val list = ArrayList<CategoryFood>()
            list.addAll(it)
            categoryAdapter.setListCategoryFood(it, requireContext())
            hideLoading()
        }

        mainViewModel.listFoodSuggest.observe(viewLifecycleOwner) {
            listFood.clear()
            listFood.addAll(it)
            recommendMealAdapter.setListExercise(listFood.take(5), requireContext())
            mealPopular.setListMeal(listFood.takeLast(5), requireContext())
        }
    }
}