package com.adrian.commlib.util

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.io.File

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
 * date:2020/12/15 0015 14:04
 * description:
 */
object ImageUtil {

    /**
     * 此方法调用后可完成布局，并通过测量可获取宽高
     */
    fun layoutView(v: View, width: Int, height: Int) {
        v.layout(0, 0, width, height)
        val measureWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val measureHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST)
        v.measure(measureWidth, measureHeight)
        //measure后不会改变View的实际大小，需要layout进行重布局
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
    }

    private fun loadBmpFromView(v: View): Bitmap = v.let {
        val w = it.width
        val h = it.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        //不设置则为透明
        c.drawColor(Color.WHITE)

        v.layout(0, 0, w, h)
        v.draw(c)
        bmp
    }

    fun save2sdcard(v: View, dirPath: String, fileName: String): String {
        return File(dirPath).apply { mkdirs() }.run {
            File(this, "$fileName").let {
                if (it.exists()) {
                    it.delete()
                    File(this, "$fileName")
                } else {
                    it
                }
            }
        }.let { file ->
            file.outputStream().use { fos ->
                loadBmpFromView(v).let { bmp ->
                    bmp.compress(
                        if (fileName.endsWith(".png")) Bitmap.CompressFormat.PNG else Bitmap.CompressFormat.JPEG,
                        90,
                        fos
                    )
                }
                fos.flush()
            }
            v.destroyDrawingCache()
            file.absolutePath
        }
    }

    fun Drawable.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        draw(Canvas(bitmap))
        return bitmap
//        return toBitmap(intrinsicWidth, intrinsicHeight, if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
    }

    fun Bitmap.toDrawable(res: Resources) = BitmapDrawable(res, this)

    fun Bitmap.toByteArray() = ByteArrayOutputStream().also {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
    }.toByteArray()

    fun ByteArray.toBitmap() = if (this.isNotEmpty()) BitmapFactory.decodeByteArray(this, 0, size) else null

    fun Bitmap.toCircle() = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).let {
        Canvas(it).also { canvas ->
            Paint(Paint.ANTI_ALIAS_FLAG).also { paint ->
                paint.color = Color.WHITE
                canvas.drawARGB(0, 0 , 0, 0)
                val rect = Rect(0, 0, width, height)
                canvas.drawOval(RectF(rect), paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(this, rect, rect, paint)
            }
        }
        it
    }
}