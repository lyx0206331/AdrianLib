package com.adrian.commlib

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getPhoneModel() {
//        "PhoneModel".logE("model:${getPhoneModel()}")
        assertEquals("Mix2", getSystemModel())
    }

    @Test
    fun testIsMacString() {
        assertEquals(true, "0a:bb:23:dA:34:fa".isMacString())
    }
}