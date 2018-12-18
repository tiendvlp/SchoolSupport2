package phamf.com.chemicalapp.CustomAnimation

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

import java.util.ArrayList
import java.util.Arrays

class FadedOutAnim : Animation {

    private var listener: FadedOutAnimListener? = null


    private val views = ArrayList<View>()


    constructor(vararg views: View) {
        this.views.addAll(Arrays.asList(*views))
    }


    constructor(views: List<View>) {
        this.views.addAll(views)
    }


    fun setOnAnimEndListener(listener: FadedOutAnimListener) {
        this.listener = listener
    }


    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val alpha = 1 - interpolatedTime

        for (view in views) {
            view.alpha = alpha
        }

        if (interpolatedTime >= 1) {
            if (listener != null) listener!!.onEnd()
            for (view in views) {
                view.visibility = View.GONE
            }
        }

        super.applyTransformation(interpolatedTime, t)
    }


    override fun start() {
        super.start()
        if (listener != null) listener!!.onStart()
    }


    interface FadedOutAnimListener {

        fun onStart()

        fun onEnd()
    }
}
