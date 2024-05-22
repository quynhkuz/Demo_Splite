package com.example.demosplite


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) :
    AppCompatActivity() {


    val binding: B by lazy { bindingFactory(layoutInflater) }
    var myDataBase : MyDBHelper = MyDBHelper(this)

    open fun binding() {
        setContentView(binding.root)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding()

        setStatusBarTransparent(this)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        myDataBase.openDB()
    }

    override fun onStop() {
        super.onStop()
        myDataBase.closeDB()
    }






    open fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun changeColorStatusBar(color: String) {
        val window: Window = window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = false
        // And then you can set any background color to the status bar.
        window.statusBarColor = Color.parseColor(color)
    }


    private fun setStatusBarTransparent(activity: Activity) {

        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        activity.window.decorView.systemUiVisibility = flags
        activity.window.statusBarColor = Color.TRANSPARENT

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false


        //display cutout
//        val attrib = window.attributes
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            attrib.layoutInDisplayCutoutMode =
//                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//        }

    }


//    fun setupFullScreen() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val controller = window.insetsController
//            if (controller != null) {
//                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                controller.systemBarsBehavior =
//                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        }
//    }


}



