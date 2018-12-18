package phamf.com.chemicalapp

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager

import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_chemical_equation.*
import phamf.com.chemicalapp.Abstraction.Interface.IChemicalEquationActivity
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.ViewModel.MVVM_ChemicalEquationActivityPresenter
import java.util.*

@Suppress("DEPRECATION")
/**
 * Presenter
 * @see ChemicalEquationActivityPresenter
 */
class ChemicalEquationActivity : FullScreenActivity(), IChemicalEquationActivity.View{

    private var rcv_search_adapter: Search_CE_RCV_Adapter? = null

    private var hasHiddenNavAndStatusBar: Boolean = false

    private var isOnSearchMode: Boolean = false

    lateinit var virtualKeyboardManager: InputMethodManager

    private var fade_in: Animation? = null
    private var fade_out: Animation? = null

    lateinit private var viewModel : MVVM_ChemicalEquationActivityPresenter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chemical_equation)

        ButterKnife.bind(this)

        setUpNecessaryInfos()

        loadAnim()

        setUpViewModel()

        addControl()

        addEvent()

        viewModel.loadData(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.ldt_ro_recent_ces.removeObservers(this)
        viewModel.ldt_ro_chem_equation.removeObservers(this)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(MVVM_ChemicalEquationActivityPresenter::class.java)
        viewModel.ldt_ro_chem_equation.observe(this, Observer {
            bindData(it!!)
        })

        viewModel.ldt_ro_recent_ces.observe(this, Observer{
            rcv_search_adapter!!.setData(it!!)
        })
    }

    override fun setUpNecessaryInfos() {
        virtualKeyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setEdt_SearchAdvanceFunctions()
    }

    override fun addControl() {
        rcv_search_adapter = Search_CE_RCV_Adapter(this)
        rcv_search_adapter!!.adaptFor(rcv_chem_search!!)
        rcv_search_adapter!!.observe(edt_chem_search!!)
    }

    override fun addEvent() {
        rcv_search_adapter!!.setOnItemClickListener (object : Search_CE_RCV_Adapter.OnItemClickListener{
            override fun OnItemClickListener(view: View, equation: RO_ChemicalEquation, position: Int) {
                bindData(equation)
                chemical_equation_search_equation_search_view_parent!!.startAnimation(fade_out)
                rcv_search_adapter!!.isSearching(false)
                hideSoftKeyboard(this@ChemicalEquationActivity)
                isOnSearchMode = false
            }
        })


        bg_chem_escape_search_mode!!.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                chemical_equation_search_equation_search_view_parent!!.startAnimation(fade_out)
                edt_chem_search!!.setText("")
                rcv_search_adapter!!.isSearching(false)
                hideSoftKeyboard(this@ChemicalEquationActivity)
                isOnSearchMode = false
            }

        })

        btn_chemical_equation_back!!.setOnClickListener { v -> finish() }
    }

    private fun bindData(equation: RO_ChemicalEquation) {
        txt_chem_equation!!.text = Html.fromHtml(equation.equation)
        txt_chem_condition!!.text = Html.fromHtml(equation.condition)

        title_phenonema.visibility = if (equation.phenonema != "") View.VISIBLE else View.INVISIBLE
        txt_chem_phenonema!!.text = Html.fromHtml(equation.phenonema)

        title_how_to_do.visibility = if (equation.how_to_do != "") View.VISIBLE else View.INVISIBLE
        txt_how_to_do!!.text = Html.fromHtml(equation.how_to_do)
    }

    override fun loadAnim() {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fade_in!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                chemical_equation_search_equation_search_view_parent!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        fade_out!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                chemical_equation_search_equation_search_view_parent!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }

    override fun setEdt_SearchAdvanceFunctions() {
        edt_chem_search!!.setOnClickListener { v ->
            if (!hasHiddenNavAndStatusBar) {
                makeFullScreenAfter(1000)
                hasHiddenNavAndStatusBar = true
            }
            if (!isOnSearchMode) {
                chemical_equation_search_equation_search_view_parent!!.startAnimation(fade_in)
                rcv_search_adapter!!.isSearching(true)
                showSofKeyboard()
                isOnSearchMode = true
            }
        }


        edt_chem_search!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasHiddenNavAndStatusBar) {
                makeFullScreenAfter(1000)
                hasHiddenNavAndStatusBar = true
            }
        }

        edt_chem_search!!.setOnHideVirtualKeyboardListener (object : VirtualKeyBoardSensor.OnHideVirtualKeyboardListener {
            override fun onHide() {
                makeFullScreen()
                hasHiddenNavAndStatusBar = false
            }
        })

    }

    override fun hideSoftKeyboard(activity: Activity) {
        virtualKeyboardManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.currentFocus).windowToken, 0)
    }

    override fun showSofKeyboard() {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    fun onGettingChemicalEquation(chemicalEquation: RO_ChemicalEquation?) {
        bindData(chemicalEquation!!)
    }

    fun onDataLoadedFromDatabase(chemicalEquations: ArrayList<RO_ChemicalEquation>) {
        rcv_search_adapter!!.setData(chemicalEquations)
    }

    companion object {

        val CHEMICAL_EQUATION = "Chemical_Equation"
    }
}
