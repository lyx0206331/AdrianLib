package com.adrian.test

import com.adrian.commlib.util.decodeBase64
import com.adrian.commlib.util.encodeBase64
import com.adrian.commlib.util.encryptMD5
import com.adrian.commlib.util.verifyBase64
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
    fun testEncode() {
        val value = "大发发大发发"
        val md5 = value.encodeToByteArray().encryptMD5()
        val encodeBase64 = md5.encodeBase64()
        val decodeBase64 = encodeBase64.encodeToByteArray().decodeBase64()
        println("value:$value, md5:${md5.contentToString()}, enBase64:$encodeBase64, deBase64:${decodeBase64.contentToString()}")
    }

    @Test
    fun testBase64() {
        val value = "咳咳咳咳咳大在在在在国工「 中"
        val encode = value.encodeToByteArray().encodeBase64()
        val result = "5ZKz5ZKz5ZKz5ZKz5ZKz5aSn5Zyo5Zyo5Zyo5Zyo5Zu95bel44CMIOS4rQ=="
        print("value:$value, encode:$encode, decode:${encode.encodeToByteArray().decodeBase64().decodeToString()}, verify:${result.verifyBase64(value)}")
    }
}