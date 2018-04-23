package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.activity.details.model.HistoryModel
import java.util.*

class HistoryGraphView : View {
    private val paint = Paint()
    private val legendPaint = Paint()
    var model: HistoryModel = HistoryModel(emptyArray(), Date(), Date(), "")

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        paint.isAntiAlias = true
        paint.color = context.getColor(R.color.colorAccent)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 5f

        legendPaint.isAntiAlias = true
        legendPaint.color = context.getColor(R.color.legend_white)
        legendPaint.style = Paint.Style.STROKE
        legendPaint.strokeJoin = Paint.Join.ROUND
        legendPaint.strokeWidth = 3f
        legendPaint.textSize = 30f
    }

    override fun onDraw(canvas: Canvas) {
        val itemWidth = canvas.width/model.historyItems.size.toFloat()
        val maxValue = model.historyItems.maxBy { it.value }?.value ?: 1f
        val minValue = model.historyItems.minBy { it.value }?.value ?: 0f

        val heightPadding = (canvas.height / 7)
        val scale = (canvas.height - 2 * heightPadding)/(maxValue - minValue)

        var previousHeight = canvas.height.toFloat()/2
        val maxHeight = canvas.height - heightPadding
        val maxMeasuredHeight = maxHeight - scale * (maxValue - minValue)

        canvas.drawLine(0f, maxHeight.toFloat(), canvas.width.toFloat(), maxHeight.toFloat(), legendPaint)
        canvas.drawLine(0f, maxMeasuredHeight, canvas.width.toFloat(), maxMeasuredHeight, legendPaint)
        canvas.drawText(maxValue.toString() + model.unit, 10f, maxMeasuredHeight - 10, legendPaint)
        canvas.drawText(minValue.toString() + model.unit, 10f, maxHeight.toFloat() + 40, legendPaint)
        canvas.drawText(model.endTime.toString(), 10f, canvas.height-5f, legendPaint)
        //Quick fix, doesnt center properly, need to measure text width then remove it from canvas width
        canvas.drawText(model.startTime.toString(), canvas.width/2 + 10f, canvas.height-5f, legendPaint)

        model.historyItems.forEachIndexed { index, historyItem ->
            val startX = index*itemWidth
            val nextHeight = maxHeight - scale * (historyItem.value - minValue)

            canvas.drawLine(startX, previousHeight, startX+itemWidth, nextHeight, paint)
            previousHeight = nextHeight
        }
    }
}