package com.adrian.commlib

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import androidx.annotation.LayoutRes
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }

    @LayoutRes abstract fun getLayoutResId(): Int

    protected fun <T:Activity> startActivity(destActivity: Class<T>, bundle: Bundle? = null) {
        startActivity(Intent(this, destActivity), bundle)
    }

    protected fun <T:Activity> startActivityWithInt(destActivity: Class<T>, pair: Pair<String, Int?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithLong(destActivity: Class<T>, pair: Pair<String, Long?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithFloat(destActivity: Class<T>, pair: Pair<String, Float?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithDouble(destActivity: Class<T>, pair: Pair<String, Double?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithShort(destActivity: Class<T>, pair: Pair<String, Short?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithByte(destActivity: Class<T>, pair: Pair<String, Byte?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithChar(destActivity: Class<T>, pair: Pair<String, Char?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithCharSequence(destActivity: Class<T>, pair: Pair<String, CharSequence?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithBoolean(destActivity: Class<T>, pair: Pair<String, Boolean?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithString(destActivity: Class<T>, pair: Pair<String, String?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithParcelable(destActivity: Class<T>, pair: Pair<String, Parcelable?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithParcelableArray(destActivity: Class<T>, pair: Pair<String, Array<Parcelable>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity, L: Parcelable> startActivityWithParcelableList(destActivity: Class<T>, pair: Pair<String, ArrayList<L>?>) {
        startActivity(Intent(this, destActivity).apply {
            putParcelableArrayListExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithSerializable(destActivity: Class<T>, pair: Pair<String, Serializable?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithIntList(destActivity: Class<T>, pair: Pair<String, ArrayList<Int>?>) {
        startActivity(Intent(this, destActivity).apply {
            putIntegerArrayListExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithStringList(destActivity: Class<T>, pair: Pair<String, ArrayList<String>?>) {
        startActivity(Intent(this, destActivity).apply {
            putStringArrayListExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithCharSequenceList(destActivity: Class<T>, pair: Pair<String, ArrayList<CharSequence>?>) {
        startActivity(Intent(this, destActivity).apply {
            putCharSequenceArrayListExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithBooleanArray(destActivity: Class<T>, pair: Pair<String, Array<Boolean>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithByteArray(destActivity: Class<T>, pair: Pair<String, Array<Byte>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithShortArray(destActivity: Class<T>, pair: Pair<String, Array<Short>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithCharArray(destActivity: Class<T>, pair: Pair<String, Array<Char>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithIntArray(destActivity: Class<T>, pair: Pair<String, Array<Int>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithLongArray(destActivity: Class<T>, pair: Pair<String, Array<Long>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithFloatArray(destActivity: Class<T>, pair: Pair<String, Array<Float>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithDoubleArray(destActivity: Class<T>, pair: Pair<String, Array<Double>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithStringArray(destActivity: Class<T>, pair: Pair<String, Array<String>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }

    protected fun <T:Activity> startActivityWithCharSequenceArray(destActivity: Class<T>, pair: Pair<String, Array<CharSequence>?>) {
        startActivity(Intent(this, destActivity).apply {
            putExtra(pair.first, pair.second)
        })
    }
}