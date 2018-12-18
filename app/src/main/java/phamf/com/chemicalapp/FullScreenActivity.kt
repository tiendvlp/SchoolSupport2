package phamf.com.chemicalapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import phamf.com.chemicalapp.Manager.FullScreenManager

open class FullScreenActivity : AppCompatActivity() {


    private var fullScreenManager: FullScreenManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        makeFullScreen()
    }

    private fun setUp() {
        fullScreenManager = FullScreenManager(this)
    }

    fun makeFullScreen() {
        fullScreenManager!!.hideNavAndStatusBar()
    }

    fun makeFullScreenAfter(milis: Long) {
        fullScreenManager!!.hideNavAndStatusBar_After(milis)
    }

}
