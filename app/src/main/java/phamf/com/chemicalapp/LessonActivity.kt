package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

import java.util.concurrent.Executors

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_lesson.*
import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity
import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter
import phamf.com.chemicalapp.CustomView.LessonViewCreator
import phamf.com.chemicalapp.CustomView.LessonViewCreator.Companion.PART_DEVIDER
import phamf.com.chemicalapp.CustomView.ViewPagerIndicator
import phamf.com.chemicalapp.Manager.AppThemeManager
//import phamf.com.chemicalapp.Presenter.LessonActivityPresenter
import phamf.com.chemicalapp.ViewModel.MVVM_LessonActivityPresenter

import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel


/**
 * @see LessonActivityPresenter
 */
class LessonActivity : FullScreenActivity(), ILessonActivity.View {

    private lateinit var vpg_lesson_adapter: ViewPager_Lesson_Adapter
    private var viewModel: MVVM_LessonActivityPresenter? = null
    lateinit var fade_in: Animation
    lateinit var fade_out: Animation
    internal var isOn = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson)

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(MVVM_LessonActivityPresenter::class.java)

        var ro_lesson_observer = Observer<RO_Lesson> { ro_lesson ->
            if (vpg_indicator!!.isHasTitle) vpg_indicator!!.setTitle_list(vpg_lesson_adapter.titles)

            separatePart_And_BindDataToViewPG(ro_lesson!!.content)
            vpg_indicator!!.dot_count = vpg_lesson_adapter.count
            txt_lesson_title!!.setText(ro_lesson!!.name)
        }

        viewModel!!.ldt_ro_lesson.observe(this, ro_lesson_observer)

        setTheme()

        addControl()

        setUpViewCreator()

        loadAndSetEventForAnimation()

        addEvent()

        viewModel!!.loadData(this)
    }

    override fun addControl() {
        val fragmentManager = supportFragmentManager
        vpg_lesson_adapter = ViewPager_Lesson_Adapter(fragmentManager)
        vpg_lesson!!.adapter = vpg_lesson_adapter
    }

    override fun addEvent() {

        btn_back_lesson!!.setOnClickListener { v -> finish() }

        vpg_lesson!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (!isOn) {
                    bg_hide_content_to_show_indicator!!.startAnimation(fade_in)
                    isOn = true
                }
                time = 1000
            }

            override fun onPageSelected(position: Int) {
                vpg_indicator!!.hightLightDotAt(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun setTheme() {
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_lesson!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_lesson!!.visibility = View.INVISIBLE
        }

        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_lesson_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_lesson_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    fun separatePart_And_BindDataToViewPG(content: String) {

        val part_list = content.split(PART_DEVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        //Add to Viewpager, then ViewPager send it to Fragment and process
        /**
         * @see phamf.com.chemicalapp.Fragment.LessonPartFragment
         */
        vpg_lesson_adapter.addData(*part_list)
    }

    override fun setUpViewCreator() {

        if (!LessonViewCreator.isSetUp) {
            LessonViewCreator.ViewCreator.setMarginBigTitle(DpToPixel(10), DpToPixel(7), DpToPixel(10), 0)
            LessonViewCreator.ViewCreator.setMarginSmallTitle(DpToPixel(13), DpToPixel(4), DpToPixel(10), 0)
            LessonViewCreator.ViewCreator.setMarginSmallerTitle(DpToPixel(15), DpToPixel(4), DpToPixel(10), 0)
            LessonViewCreator.ViewCreator.setMarginContent(DpToPixel(20), DpToPixel(4), DpToPixel(10), 0)
            LessonViewCreator.ViewCreator.setBig_title_text_size(DpToPixel(2.2))
            LessonViewCreator.ViewCreator.setSmall_title_text_size(DpToPixel(2))
            LessonViewCreator.ViewCreator.setSmaller_title_text_size(DpToPixel(1.8))
            LessonViewCreator.ViewCreator.setContent_text_size(DpToPixel(1.5))
            LessonViewCreator.isSetUp = true
        }

    }

    fun loadAndSetEventForAnimation() {
        val threadPool = Executors.newFixedThreadPool(1)

        val count_time_to_hide = {
            while (time > 0) {
                try {
                    Thread.sleep(100)
                    time = time - 100
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }

            runOnUiThread { bg_hide_content_to_show_indicator!!.startAnimation(fade_out) }
        }

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fade_in.duration = 200

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fade_out.duration = 200

        fade_out.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                isOn = false
            }

            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })


        fade_in.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                threadPool.execute(count_time_to_hide)
            }

            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

    companion object {

        @Volatile
        internal var time = 500
    }

}
