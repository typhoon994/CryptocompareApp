package com.rbt.cryptocompare.cryptocompareapp.activity.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.rbt.cryptocompare.cryptocompareapp.R
import com.rbt.cryptocompare.cryptocompareapp.domain.model.CoinHistory
import java.util.*

class HistoryGraphView : View {
    private val paint = Paint()
    private val legendPaint = Paint()
    var coinHistory: CoinHistory = CoinHistory(emptyArray(), Date(), Date(), "")

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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
        val itemWidth = width/coinHistory.historyItems.size.toFloat()
        val maxValue = coinHistory.historyItems.maxByOrNull { it.value }?.value ?: 1f
        val minValue = coinHistory.historyItems.minByOrNull { it.value }?.value ?: 0f

        val heightPadding = (height / 7)
        val scale = (height - 2 * heightPadding) / (maxValue - minValue)

        var previousHeight = height.toFloat()/2
        val maxHeight = height - heightPadding
        val maxMeasuredHeight = maxHeight - scale * (maxValue - minValue)

        canvas.drawLine(0f, maxHeight.toFloat(), width.toFloat(), maxHeight.toFloat(), legendPaint)
        canvas.drawLine(0f, maxMeasuredHeight, width.toFloat(), maxMeasuredHeight, legendPaint)
        canvas.drawText(maxValue.toString() + coinHistory.unit, 10f, maxMeasuredHeight - 10, legendPaint)
        canvas.drawText(minValue.toString() + coinHistory.unit, 10f, maxHeight.toFloat() + 40, legendPaint)
        canvas.drawText(coinHistory.endTime.toString(), 10f, height-5f, legendPaint)
        //Quick fix, doesnt center properly, need to measure text width then remove it from canvas width
        canvas.drawText(coinHistory.startTime.toString(), width/2 + 10f, height-5f, legendPaint)

        coinHistory.historyItems.forEachIndexed { index, historyItem ->
            val startX = index*itemWidth
            val nextHeight = maxHeight - scale * (historyItem.value - minValue)

            canvas.drawLine(startX, previousHeight, startX+itemWidth, nextHeight, paint)
            previousHeight = nextHeight
        }
    }
}