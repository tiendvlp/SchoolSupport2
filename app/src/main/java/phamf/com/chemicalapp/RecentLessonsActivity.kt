package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_recent_learning_lesson.*
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter
import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity
import phamf.com.chemicalapp.Adapter.Lesson_Menu_Adapter
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
//import phamf.com.chemicalapp.Presenter.RecentLessonActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.ViewModel.MVVM_RecentLessonActivityPresenter


/**
 * Presenter
 * @see RecentLessonActivityPresenter
 */

class RecentLessonsActivity : FullScreenActivity() {


    lateinit var rcv_recent_lesson_menu_adapter: Lesson_Menu_Adapter

//    @BindView(R.id.parent_recent_learning_lesson_activity)
//    internal var parent_recent_learning_lesson_activity: ConstraintLayout? = null

//    @BindView(R.id.bg_night_mode_recent_learning_lesson_recent_learning_lesson)
//    internal var bg_night_mode_recent_learning_lesson: TextView? = null

//    @BindView(R.id.btn_recent_lesson_back)
//    internal var btn_back: Button? = null

    lateinit var viewModel: MVVM_RecentLessonActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_learning_lesson)

        ButterKnife.bind(this)

        setUpViewModel()

        setTheme()

        addControl()

        addEvent()

        viewModel.loadData()

    }

    override fun onPause() {
        super.onPause()
        viewModel.pushCachingData()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MVVM_RecentLessonActivityPresenter::class.java)
        viewModel.ldt_recent_lessons.observe(this, Observer <ArrayList<RO_Lesson>>{ recent_lessons -> rcv_recent_lesson_menu_adapter.setData(recent_lessons!!) })
    }

    fun addControl() {
        rcv_recent_lesson_menu_adapter = Lesson_Menu_Adapter(this)
        rcv_recent_lesson_menu_adapter.adaptFor(rcv_recent_lesson_menu!!)

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_recent_learning_lesson!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_recent_learning_lesson!!.visibility = View.INVISIBLE
        }
    }

    fun addEvent() {
        var on_recent_lesson_menu_item_click = object : RCV_Menu_Adapter.OnItemClickListener<RO_Lesson> {
            override fun onItemClickListener(item: RO_Lesson) {
                val intent = Intent(this@RecentLessonsActivity, LessonActivity::class.java)
                intent.putExtra(LessonMenuActivity.LESSON_NAME, item)
                startActivity(intent)
                viewModel.bringToTop(item)
            }

        }
        rcv_recent_lesson_menu_adapter.setOnItemClickListener(on_recent_lesson_menu_item_click)

        btn_recent_lesson_back!!.setOnClickListener { v -> finish() }
    }

    fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_recent_learning_lesson_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_recent_learning_lesson_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

}
