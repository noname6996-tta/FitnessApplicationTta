package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.app.Activity
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.noti.CategoryInfo
import com.tta.fitnessapplication.data.model.noti.TaskInfo
import com.tta.fitnessapplication.databinding.ActivityMealInfoBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.noti.NotificationViewModel
import java.util.Date

class MealInfoActivity : BaseFragment<ActivityMealInfoBinding>() {
    private lateinit var viewModelNotificationViewModel: NotificationViewModel
    val args: MealInfoActivityArgs by navArgs()
    var idMeal = 0
    private var categoryInfo = CategoryInfo("tta","#000000")
    private var taskInfo = TaskInfo(
        0,
        "Time to eat ",
        Date(8640000000000000),
        1,
        false,
        "tta",
        "",
        0,
        "",
        ""
    )
    override fun getDataBinding(): ActivityMealInfoBinding {
        return ActivityMealInfoBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        idMeal = args.id
        mainViewModel.getFoodById(idMeal.toString())
        viewModelNotificationViewModel = (activity as MainActivity).viewModelNoti
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listFoodById.observe(viewLifecycleOwner){
            setData(it[0])
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            btnAddSchedule.setOnClickListener {
                AwesomeDialog.build(requireActivity())
                    .title("Notification !")
                    .body("Do you want to remind you to use the dish?")
                    .icon(R.drawable.baseline_fastfood_24)
                    .onPositive("Sure") {
                        var action = MealInfoActivityDirections.actionMealInfoActivityToNotificationActivity(idMeal)
                        findNavController().navigate(action)
                    }
                    .onNegative("No") {
                        // add taskinfo but enable = false
                        viewModelNotificationViewModel.insertTaskAndCategory(taskInfo, categoryInfo)
                        findNavController().popBackStack()
                    }
            }
            view16.setOnClickListener {
                findNavController().popBackStack()
            }
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
            val diff = (Date().time/1000) - 1640908800
            taskInfo.id = diff.toInt()
            taskInfo.description = "Eat " + food.name
            // category == value
            taskInfo.category = food.calo
            // priority == id
            taskInfo.priority = food.id
            taskInfo.status = false
            taskInfo.foodName = food.name
            taskInfo.detail = food.desc
            taskInfo.time = food.type.toInt()
            taskInfo.image = food.image

        }
    }
}