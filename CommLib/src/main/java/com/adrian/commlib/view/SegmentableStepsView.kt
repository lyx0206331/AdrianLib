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
        const val STYLE_CIRCLE =3
        // 弧形
        const val STYLE_ARC = 4

        @Retention(AnnotationRetention.SOURCE)
        @IntDef(STYLE_RING, STYLE_CIRCLE, STYLE_LINE_HORIZONTAL, STYLE_LINE_VERTICAL, STYLE_ARC)
        annotation class StepStyle
    }

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
    var stepStrokeWidth = 10f
        set(value) {
            field = if (value > 0) value else 1f
            invalidate()
        }
    @StepStyle var stepStyle = STYLE_RING

    private val progressPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    } }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SegmentableStepsView)?.also {
            stepCornerRadius =
                it.getDimension(R.styleable.SegmentableStepsView_step_corner_radius, 10f)
            maxSteps = it.getInt(R.styleable.SegmentableStepsView_max_steps, 5)
            stepBgColor =
                it.getColor(R.styleable.SegmentableStepsView_step_background_color, Color.GRAY)
            stepIndex = it.getInt(R.styleable.SegmentableStepsView_step_index, 3)
            it.getResourceId(R.styleable.SegmentableStepsView_step_colors_array, 0).takeIf { arrayId ->
                arrayId > 0}?.apply { context.resources.getIntArray(this).also { residArray ->
                val colors = IntArray(residArray.size)
                residArray.forEachIndexed { index, color ->
                    colors[index] = color
                }
                colorsArray = colors
            } }
            stepStyle = it.getInt(R.styleable.SegmentableStepsView_step_style, STYLE_RING)
            stepStrokeWidth = it.getDimension(R.styleable.SegmentableStepsView_step_stroke_width, 5f)
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
                paddingStart + paddingEnd
            }
            MeasureSpec.UNSPECIFIED -> {
                max(suggestedMinimumWidth, specSize)
            }
            else -> suggestedMinimumWidth
        }.also {
//            stepStrokeWidth = if (stepStyle == STYLE_LINE_HORIZONTAL) height.toFloat() else it.toFloat()
            "Width".logE("smw:$suggestedMinimumWidth width:$it strokeW:$stepStrokeWidth") }
    }

    private fun getHeightSize(measureSpec: Int): Int {
        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.EXACTLY -> {
                specSize
            }
            MeasureSpec.AT_MOST -> {
                paddingTop + paddingBottom
            }
            MeasureSpec.UNSPECIFIED -> {
                max(suggestedMinimumHeight, specSize)
            }
            else -> suggestedMinimumHeight
        }.also {
//            stepStrokeWidth = if (stepStyle == STYLE_LINE_VERTICAL) width.toFloat() else it.toFloat()
            "Height".logE("smh:$suggestedMinimumWidth height:$it strokeW:$stepStrokeWidth") }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bl = when (stepStyle) {
            STYLE_LINE_HORIZONTAL -> 0f
            STYLE_LINE_VERTICAL -> 0f
            else -> 0f
        }
        val bt = when (stepStyle) {
            STYLE_LINE_HORIZONTAL -> 0f
            STYLE_LINE_VERTICAL -> 0f
            else -> 0f
        }
        val br = when (stepStyle) {
            STYLE_LINE_HORIZONTAL -> width.toFloat()
            STYLE_LINE_VERTICAL -> stepStrokeWidth
            else -> 0f
        }
        val bb = when (stepStyle) {
            STYLE_LINE_HORIZONTAL -> stepStrokeWidth
            STYLE_LINE_VERTICAL -> height.toFloat()
            else -> 0f
        }
        canvas?.drawRect(
            bl,
            bt,
            br,
            bb,
            progressPaint.also {
                it.color = stepBgColor
                it.strokeWidth = stepStrokeWidth
            })
        if (stepIndex > 0) {
            stepIndex = min(stepIndex, maxSteps)
            for (index in 1..stepIndex) {
                val color = if (index <= colorsArray.size) {
                    colorsArray[index - 1]
                } else {
                    colorsArray.last()
                }
                val l = when(stepStyle) {
                    STYLE_LINE_HORIZONTAL -> (index - 1f) / maxSteps * width
                    STYLE_LINE_VERTICAL -> 0f
                    else -> 0f
                }
                val t = when(stepStyle) {
                    STYLE_LINE_HORIZONTAL -> 0f
                    STYLE_LINE_VERTICAL -> height * (maxSteps-index)/maxSteps
                    else -> 0f
                }
                val r = when(stepStyle) {
                    STYLE_LINE_HORIZONTAL -> 1f * index / maxSteps * width
                    STYLE_LINE_VERTICAL -> stepStrokeWidth
                    else -> 0f
                }
                val b = when(stepStyle) {
                    STYLE_LINE_HORIZONTAL -> stepStrokeWidth
                    STYLE_LINE_VERTICAL -> height * (maxSteps-index+1)/maxSteps
                    else -> 0f
                }
                canvas?.drawRect(
                    l,
                    t.toFloat(),
                    r,
                    b.toFloat(),
                    progressPaint.also { it.color = color })
            }
        }
    }

    private fun getRadius(lt: Float = 0f, rt: Float = 0f, rb: Float = 0f, lb: Float = 0f) =
        floatArrayOf(lt, lt, rt, rt, rb, rb, lb, lb)
}