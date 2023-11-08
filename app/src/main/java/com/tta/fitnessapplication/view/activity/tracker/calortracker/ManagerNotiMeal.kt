package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.getColorCompat
import com.tta.fitnessapplication.data.utils.getCurrentDate
import com.tta.fitnessapplication.data.utils.makeInVisible
import com.tta.fitnessapplication.data.utils.makeVisible
import com.tta.fitnessapplication.data.utils.setTextColorRes
import com.tta.fitnessapplication.databinding.Example3CalendarDayBinding
import com.tta.fitnessapplication.databinding.Example3CalendarHeaderBinding
import com.tta.fitnessapplication.databinding.FragmentManagerNotiMealBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemTodayMealAdapter
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.noti.NotificationViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ManagerNotiMeal : BaseFragment<FragmentManagerNotiMealBinding>() {
    private lateinit var viewModelNotificationViewModel: NotificationViewModel
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val fullDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private lateinit var idUser: String
    private val listMeal = ArrayList<Meal>()
    private val listMealFinal = ArrayList<Meal>()
    private val mealAdapter = ItemTodayMealAdapter()
    override fun getDataBinding(): FragmentManagerNotiMealBinding {
        return FragmentManagerNotiMealBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        viewModelNotificationViewModel = (activity as MainActivity).viewModelNoti
    }

    override fun initView() {
        super.initView()
        binding.topAppBar.setBackgroundColor(
            requireContext().getColorCompat(R.color.text),
        )
        binding.topAppBar.title = "History"
        binding.exThreeRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mealAdapter
        }

        binding.exThreeCalendar.monthScrollListener = {
            binding.topAppBar.title = if (it.yearMonth.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }
            // Select the first day of the visible month.
            selectDate(it.yearMonth.atDay(Constant.DATE.today.dayOfMonth))
        }

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(50)
        val endMonth = currentMonth.plusMonths(50)
        configureBinders(daysOfWeek)
        binding.exThreeCalendar.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (binding.root == null) {
            // Show today's events initially.
            binding.exThreeCalendar.post { selectDate(today) }
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
            binding.exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        binding.exThreeSelectedDateText.text = "Day: " + fullDateFormatter.format(date)
        mealAdapter.clearList()
        viewModelNotificationViewModel.getUncompletedTask().observe(viewLifecycleOwner) {

            listMeal.clear()
            for (item in it) {
                Log.e("oooooo",formatDateToString(item.taskInfo.date))
                Log.e("oooooo",fullDateFormatter.format(date))
                if (formatDateToString(item.taskInfo.date) == fullDateFormatter.format(date)) {
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
            }
            listMealFinal.clear()
            listMealFinal.addAll(listMeal)
            if (listMealFinal.isNotEmpty()) {
                mealAdapter.setListMeal(listMealFinal, requireContext())
                mealAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = Example3CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        selectDate(day.date)
                    }
                }
            }
        }
        binding.exThreeCalendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView

                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
                            dotView.makeInVisible()
                        }

                        selectedDate -> {
                            textView.setTextColorRes(R.color.example_3_blue)
                            textView.setBackgroundResource(R.drawable.example_3_selected_bg)
                            dotView.makeInVisible()
                        }

                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                            dotView.isVisible = false
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = Example3CalendarHeaderBinding.bind(view).legendLayout.root
        }
        binding.exThreeCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].name.first().toString()
                                tv.setTextColorRes(R.color.black)
                            }
                    }
                }
            }
    }
}