package phamf.com.chemicalapp.CustomAnimation

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

import java.util.ArrayList
import java.util.Arrays

class FadedInAnim : Animation {

    private var listener: FadedInAnim.FadedInAnimListener? = null

    private val views = ArrayList<View>()

    constructor(vararg views: View) {
        this.views.addAll(Arrays.asList(*views))
    }

    constructor(views: List<View>) {
        this.views.addAll(views)
    }

    fun setOnAnimEndListener(listener: FadedInAnim.FadedInAnimListener) {
        this.listener = listener
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        for (view in views) {
            view.alpha = interpolatedTime
        }

        if (interpolatedTime >= 1) {
            if (listener != null) listener!!.onEnd()
        }

        super.applyTransformation(interpolatedTime, t)
    }


    override fun start() {
        super.start()
        if (listener != null) listener!!.onStart()
    }


    interface FadedInAnimListener {

        fun onStart()

        fun onEnd()
    }
}
