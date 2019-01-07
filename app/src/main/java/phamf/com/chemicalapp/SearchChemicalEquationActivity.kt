package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import kotlinx.android.synthetic.main.activity_search_chemical_equation.*
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.ViewModel.MVVM_SearchChemicalEquationActivityPresenter


/**
 * @see phamf.com.chemicalapp.ViewModel.MVVM_SearchChemicalEquationActivityPresenter
 */
class SearchChemicalEquationActivity : FullScreenActivity() {

    var isOnSearchMode : Boolean = false

    lateinit var viewModel : MVVM_SearchChemicalEquationActivityPresenter

    lateinit var chem_eq_rcv_adapter : Search_CE_RCV_Adapter

    lateinit var keyboardManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_chemical_equation)

        createManagers()

        createViewModel()

        createSearchView()

        addEvent()

        sch_eq_edt_search_chem_eq.clearFocus()
    }

    private fun createManagers () {
        keyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun createViewModel () {
        viewModel = ViewModelProviders.of(this).get(MVVM_SearchChemicalEquationActivityPresenter::class.java)
    }

    private fun addEvent () {

        sch_eq_edt_search_chem_eq.setOnClickListener {
            if (!isOnSearchMode) {
                showKeyboard()
                isOnSearchMode = true
            }
        }

        sch_eq_edt_search_chem_eq.setOnHideVirtualKeyboardListener (object : VirtualKeyBoardSensor.OnHideVirtualKeyboardListener {
            override fun onHide() {
                isOnSearchMode = false
                makeFullScreen()
            }
        })

        sch_eq_btn_cancel_search.setOnClickListener {
            if (isOnSearchMode) {
                hideKeyboard()
                makeFullScreen()
                isOnSearchMode = false
            }
        }

        sch_eq_btn_back.setOnClickListener {
            finish()
        }

        sch_eq_btn_search_online.setOnClickListener {
            var intent = Intent(this@SearchChemicalEquationActivity, OnlineSearchChemicalEquationActivity::class.java)
            intent.putExtra(OnlineSearchChemicalEquationActivity.EQUATION, sch_eq_edt_search_chem_eq.text.toString())
            startActivity(intent)
        }

    }

    /***/
    private fun createSearchView () {

        fun createAdapterProperties () {
            chem_eq_rcv_adapter = Search_CE_RCV_Adapter(this)
            chem_eq_rcv_adapter.adaptFor(sch_eq_rcv_chemical_equations)
            chem_eq_rcv_adapter.observe(sch_eq_edt_search_chem_eq)
        }

        fun setEvent () {
            sch_eq_edt_search_chem_eq.setOnClickListener {
                if (isOnSearchMode) {
                    isOnSearchMode = false

                }
            }

            chem_eq_rcv_adapter.setOnItemClickListener(object : Search_CE_RCV_Adapter.OnItemClickListener {

                override fun OnItemClickListener(view: View, equation: RO_ChemicalEquation, position: Int) {
                    // Do this to bring the just chosen equation to top of the list
                    chem_eq_rcv_adapter.list!!.remove(equation)
                    chem_eq_rcv_adapter.list!!.add(0, equation)
                    chem_eq_rcv_adapter.notifyDataSetChanged()
                    // Update the top in database
                    viewModel.bringToTop(equation)

                    val intent = Intent(this@SearchChemicalEquationActivity, ChemicalEquationActivity::class.java)
                    intent.putExtra(ChemicalEquationActivity.CHEMICAL_EQUATION, equation)
                    startActivity(intent)
                }

            })

            chem_eq_rcv_adapter.setOnSearchListEmptyListener {
                sch_eq_btn_search_online.visibility = View.VISIBLE
            }

            chem_eq_rcv_adapter.set_OnSearchListHasElement {
                sch_eq_btn_search_online.visibility = View.GONE
            }
        }

        fun listenToViewModel_LiveData () {
            viewModel.ldt_ro_chemicalEquation.observe(this, Observer {
                chem_eq_rcv_adapter.setData(it!!)
            })
        }


        createAdapterProperties()
        setEvent()
        listenToViewModel_LiveData()
        viewModel.loadData()
    }

    private fun showKeyboard () {
        keyboardManager.toggleSoftInput(0, SHOW_IMPLICIT)
    }

    private fun hideKeyboard () {
        keyboardManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

}
