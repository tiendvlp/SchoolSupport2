package phamf.com.chemicalapp.CustomView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import phamf.com.chemicalapp.CustomView.CustomViewModel.Point
import phamf.com.chemicalapp.Manager.FontManager
import phamf.com.chemicalapp.Supporter.UnitConverter
import phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel
import java.lang.Exception
import java.math.BigDecimal

class AddMoreGraphDrawer : View {

    var start_X_value : Float = 0.0f

    var end_X_value : Float = 0.0f

    var step : BigDecimal = BigDecimal("0.05")

    var calc_content : String = ""

    var processer : ValueProcesser? = null

    var prev_point : Point = Point()

    var graph_paint : Paint = Paint()

    var zoom_index : Int = 50

    var indentify_x_points = ArrayList<Double>()

    constructor(context: Context) : super(context) {
        graph_paint.strokeWidth = DpToPixel(3).toFloat()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        graph_paint.strokeWidth = DpToPixel(3).toFloat()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun draw () {
        invalidate()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (processer == null) return

        prev_point.x = start_X_value * zoom_index + width/2

        prev_point.y = height/2 - processer!!.process(start_X_value) * zoom_index

        // It's inside loop, it just run once each time onDraw is called
        var x_value = BigDecimal(start_X_value.toString())

        x_value += step

        var errorHappened = false

        var bigdecimal_end_x_value = BigDecimal(end_X_value.toString())

        // x_value <= end_x value
        while (x_value.compareTo(bigdecimal_end_x_value) <= 0) {

            try {

                Log.e("x val", x_value.toString())
                var y = height/2 - processer!!.process(x_value.toFloat()) * zoom_index
                Log.e("It still run: ", x_value.toString())
                if (y.isNaN()) throw NotANumberException()

                var x = x_value.toFloat() * zoom_index + width/2

                if (indentify_x_points.size > 0) {
                    for (error_x_point in indentify_x_points)
                        if (x_value.toDouble() == error_x_point) throw Exception("Math Error Exception")
                }

                if (!errorHappened) {
                    canvas!!.drawLine(prev_point.x, prev_point.y, x, y, graph_paint)
                }

                else {

                    var prev_point_y = height/2 - processer!!.process(prev_point.x) * zoom_index
                    prev_point.x = prev_point.x * zoom_index + width/2
                    canvas!!.drawLine(prev_point.x, prev_point_y, x, y, graph_paint)
                    errorHappened = false

                }

                prev_point.x = x

                prev_point.y = y

                x_value += step

            } catch (e : Exception) {
                e.printStackTrace()
                Log.e("ERROR HAPPEN", x_value.toString() + " is Error")
                errorHappened = true
                // Next x value is stored in prev to start new line after an error happen
                x_value += step
                if (x_value.compareTo(bigdecimal_end_x_value) <= 0)

                prev_point.x = x_value.toFloat()

                x_value += step
            }

        }


    }

    interface ValueProcesser {
        fun process (x : Float) : Float
    }

    class NullValueProcesserException : Exception () {
        override val message: String = "You must implement a processer to process Y value from X value"
    }

    class MathErrorOf_HamSoHuuTiBac1 : Exception() {
        override val message: String?
            get() = "Divide 0"
    }

    class NotANumberException : Exception() {
        override val message: String?
            get() = "Not a number"
    }
}
