package com.tta.fitnessapplication.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.tta.fitnessapplication.R

class VerticalProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Int = 0
    private val backgroundPaint = Paint()
    private val progressPaint = Paint()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalProgressBar)
        progress = typedArray.getInt(R.styleable.VerticalProgressBar_progress, 0)
        typedArray.recycle()

        backgroundPaint.color = Color.TRANSPARENT
        progressPaint.color = ContextCompat.getColor(context, R.color.text)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = getWidth()
        val height = getHeight()

        val backgroundHeight = height
        val progressHeight = (height.toFloat() / 100) * progress

        canvas.drawRect(0F, 0F, width.toFloat(), backgroundHeight.toFloat(), backgroundPaint)
        canvas.drawRect(0F, (height - progressHeight), width.toFloat(), height.toFloat(), progressPaint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }
}
