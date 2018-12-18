package phamf.com.chemicalapp.Manager

import android.app.Activity
import android.content.Intent

import phamf.com.chemicalapp.Service.FloatingSearchIconService

// Create quick search view which floating on screen
class FloatingSearchViewManager(internal var activity: Activity) {

    fun init() {
        activity.startService(Intent(activity, FloatingSearchIconService::class.java))
    }
}
