package com.tta.fitnessapplication.data.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.yearMonth
import com.tta.fitnessapplication.R
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics,
    ).toInt()

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

internal val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int): Drawable =
    requireNotNull(ContextCompat.getDrawable(this, drawable))

internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))


fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}


fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}


fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}


fun getWeekPageTitle(week: Week): String {
    val firstDate = week.days.first().date
    val lastDate = week.days.last().date
    return when {
        firstDate.yearMonth == lastDate.yearMonth -> {
            firstDate.yearMonth.displayText()
        }

        firstDate.year == lastDate.year -> {
            "${firstDate.month.displayText(short = false)} - ${lastDate.yearMonth.displayText()}"
        }

        else -> {
            "${firstDate.yearMonth.displayText()} - ${lastDate.yearMonth.displayText()}"
        }
    }
}

fun showAnimatedAlertDialog(context: Context, title: String, message: String) {
    val alertDialog = AlertDialog.Builder(context)
        .create()

    val customLayout: View = context.layoutInflater.inflate(R.layout.alert_dialog, null)
    alertDialog.setView(customLayout)

     val btnClose = customLayout.findViewById<Button>(R.id.appCompatButton2)
     btnClose.setOnClickListener {
         alertDialog.dismiss()
     }

     val textView66 = customLayout.findViewById<TextView>(R.id.textView66)
     textView66.text = title

     val tvMessage = customLayout.findViewById<TextView>(R.id.tvMessage)
     tvMessage.text = message

    alertDialog.setOnShowListener {
        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        alertDialog.window?.decorView?.startAnimation(fadeInAnimation)
    }

    alertDialog.show()
}

fun Fragment.hideKeyboard() {
    if (requireActivity().currentFocus != null) {
        val inputMethodManager = requireActivity().getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
    }
}

fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("hh:mm:ss")
    return dateFormat.format(calendar.time)
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

fun formatLocalDate(localDate: LocalDate): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return localDate.format(formatter)
}

fun formatDateToTimeString(date: Date): String {
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return format.format(date)
}

fun formatDateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(date)
}

fun getWeekDates(): List<String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dates = mutableListOf<String>()

    // Set the calendar to the start of the week
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

    for (i in 0 until 7) {
        val date = calendar.time
        val formattedDate = dateFormat.format(date)
        dates.add(formattedDate)

        // Increment the calendar to the next day
        calendar.add(Calendar.DAY_OF_WEEK, 1)
    }

    return dates
}


fun convertToDecimalTime(hours: Int, minutes: Int): Double {
    return hours + (minutes.toDouble() / 60)
}

fun getTimeValue(): Int {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

    return when {
        currentHour in 6..11 -> 1 // Morning (6 am to 11 am)
        currentHour in 12..17 -> 2 // Afternoon (12 pm to 5 pm)
        else -> 3 // Night (6 pm to 5:59 am)
    }
}
