package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.CategoryFood
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.databinding.ActivityMealTrackerBinding
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.CategoryMealAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemRecommendMealAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemTodayMealAdapter
import com.tta.fitnessapplication.view.base.BaseFragment


class FindMealActivity : BaseFragment<ActivityMealTrackerBinding>() {
    val categoryAdapter = CategoryMealAdapter()
    val recommendMealAdapter = ItemRecommendMealAdapter()
    val mealPopular = ItemTodayMealAdapter()
    val listRecommentMeal = ArrayList<Meal>()
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
    }

    override fun initView() {
        super.initView()
        with(binding){
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
        mainViewModel.getCategoryFood()
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listCategory.observe(viewLifecycleOwner){
            val list = ArrayList<CategoryFood>()
            list.addAll(it)
            categoryAdapter.setListCategoryFood(it,requireContext())
        }
        listRecommentMeal.add(Meal(1,"buger","350cal","ssss",1,""))
        recommendMealAdapter.setListExercise(listRecommentMeal,requireContext())
        mealPopular.setListExercise(listRecommentMeal,requireContext())

    }
}