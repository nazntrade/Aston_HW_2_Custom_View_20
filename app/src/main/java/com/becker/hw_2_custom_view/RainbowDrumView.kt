package com.becker.hw_2_custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class RainbowDrumView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var customSize = 50

    private val colors = listOf(
        Color.RED,
        Color.rgb(255, 165, 0),  // orange
        Color.YELLOW,
        Color.GREEN,
        Color.BLUE,
        Color.rgb(75, 0, 130),   // Violet
        Color.rgb(0, 255, 255)   // light blue
    )

    private val labels = listOf(
        "red color",
        "image 1",
        "yellow color",
        "image 2",
        "blue color",
        "image 3",
        "light blue color"
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var angle: Float = 0f
    private val drumRect = RectF()
    private var selectedColorIndex = -1
    private var resultText = ""
    private var listener: CustomViewListener? = null

    fun setCustomSize(outerSize: Int) {
        customSize = outerSize
        requestLayout()
    }

    fun setCustomViewListener(listener: CustomViewListener) {
        this.listener = listener
    }

    fun removeCustomViewListener() {
        this.listener = null
    }

    private fun sendData(data: String) {
        listener?.onDataReceived(data)
    }

    init {
        setOnClickListener {
            startSpinning()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 500 + customSize * 2
        val desiredHeight = 500 + customSize * 2

        val width = measureDimension(desiredWidth, widthMeasureSpec)
        val height = measureDimension(desiredHeight, heightMeasureSpec)

        val size = width.coerceAtMost(height)
        setMeasuredDimension(size, size)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = centerX.coerceAtMost(centerY) * 0.9f

        angle = 360f / colors.size

        drumRect.set(
            (centerX - radius),
            (centerY - radius),
            (centerX + radius),
            (centerY + radius)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)

        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> desiredSize.coerceAtMost(size)
            MeasureSpec.UNSPECIFIED -> desiredSize
            else -> desiredSize
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for ((index, color) in colors.withIndex()) {
            paint.color = color
            canvas.drawArc(
                drumRect,
                index * angle,
                angle,
                true,
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            startSpinning()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun startSpinning() {
        selectedColorIndex = -1
        resultText = ""
        val tempAngle = ((333..555).random() * (2..5).random() + 90).toFloat()

        animate().rotationBy(tempAngle)
            .setDuration((3000..6000).random().toLong())
            .withEndAction {
                stopSpinning()
            }
            .start()
    }

    private fun stopSpinning() {
        val degrees = (rotation % 360 + 360) % 360
        val normalizedAngle = 360 - degrees
        val sector = (normalizedAngle / (360 / colors.size))
        selectedColorIndex = (sector % colors.size).toInt()
        resultText = labels[selectedColorIndex]
        println(resultText)
        sendData(resultText)
        invalidate()
    }
}
