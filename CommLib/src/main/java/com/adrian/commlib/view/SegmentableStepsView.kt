package com.adrian.commlib.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.annotation.Nullable
import com.adrian.commlib.R
import com.adrian.commlib.util.logE
import kotlin.math.max
import kotlin.math.min

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
 * Description: 可分段步骤进度控件
 */

class SegmentableStepsView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        // 环形
        const val STYLE_RING = 0

        // 横线
        const val STYLE_LINE_HORIZONTAL = 1

        // 竖线
        const val STYLE_LINE_VERTICAL = 2

        // 圆形
        const val STYLE_CIRCLE = 3

        // 弧形
        const val STYLE_ARC = 4

        @Retention(AnnotationRetention.SOURCE)
        @IntDef(STYLE_RING, STYLE_CIRCLE, STYLE_LINE_HORIZONTAL, STYLE_LINE_VERTICAL, STYLE_ARC)
        annotation class StepStyle
    }

    //最大步骤数
    var maxSteps = 5
        set(value) {
            field = if (value < 1) 1 else value
            invalidate()
        }

    //当前步骤数
    var stepIndex = 3
        set(value) {
            field = if (value < 0) 0 else value
            invalidate()
        }

    //各个步骤片段颜色数组
    var colorsArray: IntArray =
        intArrayOf(Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA)
        set(value) {
            field = value
            invalidate()
        }

    //步骤背景颜色
    var stepBgColor = Color.GRAY
        set(value) {
            field = value
            invalidate()
        }

    //圆环时中间部分颜色
    var stepRingCenterColor = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    //步骤起止片段圆角度数
    var stepCornerRadius = 5f
        set(value) {
            field = value
            invalidate()
        }

    //环形或者圆形时外半径
    var stepOutsideRadius = 10f
        set(value) {
            field = value
            stepInsideRadius = if (field > stepStrokeWidth) field - stepStrokeWidth else field
            invalidate()
        }

    //环形内半径
    private var stepInsideRadius = 5f

    //宽度
    var stepStrokeWidth = 5f
        set(value) {
            field = if (value > 0) value else 1f
            invalidate()
        }

    //步骤样式:环形、水平、垂直、圆形、扇形
    @StepStyle
    var stepStyle = STYLE_LINE_HORIZONTAL
        set(value) {
            field = value
            invalidate()
        }

    private val progressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SegmentableStepsView)?.also {
            stepCornerRadius =
                it.getDimension(R.styleable.SegmentableStepsView_step_corner_radius, 10f)
            maxSteps = it.getInt(R.styleable.SegmentableStepsView_max_steps, 5)
            stepBgColor =
                it.getColor(R.styleable.SegmentableStepsView_step_background_color, Color.GRAY)
            stepRingCenterColor =
                it.getColor(R.styleable.SegmentableStepsView_step_ring_center_color, Color.WHITE)
            stepOutsideRadius = it.getDimension(R.styleable.SegmentableStepsView_step_outside_radius, 10f)
            stepIndex = it.getInt(R.styleable.SegmentableStepsView_step_index, 3)
            it.getResourceId(R.styleable.SegmentableStepsView_step_colors_array, 0)
                .takeIf { arrayId ->
                    arrayId > 0
                }?.apply {
                    context.resources.getIntArray(this).also { residArray ->
                        val colors = IntArray(residArray.size)
                        residArray.forEachIndexed { index, color ->
                            colors[index] = color
                        }
                        colorsArray = colors
                    }
                }
            stepStyle = it.getInt(R.styleable.SegmentableStepsView_step_style, STYLE_RING)
            stepStrokeWidth =
                it.getDimension(R.styleable.SegmentableStepsView_step_stroke_width, 5f)
        }.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getWidthSize(widthMeasureSpec), getHeightSize(heightMeasureSpec))
    }

    private fun getWidthSize(measureSpec: Int): Int {
        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                val strokeW = when (stepStyle) {
                    STYLE_LINE_HORIZONTAL -> 0
                    STYLE_LINE_VERTICAL -> stepStrokeWidth
                    STYLE_RING -> stepOutsideRadius * 2
                    else -> 0
                }
                paddingStart + paddingEnd + strokeW.toInt()
            }
            MeasureSpec.UNSPECIFIED -> {
                max(suggestedMinimumWidth, specSize)
            }
            else -> suggestedMinimumWidth
        }
    }

    private fun getHeightSize(measureSpec: Int): Int {
        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                val strokeH = when (stepStyle) {
                    STYLE_LINE_HORIZONTAL -> stepStrokeWidth
                    STYLE_LINE_VERTICAL -> 0
                    STYLE_RING -> stepOutsideRadius * 2
                    else -> 0
                }
                paddingTop + paddingBottom + strokeH.toInt()
            }
            MeasureSpec.UNSPECIFIED -> {
                max(suggestedMinimumHeight, specSize)
            }
            else -> suggestedMinimumHeight
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        val bl = when (stepStyle) {
//            STYLE_LINE_HORIZONTAL -> paddingLeft
//            STYLE_LINE_VERTICAL -> paddingLeft
//            STYLE_RING -> paddingStart + stepOutsideRadius
//            else -> 0f
//        }
//        val bt = when (stepStyle) {
//            STYLE_LINE_HORIZONTAL -> paddingTop
//            STYLE_LINE_VERTICAL -> paddingTop
//            STYLE_RING -> paddingTop + stepOutsideRadius
//            else -> 0f
//        }
//        val br = when (stepStyle) {
//            STYLE_LINE_HORIZONTAL -> width.toFloat() - paddingRight
//            STYLE_LINE_VERTICAL -> stepStrokeWidth + paddingLeft
//            else -> 0f
//        }
//        val bb = when (stepStyle) {
//            STYLE_LINE_HORIZONTAL -> stepStrokeWidth + paddingTop
//            STYLE_LINE_VERTICAL -> height.toFloat() - paddingBottom
//            else -> 0f
//        }
        when (stepStyle) {
            STYLE_LINE_HORIZONTAL, STYLE_LINE_VERTICAL -> {
                canvas?.drawRect(
                    paddingStart.toFloat(),
                    paddingTop.toFloat(),
                    if (stepStyle == STYLE_LINE_HORIZONTAL) width.toFloat() - paddingRight else stepStrokeWidth + paddingLeft,
                    if (stepStyle == STYLE_LINE_HORIZONTAL) stepStrokeWidth + paddingTop else height.toFloat() - paddingBottom,
                    progressPaint.also {
                        it.color = stepBgColor
                        it.strokeWidth = stepStrokeWidth
                    })
            }
            else -> {
                val cx = paddingStart + stepOutsideRadius
                val cy = paddingTop + stepOutsideRadius
                canvas?.drawCircle(
                    cx, cy, stepOutsideRadius, progressPaint.also {
                        it.color = stepBgColor
                    })
                canvas?.drawCircle(
                    cx, cy, stepInsideRadius, progressPaint.also { it.color = stepRingCenterColor }
                )
            }
        }

        if (stepIndex > 0) {
            stepIndex = min(stepIndex, maxSteps)
            val validW = width.toFloat() - paddingLeft - paddingRight
            val validH = height.toFloat() - paddingTop - paddingBottom
            for (index in 1..stepIndex) {
                val color = if (index <= colorsArray.size) {
                    colorsArray[index - 1]
                } else {
                    colorsArray.last()
                }
//                val l = when (stepStyle) {
//                    STYLE_LINE_HORIZONTAL -> (index - 1f) / maxSteps * validW + paddingLeft
//                    STYLE_LINE_VERTICAL -> paddingLeft
//                    else -> 0f
//                }
//                val t = when (stepStyle) {
//                    STYLE_LINE_HORIZONTAL -> paddingTop
//                    STYLE_LINE_VERTICAL -> validH * (maxSteps - index) / maxSteps + paddingTop
//                    else -> 0f
//                }
//                val r = when (stepStyle) {
//                    STYLE_LINE_HORIZONTAL -> 1f * index / maxSteps * validW + paddingLeft
//                    STYLE_LINE_VERTICAL -> stepStrokeWidth + paddingLeft
//                    else -> 0f
//                }
//                val b = when (stepStyle) {
//                    STYLE_LINE_HORIZONTAL -> stepStrokeWidth + paddingTop
//                    STYLE_LINE_VERTICAL -> validH * (maxSteps - index + 1) / maxSteps + paddingTop
//                    else -> 0f
//                }
                when (stepStyle) {
                    STYLE_LINE_HORIZONTAL, STYLE_LINE_VERTICAL -> {
                        canvas?.drawRect(
                            if (stepStyle == STYLE_LINE_HORIZONTAL) (index - 1f) / maxSteps * validW + paddingLeft else paddingStart.toFloat(),
                            if (stepStyle == STYLE_LINE_HORIZONTAL) paddingTop.toFloat() else validH * (maxSteps - index) / maxSteps + paddingTop,
                            if (stepStyle == STYLE_LINE_HORIZONTAL) 1f * index / maxSteps * validW + paddingLeft else stepStrokeWidth + paddingLeft,
                            if (stepStyle == STYLE_LINE_HORIZONTAL) stepStrokeWidth + paddingTop else validH * (maxSteps - index + 1) / maxSteps + paddingTop,
                            progressPaint.also { it.color = color })
                    }
                    else -> {
                        val cx = paddingStart + stepOutsideRadius
                        val cy = paddingTop + stepOutsideRadius
                        canvas?.drawArc(paddingStart.toFloat(), paddingTop.toFloat(), paddingStart+stepOutsideRadius*2, paddingTop+stepOutsideRadius*2,
                            (index - 1f) / maxSteps * 360 - 90, 1f*index/maxSteps*360 - 90, true, progressPaint.also {
                                it.color = color
                                it.strokeWidth = stepStrokeWidth
                            })
                        canvas?.drawCircle(
                            cx,
                            cy,
                            stepInsideRadius,
                            progressPaint.also {
                                it.color = stepRingCenterColor
                                it.strokeWidth = 0f
                            }
                        )
                    }
                }

            }
        }
    }

    private fun getRadius(lt: Float = 0f, rt: Float = 0f, rb: Float = 0f, lb: Float = 0f) =
        floatArrayOf(lt, lt, rt, rt, rb, rb, lb, lb)
}