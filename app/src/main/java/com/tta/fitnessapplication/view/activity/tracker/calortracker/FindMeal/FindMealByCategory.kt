package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.databinding.FragmentMealBycategoryBinding
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemPopularFoodAdapter
import com.tta.fitnessapplication.view.base.BaseFragment

class FindMealByCategory : BaseFragment<FragmentMealBycategoryBinding>() {
    private val args: FindMealByCategoryArgs by navArgs()
    private val mealPopular = ItemPopularFoodAdapter()
    override fun getDataBinding(): FragmentMealBycategoryBinding {
        return FragmentMealBycategoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        binding.apply {
            recCategory.adapter = mealPopular
            val layoutManagerPopularFood = LinearLayoutManager(requireContext())
            layoutManagerPopularFood.orientation = LinearLayoutManager.VERTICAL
            recCategory.layoutManager = layoutManagerPopularFood
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        val id = args.id
        if (id != null) {
            mainViewModel.getFoodByIdCategory(id)
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            viewback.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        mealPopular.findSomethingToEat {
            val action = FindMealByCategoryDirections.actionFindMealByCategoryToMealInfoActivity(it)
            findNavController().navigate(action)
        }
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listFoodById.observe(viewLifecycleOwner) {
            mealPopular.setListMeal(it, requireContext())
        }
    }
}