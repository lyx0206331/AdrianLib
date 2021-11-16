package com.adrian.commlib.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.adrian.commlib.R
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

//                       _ooOoo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                       O\ = /O
//                   ____/`---'\____
//                 .   ' \\| |// `.
//                  / \\||| : |||// \
//                / _||||| -:- |||||- \
//                  | | \\\ - /// | |
//                | \_| ''\---/'' | |
//                 \ .-\__ `-` ___/-. /
//              ______`. .' /--.--\ `. . __
//           ."" '< `.___\_<|>_/___.' >'"".
//          | | : `- \`.;`\ _ /`;.`/ - ` : | |
//            \ \ `-. \_ __\ /__ _/ .-` / /
//    ======`-.____`-.___\_____/___.-`____.-'======
//                       `=---='
//
//    .............................................
//             佛祖保佑             永无BUG
/**
 * Author:RanQing
 * Date:2021/11/12 3:04 下午
 * Description:
 */

class SegmentableStepsView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    var maxSteps = 5
        set(value) {
            field = if (value < 1) 1 else value
            invalidate()
        }
    var stepIndex = 3
        set(value) {
            field = if (value < 0) 0 else value
            invalidate()
        }
    var colorsArray: IntArray =
        intArrayOf(Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA)
        set(value) {
            field = value
            invalidate()
        }
    var stepBgColor = Color.GRAY
        set(value) {
            field = value
            invalidate()
        }
    var stepCornerRadius = 5f
        set(value) {
            field = value
            invalidate()
        }
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SegmentableStepsView)?.also {
            stepCornerRadius =
                it.getDimension(R.styleable.SegmentableStepsView_step_corner_radius, 5f)
            maxSteps = it.getInt(R.styleable.SegmentableStepsView_max_steps, 5)
            stepBgColor =
                it.getColor(R.styleable.SegmentableStepsView_step_background_color, Color.GRAY)
            stepIndex = it.getInt(R.styleable.SegmentableStepsView_step_index, 3)
//            context.resources.getIntArray(it.getResourceId(R.styleable.SegmentableStepsView_step_colors_array, 0)).also { residArray ->
//                val colors = IntArray(residArray.size)
//                residArray.forEachIndexed { index, colorRes ->
//                    colors[index] = resources.getColor(colorRes)
//                }
//                colorsArray = colors
//            }
        }.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getSize(widthMeasureSpec), getSize(heightMeasureSpec))
    }

    private fun getSize(measureSpec: Int): Int {
        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                200
            }
            MeasureSpec.AT_MOST -> {
                100.coerceAtMost(specSize)
            }
            MeasureSpec.UNSPECIFIED -> {
                400
            }
            else -> 0
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            progressPaint.also { it.color = stepBgColor })
        if (stepIndex > 0) {
            stepIndex = min(stepIndex, maxSteps)
            for (index in 1..stepIndex) {
                val color = if (index <= colorsArray.size) {
                    colorsArray[index - 1]
                } else {
                    colorsArray.last()
                }
                val l = (index - 1f) / maxSteps * width
                val r = 1f * index / maxSteps * width
                canvas?.drawRect(
                    l,
                    0f,
                    r,
                    height.toFloat(),
                    progressPaint.also { it.color = color })
            }
        }
    }

    private fun getRadius(lt: Float = 0f, rt: Float = 0f, rb: Float = 0f, lb: Float = 0f) =
        floatArrayOf(lt, lt, rt, rt, rb, rb, lb, lb)
}