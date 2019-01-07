package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_dpdp.*
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPActivity
import phamf.com.chemicalapp.Abstraction.SpecialDataType.QuickChangeItemListViewAdapter
import phamf.com.chemicalapp.Adapter.QuickChange_Organic_Adapter
import phamf.com.chemicalapp.CustomView.LessonViewCreator
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.Model.OrganicMolecule
//import phamf.com.chemicalapp.Presenter.DPDPActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule
import phamf.com.chemicalapp.ViewModel.MVVM_DPDPActivityPresenter

import phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel

/**
 * Presenter
 * @see phamf.com.chemicalapp.Presenter.DPDPActivityPresenter
 */

class DPDPActivity : FullScreenActivity(), IDPDPActivity.View/*, DPDPActivityPresenter.DataLoadListener */{

    lateinit var qc_organic_adapter: QuickChange_Organic_Adapter

    lateinit var viewCreator: LessonViewCreator.ViewCreator

    // check if s showing quick change dpdp dpdp_board
    private var isShowingQCB: Boolean = false

    internal lateinit var fade_out_then_in: Animation
    internal lateinit var left_to_right: Animation
    internal lateinit var right_to_left: Animation

    //    private DPDPActivityPresenter presenter;

    private var viewModel: MVVM_DPDPActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dpdp)
        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(MVVM_DPDPActivityPresenter::class.java)

        var name_observer = object : Observer<String?> {
            override fun onChanged(name : String?) {
                txt_dpdp_title!!.setText(name)
            }
        }

        var content_observer = object : Observer<String?> {
            override fun onChanged(content : String?) {
                viewCreator.addView(content!!)
            }
        }

        var organicMolecules_observer = object : Observer<Collection<RO_OrganicMolecule>> {
            override fun onChanged(rO_OrganicMolecule : Collection<RO_OrganicMolecule>?) {
                qc_organic_adapter.setData(rO_OrganicMolecule!!)
            }
        }

        viewModel!!.ldt_dpdp_name_data.observe(this, name_observer)

        viewModel!!.ldt_content_data.observe(this, content_observer)

        viewModel!!.ldt_ro_organicMolecule.observe(this, organicMolecules_observer)

        setUpViewCreator()

        addAnimation()

        setTheme()

        addControl()

        addEvent()

        viewModel!!.loadData(this)

    }

    override fun addControl() {
        qc_organic_adapter = QuickChange_Organic_Adapter(this)
        qc_organic_adapter.adaptFor(lv_dpdp_quick_change_organic!!)
        lv_dpdp_quick_change_organic!!.visibility = View.GONE

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_dpdp!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_dpdp!!.visibility = View.INVISIBLE
        }
    }

    override fun addAnimation() {
        fade_out_then_in = AnimationUtils.loadAnimation(this, R.anim.fade_out_then_fade_in)
        left_to_right = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        right_to_left = AnimationUtils.loadAnimation(this, R.anim.right_to_left)

        left_to_right.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                lv_dpdp_quick_change_organic!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {

            }
            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        right_to_left.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                lv_dpdp_quick_change_organic!!.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {
}
        })
    }
        override fun addEvent() {

        var itemClickListener = object : QuickChangeItemListViewAdapter.OnItemClickListener<RO_OrganicMolecule> {
            override fun OnItemClickListener(item: RO_OrganicMolecule, view: View) {
                //            viewCreator.clearAll();
                dpdp_board!!.startAnimation(fade_out_then_in)
                viewModel!!.convertContent(item)
                lv_dpdp_quick_change_organic!!.startAnimation(left_to_right)
                isShowingQCB = false
            }

        }
        //qc : Quick Change
        qc_organic_adapter.setOnItemClickListener_(itemClickListener)

        dpdp_board!!.setOnClickListener { v ->
            if (isShowingQCB) {
                isShowingQCB = false
                lv_dpdp_quick_change_organic!!.startAnimation(left_to_right)
            }
        }

        btn_dpdp_turn_on_quick_change!!.setOnClickListener { v ->
            if (!isShowingQCB) {
                lv_dpdp_quick_change_organic!!.startAnimation(right_to_left)
                isShowingQCB = true
            }
        }

        btn_dpdp_back!!.setOnClickListener { v -> finish() }

        btn_dpdp_home!!.setOnClickListener { v -> startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) }
    }

    override fun setUpViewCreator() {
        viewCreator = LessonViewCreator.ViewCreator(this, dpdp_board!!)
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

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                dpdp_board!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                dpdp_board!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    fun onDataLoadSuccess(title: String, content: String) {
        txt_dpdp_title!!.text = title
        viewCreator.addView(content)
    }
}
