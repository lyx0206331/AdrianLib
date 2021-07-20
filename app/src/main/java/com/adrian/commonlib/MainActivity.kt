package com.adrian.commonlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.commlib.getDeviceBrand
import com.adrian.commlib.getSystemModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvModel.text = "型号:${getSystemModel()}\n品牌:${getDeviceBrand()}"
    }
}