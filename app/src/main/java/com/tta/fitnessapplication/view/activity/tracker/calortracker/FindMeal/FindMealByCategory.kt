package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import androidx.navigation.fragment.navArgs
import com.tta.fitnessapplication.databinding.FragmentMealBycategoryBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class FindMealByCategory : BaseFragment<FragmentMealBycategoryBinding>(){
    private val args : FindMealByCategoryArgs by navArgs()
    override fun getDataBinding(): FragmentMealBycategoryBinding {
        return FragmentMealBycategoryBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        val id = args.id
        if (id != null){
            mainViewModel.getFoodByIdCategory(id)
        }
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listFoodById.observe(viewLifecycleOwner) {

        }
    }
}