package com.adrian.commlib.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.adrian.commlib.R

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
class SegmentableProgressView(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0): View(context, attrs, defStyleAttr, defStyleRes) {
    init {
        context.obtainStyledAttributes(attrs, R.styleable.SegmentableProgressView)?.also {
            val segmentCount = it.getInt(R.styleable.SegmentableProgressView_segment_count, 1)
            val segmentIndex = it.getInt(R.styleable.SegmentableProgressView_segment_index, 0)
            val colorsArray = context.resources.getIntArray(it.getResourceId(R.styleable.SegmentableProgressView_segment_colors_array, 0))
        }
    }
}