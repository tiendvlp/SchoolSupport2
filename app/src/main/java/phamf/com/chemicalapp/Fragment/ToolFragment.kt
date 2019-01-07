package com.schoolsupport.app.dmt91.schoolsupport.View.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import butterknife.OnClick
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tool.*
import kotlinx.android.synthetic.main.nav_view.*
import phamf.com.chemicalapp.*
import phamf.com.chemicalapp.Abstraction.Interface.IMainActivity
import phamf.com.chemicalapp.Abstraction.Interface.OnThemeChangeListener
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.Manager.FloatingSearchViewManager
import phamf.com.chemicalapp.Manager.FontManager
import phamf.com.chemicalapp.Manager.RequireOverlayPermissionManager
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.ViewModel.MVVM_MainActivityPresenter
import java.util.*
/**
 * Presenter
 * @see MainActivityPresenter
 */
class ToolFragment : Fragment(), IMainActivity.View, OnThemeChangeListener, MVVM_MainActivityPresenter.OnUpdateCheckedListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tool, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Realm.init(this.context)

        createNecessaryInfo()

        setUpViewModel()

        setFont()

        overlayPermissionManager!!.requirePermission(CODE_DRAW_OVER_OTHER_APP_PERMISSION)

        loadAnim()

        addControl()

        addEvent()

        viewModel!!.loadData()

        if (!hasCheck) {
            viewModel!!.checkUpdateStatus()
            hasCheck = true
        }



    }

    fun addControls() {

    }

    fun addEvents() {

    }
    companion object {
        var hasCheck = false
        internal val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 21
        @JvmStatic
        fun newInstance() : ToolFragment {
            return ToolFragment()
        }
    }

    private var rcv_search_adapter: Search_CE_RCV_Adapter? = null

    /**
     * This variable is for optimization, avoid calling hideNavigationAndStatusBar many times
     * though app has been moded to full screen
     */
    private var hasHiddenNavAndStatusBar: Boolean = false

    private var isOnSearchMode: Boolean = false

    private var virtualKeyboardManager: InputMethodManager? = null

    private var floatingSearchViewManager: FloatingSearchViewManager? = null

    private var overlayPermissionManager: RequireOverlayPermissionManager? = null

    lateinit var fade_out: Animation
    lateinit var fade_in: Animation

    private var viewModel: MVVM_MainActivityPresenter? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "You're now able to use floating search view", Toast.LENGTH_SHORT).show()
            } else {
                activity!!.finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel!!.pushCachingDataToDB()
        viewModel!!.saveTheme()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun addControl() {

        rcv_search_adapter = Search_CE_RCV_Adapter(context!!)
        rcv_search_adapter!!.adaptFor(rcv_search)
        rcv_search_adapter!!.observe(edt_search)
        setEdt_SearchAdvanceFunctions()

        // isOnNightMode was set by Presenter in loadtheme()
        sw_night_mode.isChecked = AppThemeManager.isOnNightMode
        if (AppThemeManager.isOnNightMode) {
            bg_night_mode!!.visibility = View.VISIBLE
        }

    }


    @OnClick(R.id.btn_background_color_1, R.id.btn_background_color_2, R.id.btn_background_color_3, R.id.btn_background_color_4, R.id.btn_background_color_5, R.id.btn_background_color_6)
    fun onBackgroundColorButtonsClick(v: Button) {
        var color = 0
        if (!AppThemeManager.isCustomingTheme) AppThemeManager.isCustomingTheme = true
        if (!AppThemeManager.isUsingColorBackground) AppThemeManager.isUsingColorBackground = true
        when (v.id) {
            R.id.btn_background_color_1 -> {
                color = 0
            }
            R.id.btn_background_color_2 -> {
                color = 1
            }
            R.id.btn_background_color_3 -> {
                color = 2
            }
            R.id.btn_background_color_4 -> {
                color = 3
            }
            R.id.btn_background_color_5 -> {
                color = 4
            }
            R.id.btn_background_color_6 -> {
                color = 5
            }
        }
        AppThemeManager.backgroundColor = color
        setTheme()
    }

    @OnClick(R.id.btn_widget_color_1, R.id.btn_widget_color_2, R.id.btn_widget_color_3, R.id.btn_widget_color_4, R.id.btn_widget_color_5, R.id.btn_widget_color_6)
    fun onWidgetsColorButtonClick(v: Button) {
        var color = 0
        AppThemeManager.isCustomingTheme = true
        when (v.id) {
            R.id.btn_widget_color_1 -> {
                color = 0
            }
            R.id.btn_widget_color_2 -> {
                color = 1
            }
            R.id.btn_widget_color_3 -> {
                color = 2
            }
            R.id.btn_widget_color_4 -> {
                color = 3
            }
            R.id.btn_widget_color_5 -> {
                color = 4
            }
            R.id.btn_widget_color_6 -> {
                color = 5
            }
        }
        AppThemeManager.textColor = color
        setTheme()
    }

    @OnClick(R.id.btn_art_theme, R.id.btn_dark_theme, R.id.btn_normal_theme)
    fun onThemeButtonsClick(v: Button) {
        var theme = 0
        when (v.id) {
            R.id.btn_art_theme -> {
                theme = 1
            }

            R.id.btn_dark_theme -> {
                theme = 2
            }

            R.id.btn_normal_theme -> {
                theme = 0
            }
        }
        AppThemeManager.setTheme(theme)
        setTheme()
    }

    private fun setUpViewModel() {

        viewModel = ViewModelProviders.of(this).get(MVVM_MainActivityPresenter::class.java)

        viewModel!!.ldt_ro_chemicalEquation.observe(this,android.arch.lifecycle.Observer<ArrayList<RO_ChemicalEquation>> { ro_chemicalEquations -> rcv_search_adapter!!.setData(ro_chemicalEquations!!) })

        viewModel!!.setOnThemeChangeListener(this)

        viewModel!!.setOnUpdateStatusCheckedListener(this)


        viewModel!!.loadTheme()
    }

    override fun createNecessaryInfo() {
        FontManager.createFont(context!!.assets)
        virtualKeyboardManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        floatingSearchViewManager = FloatingSearchViewManager(activity!!)
        overlayPermissionManager = RequireOverlayPermissionManager(activity!!)
    }

    override fun addEvent() {

        btn_search!!.setOnClickListener { v ->
            if (!isOnSearchMode) {
                showSoftKeyboard()
                rcv_search_adapter!!.isSearching(true)
                search_chem_equation_view_parent!!.startAnimation(fade_in)
                isOnSearchMode = true
            }
        }

        bg_escape_search_mode!!.setOnClickListener { v ->
            search_chem_equation_view_parent!!.startAnimation(fade_out)
            edt_search!!.setText("")
            rcv_search_adapter!!.isSearching(false)
            hideSoftKeyboard(activity!!)
            isOnSearchMode = false
        }

        btn_setting!!.setOnClickListener { v -> main_activity_drawer_layout!!.openDrawer(nav_view!!, true) }

        btn_top_turn_off_search!!.setOnClickListener { v -> bg_escape_search_mode!!.performClick() }

        btn_recent_lesson!!.setOnClickListener { v -> startActivity(Intent(activity!!, RecentLessonsActivity::class.java)) }

        btn_bangtuanhoan!!.setOnClickListener { v -> startActivity(Intent(activity!!, BangTuanHoangActivity::class.java)) }

        btn_quick_search!!.setOnClickListener { v ->
            floatingSearchViewManager!!.init()

        }

        btn_turnOff_search!!.setOnClickListener { v ->
            search_chem_equation_view_parent!!.startAnimation(fade_out)
            edt_search!!.setText("")
            rcv_search_adapter!!.isSearching(false)
        }

        btn_lesson!!.setOnClickListener { v -> startActivity(Intent(activity!!, LessonMenuActivity::class.java)) }

        btn_dongphan_danhphap!!.setOnClickListener { v -> startActivity(Intent(activity!!, DPDPMenuActivity::class.java)) }


        txt_lesson!!.setOnClickListener { v -> startActivity(Intent(activity!!, LessonMenuActivity::class.java)) }

        var on_search_adapter_item_click = object : Search_CE_RCV_Adapter.OnItemClickListener {
            override fun OnItemClickListener(view: View, equation: RO_ChemicalEquation, position: Int) {
                // Do this to bring the just chosen equation to top of the list
                rcv_search_adapter!!.list!!.remove(equation)
                rcv_search_adapter!!.list!!.add(0, equation)
                rcv_search_adapter!!.notifyDataSetChanged()
                // Update the top in database
                viewModel!!.bringToTop(equation)

                val intent = Intent(activity!!, ChemicalEquationActivity::class.java)
                intent.putExtra(ChemicalEquationActivity.CHEMICAL_EQUATION, equation)
                startActivity(intent)
            }

        }
        rcv_search_adapter!!.setOnItemClickListener (on_search_adapter_item_click)


        btn_set_as_defaut!!.setOnClickListener { v -> viewModel!!.setThemeDefaut() }

        var onUpdateSuccess = object : MVVM_MainActivityPresenter.OnUpdateSuccess {
            override fun onUpdateSuccess() {
                startActivity(Intent(activity!!, MainActivity::class.java))

            }

        }

        btn_update!!.setOnClickListener { v ->
            Log.e("Common bro ?", "Nà ní")
            viewModel!!.update(onUpdateSuccess)
        }


        sw_night_mode!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                bg_night_mode!!.visibility = View.VISIBLE
                viewModel!!.turnOnNightMode()
            } else {
                bg_night_mode!!.visibility = View.GONE
                viewModel!!.turnOffNightMode()
            }
        }


    }

    override fun loadAnim() {
        fade_in = AnimationUtils.loadAnimation(activity!!, R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(activity!!, R.anim.fade_out)
        fade_out.fillAfter = false
        fade_in.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                search_chem_equation_view_parent!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        fade_out.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                search_chem_equation_view_parent!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

    override fun setFont() {
        txt_lesson!!.typeface = FontManager.myriad_pro_bold
        txt_recent_lesson!!.typeface = FontManager.myriad_pro_bold
        txt_dongphan_danhphap!!.typeface = FontManager.myriad_pro_bold
        txt_bangtuanhoang!!.typeface = FontManager.myriad_pro_bold
        txt_quick_search!!.typeface = FontManager.myriad_pro_bold

        edt_search!!.typeface = FontManager.myriad_pro_bold
    }

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme or AppThemeManager.isUsingAvailableThemes) {
            txt_lesson!!.setTextColor(context!!.getColor(AppThemeManager.getTextColor_()))
            txt_quick_search!!.setTextColor(context!!.getColor(AppThemeManager.getTextColor_()))
            txt_bangtuanhoang!!.setTextColor(context!!.getColor(AppThemeManager.getTextColor_()))
            txt_dongphan_danhphap!!.setTextColor(context!!.getColor(AppThemeManager.getTextColor_()))
            txt_recent_lesson!!.setTextColor(context!!.getColor(AppThemeManager.getTextColor_()))

            if (AppThemeManager.isUsingColorBackground) {
                main_activity_drawer_layout!!.setBackgroundColor(context!!.getColor(AppThemeManager.getBackgroundColor_()))
            } else {
                main_activity_drawer_layout!!.background = AppThemeManager.getBackgroundDrawable_()
            }
        }
    }

    override fun setEdt_SearchAdvanceFunctions() {

        edt_search!!.setOnClickListener { v ->
            if (!hasHiddenNavAndStatusBar) {
                //                    fullScreenManager.hideNavAndStatusBar_After(1000);
//                makeFullScreenAfter(1000)
                hasHiddenNavAndStatusBar = true
            }
            if (!isOnSearchMode) {
                search_chem_equation_view_parent!!.startAnimation(fade_in)
                rcv_search_adapter!!.isSearching(true)
                isOnSearchMode = true
            }
        }


        edt_search!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasHiddenNavAndStatusBar) {
                //  fullScreenManager.hideNavAndStatusBar_After(1000);
//                makeFullScreenAfter(1000)
                hasHiddenNavAndStatusBar = true
            }
        }


        edt_search!!.setOnHideVirtualKeyboardListener (object : VirtualKeyBoardSensor.OnHideVirtualKeyboardListener {
            override fun onHide() {
                hasHiddenNavAndStatusBar = false
            }
        })

    }

    override fun hideSoftKeyboard(activity: Activity) {
        virtualKeyboardManager!!.hideSoftInputFromWindow(Objects.requireNonNull(activity.currentFocus).windowToken, 0)
    }

    override fun showSoftKeyboard() {

        virtualKeyboardManager!!.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
        Toast.makeText(context!!, "Show", Toast.LENGTH_SHORT).show()
    }

    override fun onThemeChange() {
        setTheme()
    }

    @SuppressLint("SetTextI18n")
    override fun onStatusChecked(isAvailable: Boolean, version: Long) {
        if (isAvailable) {
            txt_update_status!!.text = "Available"
            txt_update_version!!.text = "1.$version"
            linearlayout_version!!.visibility = View.VISIBLE
            txt_update_version!!.visibility = View.VISIBLE
            btn_update!!.visibility = View.VISIBLE
            btn_update!!.isClickable = true
        } else {
            txt_update_status!!.text = "up to date"
            btn_update!!.visibility = View.GONE
            btn_update!!.isClickable = false
        }
        Log.e("Version", version.toString() + "")
    }

}
