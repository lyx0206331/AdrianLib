package com.adrian.commonlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.commlib.util.getDeviceBrand
import com.adrian.commlib.util.getSystemModel
import com.adrian.commlib.view.SegmentableStepsView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvModel.text = "型号:${getSystemModel()}\n品牌:${getDeviceBrand()}"

        btnSwitch.setOnClickListener {
            segmentableStepsView.stepStyle = when (segmentableStepsView.stepStyle) {
                SegmentableStepsView.STYLE_LINE_HORIZONTAL -> SegmentableStepsView.STYLE_LINE_VERTICAL
                SegmentableStepsView.STYLE_LINE_VERTICAL -> SegmentableStepsView.STYLE_RING
                SegmentableStepsView.STYLE_RING -> SegmentableStepsView.STYLE_CIRCLE
                else -> SegmentableStepsView.STYLE_LINE_HORIZONTAL
            }
        }
        btnIncrease.text = "递增(${segmentableStepsView.stepIndex})"
        btnIncrease.setOnClickListener {
            if (++segmentableStepsView.stepIndex > segmentableStepsView.maxSteps) {
                segmentableStepsView.stepIndex = 0
            }
            btnIncrease.text = "递增(${segmentableStepsView.stepIndex})"
        }
    }
}