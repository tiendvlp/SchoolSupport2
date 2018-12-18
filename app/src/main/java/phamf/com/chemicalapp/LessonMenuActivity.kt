package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_lesson.*
import kotlinx.android.synthetic.main.activity_lesson_menu.*
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity
import phamf.com.chemicalapp.Adapter.Chapter_Menu_Adapter
import phamf.com.chemicalapp.Adapter.Lesson_Menu_Adapter
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.ViewModel.MVVM_LessonMenuActivityPresenter


/**
 * Presenter
 * @see MVVM_LessonMenuActivityPresenter
 */
class LessonMenuActivity : FullScreenActivity(), ILessonMenuActivity.View {


    private lateinit var chapter_menu_adapter: Chapter_Menu_Adapter

    private lateinit var lesson_menu_adapter: Lesson_Menu_Adapter

    lateinit var turnOnChapterButton_disappear: Animation
    lateinit var chapter_disappear: Animation
    lateinit var lesson_disappear: Animation

    lateinit var turnOnChapterButton_appear: Animation
    lateinit var chapter_appear: Animation
    lateinit var lesson_appear: Animation


    lateinit var viewModel: MVVM_LessonMenuActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson_menu)

        ButterKnife.bind(this)

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        setUpViewModel()

        setTheme()

        loadAnim()

        addControl()

        addEvent()

        viewModel.loadData()
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearAllListenerToDatabase()
        viewModel.pushCachingDataToDB()
    }


    fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MVVM_LessonMenuActivityPresenter::class.java)

        var ro_chapter_observer = Observer<RealmResults<RO_Chapter>> { ro_chapters ->
            chapter_menu_adapter.setData(ro_chapters!!)
            circle_pb_lesson_menu!!.visibility = View.GONE
        }

        viewModel.l_data.observe(this, ro_chapter_observer)
    }


    override fun addControl() {
        chapter_menu_adapter = Chapter_Menu_Adapter(this)
        chapter_menu_adapter.adaptFor(rcv_chapter_menu!!)
        lesson_menu_adapter = Lesson_Menu_Adapter(this)
        lesson_menu_adapter.adaptFor(rcv_lesson_menu!!)

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_lesson_menu!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_lesson_menu!!.visibility = View.INVISIBLE
        }
    }

    override fun addEvent() {

        btn_turn_on_chapter_menu!!.setOnClickListener { v ->
            rcv_chapter_menu!!.startAnimation(chapter_appear)
            btn_turn_on_chapter_menu!!.startAnimation(turnOnChapterButton_disappear)
            rcv_lesson_menu!!.startAnimation(lesson_disappear)
            txt_lesson_menu_title!!.text = "Chương"
        }

        btn_lesson_menu_back!!.setOnClickListener { v -> finish() }

        var on_chapter_menu_item_click = object : RCV_Menu_Adapter.OnItemClickListener<RO_Chapter>{
            override fun onItemClickListener(item: RO_Chapter) {
                rcv_chapter_menu!!.startAnimation(chapter_disappear)
                rcv_lesson_menu!!.startAnimation(lesson_appear)
                btn_turn_on_chapter_menu!!.startAnimation(turnOnChapterButton_appear)
                if (item.lessons != null ) {
                    lesson_menu_adapter.setData(item.lessons)
                } else {
                    Log.e("Null", "lessons")
                }
                txt_lesson_menu_title!!.text = item.name
            }


        }
        var on_lesson_menu_item_click = object : RCV_Menu_Adapter.OnItemClickListener<RO_Lesson>{
            override fun onItemClickListener(item: RO_Lesson) {
                val intent = Intent(this@LessonMenuActivity, LessonActivity::class.java)
                intent.putExtra(LESSON_NAME, item)
                startActivity(intent)
                viewModel.bringToTop(item)
            }
        }

        chapter_menu_adapter.setOnItemClickListener(on_chapter_menu_item_click)
        //                |
        //                |
        //                |     Load data from chapter_menu_adapter to lesson_menu_adapter
        //              \ | /
        //               \|/
        //                v
        lesson_menu_adapter.setOnItemClickListener (on_lesson_menu_item_click)


    }

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_lesson_menu_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_lesson_menu_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    override fun loadAnim() {

        chapter_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        chapter_disappear = AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out)

        turnOnChapterButton_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        turnOnChapterButton_disappear = AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out)

        lesson_appear = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        lesson_disappear = AnimationUtils.loadAnimation(this, R.anim.suft_right_fade_out)

        chapter_appear.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                rcv_chapter_menu!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        chapter_disappear.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                rcv_chapter_menu!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        lesson_disappear.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationEnd(animation: Animation) {
                rcv_lesson_menu!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        lesson_appear.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                rcv_lesson_menu!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        turnOnChapterButton_appear.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                btn_turn_on_chapter_menu!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        turnOnChapterButton_disappear.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                btn_turn_on_chapter_menu!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })


    }

    companion object {
        val LESSON_NAME = "lesson_name"
    }

}
