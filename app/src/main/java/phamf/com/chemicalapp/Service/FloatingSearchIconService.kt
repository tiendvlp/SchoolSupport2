package phamf.com.chemicalapp.Service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton

import phamf.com.chemicalapp.R

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_PHONE
import phamf.com.chemicalapp.MainActivity

class FloatingSearchIconService : Service() {

    private var mWindowManager: WindowManager? = null
    private var parent: View? = null
    internal var deltaX = 0f
    internal var deltaY = 0f
    internal var touchX: Float = 0.toFloat()
    internal var touchY: Float = 0.toFloat()
    private var fl_ib_search: ImageButton? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        parent = LayoutInflater.from(this).inflate(R.layout.floating_search_view, null, false)
        val params = WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                    TYPE_APPLICATION_OVERLAY
                else
                    TYPE_PHONE,
                FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
        params.x = 50
        params.y = 50

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager!!.addView(parent, params)

        addControl()
        addEvent()

        fl_ib_search!!.performClick()
        fl_ib_search!!.setOnTouchListener fl_ib_view@{ v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {


                    deltaX = event.rawX - params.x
                    deltaY = event.rawY - params.y

                    touchX = event.rawX
                    touchY = event.rawY
                }

                MotionEvent.ACTION_UP -> {
                    // If this condition is true means user click icon, not touch
                    if (Math.abs(event.rawX - touchX) < 10 && Math.abs(event.rawY - touchY) < 10) {
                        OnIconClick()
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    val X = event.rawX - deltaX
                    val Y = event.rawY - deltaY
                    params.x = X.toInt()
                    params.y = Y.toInt()
                    mWindowManager!!.updateViewLayout(parent, params)
                    return@fl_ib_view true
                }
            }

            false
        }
    }

    private fun addControl() {
        fl_ib_search = parent!!.findViewById(R.id.fl_ib_search)
    }

    private fun addEvent() {

    }

    private fun OnIconClick() {
        val startSearchIntent = Intent(applicationContext, MainActivity::class.java)
        startSearchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startSearchIntent)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager!!.removeView(parent)
    }
}
