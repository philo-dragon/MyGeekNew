package com.jojo.design.common_ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.pfl.lib_common.R


/**
 *    desc  : 自定义加载圆形等待View
*/
class LoadingCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mWidth = 0f
    private var mPadding = 0f
    private var startAngle = 0f
    private lateinit var mPaint: Paint
    private var bgColor = Color.argb(100, 245, 245, 245)//加载框背景色
    private var barColor = Color.argb(100, 245, 245, 245)//加载框动画色

    private var valueAnimator: ValueAnimator? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleDialogLoading)
        bgColor = a.getColor(R.styleable.CircleDialogLoading_bgColor, bgColor)
        barColor = a.getColor(R.styleable.CircleDialogLoading_barColor, barColor)
        a.recycle()
        initPaint()
        startAnim()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measuredWidth > height)
            mWidth = measuredHeight.toFloat()
        else
            mWidth = measuredWidth.toFloat()
        mPadding = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = bgColor
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint!!)
        mPaint.color = barColor
        @SuppressLint("DrawAllocation")
        val rectF = RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding)
        //第四个参数是否显示半径
        canvas.drawArc(rectF, startAngle, 100f, false, mPaint!!)
    }


    private fun initPaint() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 4f
    }

    private fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 1000)
    }

    fun stopAnim() {
        if (valueAnimator != null) {
            clearAnimation()
            valueAnimator!!.repeatCount = 1
            valueAnimator!!.cancel()
            valueAnimator!!.end()
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long): ValueAnimator {
        valueAnimator = ValueAnimator.ofFloat(startF, endF)
        valueAnimator?.apply {
            duration = time
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE//无限循环
            repeatMode = ValueAnimator.RESTART//

            addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                startAngle = 360 * value
                //重新绘制
                invalidate()
            }
            if (!isRunning) {
               start()
            }
        }
        return valueAnimator as ValueAnimator
    }
}
