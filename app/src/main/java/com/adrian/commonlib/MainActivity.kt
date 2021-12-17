package com.adrian.commonlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.adrian.commlib.util.*
import com.adrian.commlib.view.SegmentableStepsView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isAdd = true

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
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                "Step".logE("current progress:$progress")
                segmentableStepsView.stepIndex = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        segmentableStepsView.maxSteps = seekbar.max
        segmentableStepsView.stepIndex = seekbar.progress
        segmentableStepsView.stepChangeListener = { step ->
            "Step".logE("current step:$step")
            if (step == 0) {
                segmentableStepsView.ringCenterText = "步骤(0)"
            } else {
                if (isAdd) {
                    segmentableStepsView.ringCenterText += "步骤(${step})"
                } else {
                    val string = segmentableStepsView.ringCenterText
                    val index = string?.indexOf("步骤(${step+1})".also { "Data".logE("delete content:$it") }).also { "Data".logE("delete index:$it") }
                    if (index!! > 0) {
                        segmentableStepsView.ringCenterText = string?.substring(0, index)
                    }
                }
            }
        }
        btnIncrease.setOnClickListener {
            isAdd = true
            seekbar.progress++
        }
        btnDecrease.setOnClickListener {
            isAdd = false
            seekbar.progress--
        }
        seekbar.progress = 4

        val fValue = 1.56f
        val fBytes = byteArrayOf(20, -82, -57, 63)
        tvFloat2BytesLE.text = "LE:Float -> Bytes:$fValue -> ${fValue.toByteArrayLE().contentToString()}"
        tvBytes2FloatLE.text = "LE:Bytes -> Float:${fBytes.contentToString()} -> ${fBytes.toFloatLE()}"
        tvFloat2BytesBE.text = "BE:Float -> Bytes:$fValue -> ${fValue.toByteArrayBE().contentToString()}"
        fBytes.reverse()
        tvBytes2FloatBE.text = "BE:Bytes -> Float:${fBytes.contentToString()} -> ${fBytes.toFloatBE()}"
        val dValue = 1.56
        val dBytes = byteArrayOf(-10, 40, 92, -113, -62, -11, -8, 63)
        tvDouble2BytesLE.text = "LE:Double -> Bytes:$dValue -> ${dValue.toByteArrayLE().contentToString()}"
        tvBytes2DoubleLE.text = "LE:Bytes -> Double:${dBytes.contentToString()} -> ${dBytes.toDoubleLE()}"
        tvDouble2BytesBE.text = "BE:Double -> Bytes:$dValue -> ${dValue.toByteArrayBE().contentToString()}"
        dBytes.reverse()
        tvBytes2DoubleBE.text = "BE:Bytes -> Double:${dBytes.contentToString()} -> ${dBytes.toDoubleBE()}"
    }
}