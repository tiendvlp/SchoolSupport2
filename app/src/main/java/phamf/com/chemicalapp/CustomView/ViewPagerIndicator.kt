package phamf.com.chemicalapp.CustomView

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

import phamf.com.chemicalapp.CustomView.CustomViewModel.Dot

import java.util.ArrayList

import phamf.com.chemicalapp.Manager.FontManager
import phamf.com.chemicalapp.R


class ViewPagerIndicator : View {

    var dot_count: Int = 0

    var selected_radius: Int = 0

    var distance_between_2_dots: Int = 0

    var normal_radius: Int = 0

    var normal_dot_color: Int = 0
    var selected_dot_color: Int = 0

    var normal_title_color: Int = 0
    var selected_title_color: Int = 0

    var isWrapWidth : Boolean = false
    var isWrapHeight: Boolean = false

    var orientation: Int = 0

    internal var dot_list = ArrayList<Dot>()

    internal var title_list = ArrayList<String>()

    private var startPosX: Int = 0
    private var startPosY: Int = 0

    var preDotPos: Int = 0
        private set
    var selectedDotPos: Int = 0
        private set

    private var wantShowText: Boolean = false

    var isHasTitle: Boolean = false

    internal lateinit var dotScaleAnim: ValueAnimator

    internal lateinit var title_fade_in: ValueAnimator
    internal lateinit var title_fade_out: ValueAnimator

    private val longestTitleLength: Int
        get() {
            var highest_length = 0
            for (title in title_list) {
                if (title.length > highest_length) {
                    highest_length = title.length
                }
            }
            return highest_length
        }

    var isWantShowText: Boolean
        get() = wantShowText
        set(wantShowText) {
            this.wantShowText = wantShowText
            invalidate()
        }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val properties = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator)

        normal_dot_color = properties.getColor(R.styleable.ViewPagerIndicator_normal_color, Color.GRAY)

        selected_dot_color = properties.getColor(R.styleable.ViewPagerIndicator_selected_color, Color.GRAY)

        normal_radius = properties.getDimension(R.styleable.ViewPagerIndicator_normal_radius, 20f).toInt()

        selected_radius = properties.getDimension(R.styleable.ViewPagerIndicator_seleted_radius, 20f).toInt()

        distance_between_2_dots = properties.getDimension(R.styleable.ViewPagerIndicator_distance_between_two_dots, 20f).toInt()

        dot_count = properties.getInt(R.styleable.ViewPagerIndicator_dot_count, 2)

        isWrapWidth = properties.getBoolean(R.styleable.ViewPagerIndicator_wrap_width, false)

        isWrapHeight = properties.getBoolean(R.styleable.ViewPagerIndicator_wrap_height, false)

        orientation = properties.getInt(R.styleable.ViewPagerIndicator_orientation, horizontal)

        isHasTitle = properties.getBoolean(R.styleable.ViewPagerIndicator_hasTitle, false)

        wantShowText = properties.getBoolean(R.styleable.ViewPagerIndicator_wantShowText, true)

        selected_title_color = properties.getColor(R.styleable.ViewPagerIndicator_selected_title_color, Color.GRAY)

        normal_title_color = properties.getColor(R.styleable.ViewPagerIndicator_normal_title_color, Color.GRAY)

        createAnim()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = 0
        var height = 0

        if (!isHasTitle) {
            if (orientation == horizontal) {
                val expected_width = (normal_radius * dot_count
                        + distance_between_2_dots * (dot_count - 1)
                        + selected_radius * 2)

                width = if (isWrapWidth) expected_width else View.MeasureSpec.getSize(widthMeasureSpec)

                height = if (isWrapHeight) selected_radius * 2 else View.MeasureSpec.getSize(heightMeasureSpec)

                startPosX = if (isWrapWidth) selected_radius else (width - expected_width) / 2 + selected_radius
            } else if (orientation == vertical) {

                val expected_height = (normal_radius * dot_count
                        + distance_between_2_dots * (dot_count - 1)
                        + selected_radius * 3)

                height = if (isWrapHeight) expected_height else View.MeasureSpec.getSize(heightMeasureSpec)

                width = if (isWrapWidth) selected_radius * 2 else View.MeasureSpec.getSize(widthMeasureSpec)

                startPosY = if (isWrapHeight) selected_radius * 3 / 2 else (height - expected_height) / 2 + selected_radius * 3 / 2
            }
        } else {
            if (orientation == vertical) {

                if (isWrapWidth) {

                    val expected_width = (longestTitleLength * selected_radius * 2 + selected_radius * 2.5).toInt()
                    width = expected_width
                } else {

                    width = View.MeasureSpec.getSize(widthMeasureSpec)

                }

                if (isWrapHeight) {
                    val expected_height = (normal_radius * dot_count
                            + distance_between_2_dots * (dot_count - 1)
                            + selected_radius * 3)
                    height = expected_height
                } else {
                    height = View.MeasureSpec.getSize(heightMeasureSpec)
                }

                startPosY = selected_radius * 3 / 2

                startPosX = width - selected_radius
            }
        }


        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (orientation == horizontal)
            createHorizontalDot_List()
        else if (orientation == vertical) createVerticalDot_List()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (dot in dot_list) {
            dot.drawPoint(canvas)
        }

        if (wantShowText) {
            for (dot in dot_list) {
                dot.drawTitle(canvas)
            }
        }
    }


    private fun createAnim() {
        dotScaleAnim = ValueAnimator.ofInt(normal_radius, selected_radius)

        dotScaleAnim.duration = 300

        dotScaleAnim.addUpdateListener { animation ->
            // increasing
            val big_radius = animation.animatedValue as Int

            // decreasing
            val small_radius = selected_radius - big_radius + normal_radius

            dot_list[selectedDotPos].radius = big_radius

            dot_list[preDotPos].radius = small_radius

            invalidate()
        }

        title_fade_in = ValueAnimator.ofInt(0, 255)
        title_fade_in.duration = 300
        title_fade_in.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Int

            // Start
            if (alpha == 0) isWantShowText = true

            for (dot in dot_list) {
                dot.setTextAlpha(alpha)
                invalidate()
            }

        }

        title_fade_out = ValueAnimator.ofInt(255, 0)
        title_fade_out.duration = 300
        title_fade_out.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Int
            for (dot in dot_list) {
                dot.setTextAlpha(alpha)
                invalidate()
            }

            if (alpha == 0) isWantShowText = false
        }

    }

    fun hightLightDotAt(index: Int) {
        preDotPos = selectedDotPos
        selectedDotPos = index

        val previous_dot = dot_list[preDotPos]
        previous_dot.setPaintColor(normal_dot_color)
        previous_dot.setTextColor(normal_title_color)
        previous_dot.setTextStyle(FontManager.comfortaa_regular)

        val selected_dot = dot_list[selectedDotPos]
        selected_dot.setPaintColor(selected_dot_color)
        selected_dot.setTextColor(selected_title_color)
        selected_dot.setTextStyle(FontManager.comfortaa_bold)

        dotScaleAnim.start()
    }

    fun showTitle() {
        title_fade_in.start()
    }

    fun hideTitle() {
        title_fade_out.start()
    }

    private fun createHorizontalDot_List() {

        dot_list = ArrayList()

        val first_Dot = Dot(startPosX, height / 2,
                selected_radius, "", selected_dot_color, selected_title_color)
        first_Dot.setTextStyle(FontManager.comfortaa_bold)
        dot_list.add(first_Dot)
        for (i in 1 until dot_count) {
            val x_pos = startPosX + i * (normal_radius + distance_between_2_dots)
            val dot = Dot(x_pos, height / 2, normal_radius, "", normal_dot_color, normal_title_color)
            dot.setTextStyle(FontManager.comfortaa_regular)
            dot_list.add(dot)
        }

    }

    private fun createVerticalDot_List() {

        dot_list = ArrayList()

        val first_Dot = Dot(startPosX, startPosY,
                selected_radius, title_list[0], selected_dot_color, selected_title_color)
        dot_list.add(first_Dot)
        first_Dot.setTextStyle(FontManager.comfortaa_bold)

        for (i in 1 until title_list.size) {
            val y_pos = startPosY + i * (normal_radius + distance_between_2_dots)
            val dot = Dot(startPosX, y_pos, normal_radius, title_list[i], normal_dot_color, normal_title_color)
            dot.setTextStyle(FontManager.comfortaa_regular)
            dot_list.add(dot)
        }

    }

    fun setTitle_list(list: ArrayList<String>) {
        this.title_list = ArrayList()
        this.title_list = list

        if (orientation == horizontal)
            createHorizontalDot_List()
        else if (orientation == vertical) createVerticalDot_List()

        measure(measuredWidth, measuredHeight)
        requestLayout()
    }

    companion object {

        val vertical = 2

        val horizontal = 1
    }
}


