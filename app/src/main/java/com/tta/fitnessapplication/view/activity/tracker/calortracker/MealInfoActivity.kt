package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.databinding.ActivityMealInfoBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class MealInfoActivity : BaseFragment<ActivityMealInfoBinding>() {
    val args: MealInfoActivityArgs by navArgs()
    var idMeal = 0
    override fun getDataBinding(): ActivityMealInfoBinding {
        return ActivityMealInfoBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        idMeal = args.id
        mainViewModel.getFoodById(idMeal.toString())
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listFoodById.observe(viewLifecycleOwner){
            setData(it[0])
        }
    }

    private fun setData(food : Food){
        binding.apply {
            Glide.with(requireContext())
                .load(food.image)
                .error(R.drawable.ic_breafast)
                .into(imageView9)
            tvMealName.text = food.name
            textView72.text = "by ${food.author}"
            textView72.text = "by ${food.author}"
            tvMealDescriptions.text = "${food.descriptions}"
            tvIngredients.text = "${food.ingredients}"
            tvSteps.text = "${food.steps}"
            recNutrition.text = "${food.nutrition}"
            tvLink.setOnClickListener {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra("url", food.video)
                startActivity(intent)
            }
        }
    }
}