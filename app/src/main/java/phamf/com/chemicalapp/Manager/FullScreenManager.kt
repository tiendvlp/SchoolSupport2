package phamf.com.chemicalapp.Manager

import android.app.Activity
import android.view.View
import android.view.WindowManager

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Hide virtual navigation bar and status bar
class FullScreenManager(private val activity: Activity) {

    fun hideNavAndStatusBar() {
        activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    fun hideNavAndStatusBar_After(timeMilis: Long) {
        val fullscreenService = Executors.newFixedThreadPool(1)
        fullscreenService.execute {
            try {
                Thread.sleep(timeMilis)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            activity.runOnUiThread { this.hideNavAndStatusBar() }
        }
    }
}
