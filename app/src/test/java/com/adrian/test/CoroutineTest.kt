package com.adrian.test

import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.*
import org.junit.Test

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
 * author:RanQing
 * date:2021/8/10 0010 14:42
 * description:
 */
class CoroutineTest {

    @Test
    fun testCoroutineScope() = runBlocking {
        launch {
            delay(100)
            log("Task fro runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                log("Task from nested launch")
            }
            delay(100)
            log("Task from coroutine scope")
        }
        log("Coroutine scope is over")
    }

    @Test
    fun testCoroutineImpl() = runBlocking {
        val activity = Activity()
        activity.onCreate()
        delay(1000)
        activity.onDestroy()
        delay(1000)
    }
}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
fun log(msg: Int) = println("[${Thread.currentThread().name}] $msg")

class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    fun onCreate() {
        launch {
            repeat(5) {
//                log("${System.currentTimeMillis()%1000} ms, $it")
                val millis = 200L * it
                delay(millis)
                log("${System.currentTimeMillis()%1000} ms, $it")
            }
        }
        log("${System.currentTimeMillis()%1000} ms，Activity Created")
    }

    fun onDestroy() {
        cancel()
        log("${System.currentTimeMillis()%1000} ms，Activity Destroyed")
    }
}