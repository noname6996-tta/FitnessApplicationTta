package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.getCurrentDate
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.data.utils.getTimeValue
import com.tta.fitnessapplication.data.utils.getWeekDates
import com.tta.fitnessapplication.databinding.ActivityCalorTrackerBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemFindSomethingToEatAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemTodayMealAdapter
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.noti.NotificationViewModel

class CalorieTrackerActivity : BaseFragment<ActivityCalorTrackerBinding>() {
    private var mealEnum = MealEnum.Breakfast
    private lateinit var viewModel: HistoryViewModel
    private lateinit var mealViewModel: MealViewModel
    private val mealAdapter = ItemTodayMealAdapter()
    private val somethingToEatAdapter = ItemFindSomethingToEatAdapter()
    private lateinit var viewModelNotificationViewModel: NotificationViewModel
    private val listMeal = ArrayList<Meal>()
    private val listMealFinal = ArrayList<Meal>()
    var today = 0
    override fun getDataBinding(): ActivityCalorTrackerBinding {
        return ActivityCalorTrackerBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            val listDataChart = arrayOf(0, 0, 0, 0, 0, 0, 0)
            for (item in it) {
                if (item.type == 4) {
                    today += item.value!!.toInt()
                    for (i in 0 until getWeekDates().size) {
                        if (getWeekDates()[i] == item.date) {
                            listDataChart[i] = item.value!!.toInt() + listDataChart[i]
                            listDataChart[i] = listDataChart[i]
                        }
                    }
                    binding.chart.viewColume1.setProgress((listDataChart[0] / 26).toInt())
                    binding.chart.viewColume2.setProgress((listDataChart[1] / 26).toInt())
                    binding.chart.viewColume3.setProgress((listDataChart[2] / 26).toInt())
                    binding.chart.viewColume4.setProgress((listDataChart[3] / 26).toInt())
                    binding.chart.viewColume5.setProgress((listDataChart[4] / 26).toInt())
                    binding.chart.viewColume6.setProgress((listDataChart[5] / 26).toInt())
                    binding.chart.viewColume7.setProgress((listDataChart[6] / 26).toInt())
                }
            }
            binding.tvYourHaveEat.text = "and you have eat ${today} calo"
        }
        viewModelNotificationViewModel.getCompletedTask()
            .observe(viewLifecycleOwner) { listCompletedTask ->
                listMeal.clear()
                for (item in listCompletedTask) { listMeal.add(
                    Meal(item.taskInfo.priority,
                            item.taskInfo.foodName,
                            item.taskInfo.category,
                            item.taskInfo.detail,
                            item.taskInfo.time,
                            item.taskInfo.image,
                            item.taskInfo.status,
                            item.taskInfo.id
                        )
                    )
                }
                viewModelNotificationViewModel.getUncompletedTask().observe(viewLifecycleOwner) {
                    for (item in it) {
                        if (formatDateToString(item.taskInfo.date) == getCurrentDate())
                            listMeal.add(
                                Meal(
                                    item.taskInfo.priority,
                                    item.taskInfo.foodName,
                                    item.taskInfo.category,
                                    item.taskInfo.detail,
                                    item.taskInfo.time,
                                    item.taskInfo.image,
                                    item.taskInfo.status,
                                    item.taskInfo.id
                                )
                            )
                    }
                    listMealFinal.clear()
                    listMealFinal.addAll(listMeal)
                    if (listMealFinal.isNotEmpty()) {
                        binding.recMeal.visibility = View.VISIBLE
                        mealAdapter.setListMeal(listMealFinal, requireContext())
                    }
                }
            }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        viewModelNotificationViewModel = (activity as MainActivity).viewModelNoti
    }

    override fun initView() {
        super.initView()
        binding.apply {
            var caloDaily = loginPreferences.getString(Constant.PREF.CALO_INNEED, "2000").toString()
            tvYourDailyTarget.text = "Your daily target is $caloDaily calo"
            val mealArray = resources.getStringArray(R.array.Meal)
            val adapterActivityLevel =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mealArray)
            binding.recSuggestFood.adapter = somethingToEatAdapter
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.recSuggestFood.layoutManager = linearLayoutManager

            binding.recMeal.adapter = mealAdapter
            val linearLayoutManagerVertical = LinearLayoutManager(requireContext())
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
            chart.tvValueY0.text = "calo"
            chart.tvValueY1.text = "1800"
            chart.tvValueY2.text = "2000"
            chart.tvValueY3.text = "2200"
            chart.tvValueY4.text = "2400"
            chart.tvValueY5.text = "2600"
        }

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
            somethingToEatAdapter.setListExercise(listSomething, requireContext())
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.view22.setOnClickListener {
            val action =
                CalorieTrackerActivityDirections.actionCalorieTrackerActivityToHistoryActivity()
            findNavController().navigate(action)
        }

        binding.btnAddSomethingToEat.setOnClickListener {
            val action =
                CalorieTrackerActivityDirections.actionCalorieTrackerActivityToFindMealActivity(
                    getTimeValue()
                )
            findNavController().navigate(action)
        }
        mealAdapter.findSomethingToEat {
            val action =
                CalorieTrackerActivityDirections.actionCalorieTrackerActivityToMealInfoActivity(it)
            findNavController().navigate(action)
        }
        mealAdapter.updateData { id ->
            viewModelNotificationViewModel.getUncompletedTask().observe(viewLifecycleOwner) {
                for (item in it) {
                    if (item.taskInfo.id == id) {
                        item.taskInfo.status = true
                        viewModelNotificationViewModel.updateTaskStatus(item.taskInfo)
                        mealAdapter.notifyDataSetChanged()
                        // add to history
                        val history = History(
                            null,
                            null,
                            Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                            getCurrentTime(),
                            item.taskInfo?.description,
                            4,
                            item.taskInfo?.category
                        )
                        viewModel.addHistory(history)
                    }
                }
            }
        }
        somethingToEatAdapter.findSomethingToEat {
            val action =
                CalorieTrackerActivityDirections.actionCalorieTrackerActivityToFindMealActivity(it)
            findNavController().navigate(action)
        }
        binding.cardViewHistoryWaterTracker.setOnClickListener {
            findNavController().navigate(R.id.action_calorieTrackerActivity_to_caloCalculateFragment)
        }
        binding.cardViewHistoryMeal.setOnClickListener {
            findNavController().navigate(R.id.action_calorieTrackerActivity_to_historyMeal)
        }
        binding.cardViewManagerNotification.setOnClickListener {
            findNavController().navigate(R.id.action_calorieTrackerActivity_to_managerNotiMeal)
        }
    }

    enum class MealEnum {
        Breakfast,
        Lunch,
        Dinner
    }
}

