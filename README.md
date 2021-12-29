[![](https://jitpack.io/v/lyx0206331/AdrianLib.svg)](https://jitpack.io/#lyx0206331/AdrianLib)

### 包含功能(持续更新中...)：
 - 安卓常用扩展(AndroidExt.kt)
 - 常用数据转换(CommExt.kt)
 - Excel工具(ExcelUtil)
 - View常用扩展(ViewExt.kt)
 - 全局异常捕获(UncaughtExceptionUtil.kt)
 - 图片转换工具(ImageUtil)
 - 自定义基于SoundPool的简易播放器(SoundPlayer.kt)

更新BaseActivity.kt文件

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.lyx0206331:AdrianLib:0.0.1'
	}
