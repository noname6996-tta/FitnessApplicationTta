package com.tta.fitnessapplication.view.activity.tracker.watertracker.waterHistory

import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
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
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.titleFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.titleSameYearFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.data.utils.getColorCompat
import com.tta.fitnessapplication.data.utils.makeInVisible
import com.tta.fitnessapplication.data.utils.makeVisible
import com.tta.fitnessapplication.data.utils.setTextColorRes
import com.tta.fitnessapplication.databinding.ActivityWaterHistoryBinding
import com.tta.fitnessapplication.databinding.Example3CalendarDayBinding
import com.tta.fitnessapplication.databinding.Example3CalendarHeaderBinding
import com.tta.fitnessapplication.view.activity.history.HistoryAdapter
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.activity.tracker.watertracker.WaterViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class WaterHistoryActivity : BaseFragment<ActivityWaterHistoryBinding>() {
    private lateinit var waterViewModel: WaterViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private val eventsAdapter = HistoryAdapter()
    private var selectedDate: LocalDate? = null
    private val events = mutableMapOf<LocalDate, List<History>>()
    override fun getDataBinding(): ActivityWaterHistoryBinding {
        return ActivityWaterHistoryBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        waterViewModel = ViewModelProvider(this)[WaterViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        historyViewModel.readAllData.observe(viewLifecycleOwner) {
            val list = ArrayList<History>()
            for (item in it) {
                if (item.type==1){
                    val history = History(
                        null,
                        idUser.toInt(),
                        item.date,
                        item.time,
                        item.activity,
                        item.type!!.toInt(),
                        item.value
                    )
                    list.add(history)
                }
            }
            if (list != null) {
                eventsAdapter.events.clear()
                eventsAdapter.events.addAll(list)
                eventsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun initView() {
        super.initView()
        binding.exThreeRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
        }

        binding.exThreeCalendar.monthScrollListener = {
            binding.topAppBar.title = if (it.yearMonth.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }
            // Select the first day of the visible month.
            selectDate(it.yearMonth.atDay(today.dayOfMonth))
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
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
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
        eventsAdapter.apply {
            events.clear()
            events.addAll(this@WaterHistoryActivity.events[date].orEmpty())
            notifyDataSetChanged()
        }
        binding.exThreeSelectedDateText.text = "Day: " + fullDateFormatter.format(date)
        waterViewModel.getWaterListByDate(fullDateFormatter.format(date))
    }

    override fun onStart() {
        super.onStart()
        binding.topAppBar.setBackgroundColor(
            requireContext().getColorCompat(R.color.text),
        )
    }

    override fun onStop() {
        super.onStop()
        binding.topAppBar.setBackgroundColor(
            requireContext().getColorCompat(R.color.text),
        )
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
                            dotView.isVisible = events[data.date].orEmpty().isNotEmpty()
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