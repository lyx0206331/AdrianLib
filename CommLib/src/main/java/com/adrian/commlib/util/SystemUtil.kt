package com.adrian.commlib.util

import java.util.*

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
 * date:2021/7/17 0017 18:15
 * description:
 */
class SystemUtil {
}

/**
 * 获取当前手机系统语言.例如：当前设置的是“中文-中国”，则返回“zh-CN”
 */
fun getSystemLanguage(): String = Locale.getDefault().language

/**
 * 获取当前语言列表
 */
fun getSystemLanguageList(): Array<Locale> = Locale.getAvailableLocales()

/**
 * 获取系统版本呈
 */
fun getSystemVersion(): String = android.os.Build.VERSION.RELEASE

/**
 * 获取手机型号
 */
fun getSystemModel(): String? = android.os.Build.MODEL

/**
 * 获取手机厂商
 */
fun getDeviceBrand(): String? = android.os.Build.BRAND