package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.databinding.FragmentSearchFoodBinding
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemPopularFoodAdapter
import com.tta.fitnessapplication.view.base.BaseFragment


class SearchFood  : BaseFragment<FragmentSearchFoodBinding>(){
    private val mealPopular = ItemPopularFoodAdapter()
    override fun getDataBinding(): FragmentSearchFoodBinding {
        return FragmentSearchFoodBinding.inflate(layoutInflater)
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

    override fun addEvent() {
        super.addEvent()
        binding.viewback.setOnClickListener { findNavController().popBackStack() }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Đoạn mã xử lý trước khi text thay đổi
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Đoạn mã xử lý khi text thay đổi
            }

            override fun afterTextChanged(editable: Editable) {
                // Đoạn mã xử lý sau khi text thay đổi
                mainViewModel.searchFood(editable.toString().trim())
            }
        })
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listSearchFood.observe(viewLifecycleOwner){
            if (it.body()?.response == 1){
                mealPopular.setListMeal(it.body()!!.data, requireContext())
            }
        }
    }
}