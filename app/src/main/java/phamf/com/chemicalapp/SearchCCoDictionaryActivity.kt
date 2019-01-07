package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_cco_dictionary.*
import phamf.com.chemicalapp.Adapter.Search_CCo_RCV_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Composition
import phamf.com.chemicalapp.ViewModel.MVVM_SearchCCoDictionaryActivityPresenter

/**
 * @see phamf.com.chemicalapp.ViewModel.MVVM_SearchCElDictionaryActivity
 */
class SearchCCoDictionaryActivity : FullScreenActivity() {

    var isOnSearchMode : Boolean = false

    lateinit var viewModel : MVVM_SearchCCoDictionaryActivityPresenter

    lateinit var chem_cco_rcv_adapter : Search_CCo_RCV_Adapter

    lateinit var keyboardManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cco_dictionary)

        createManagers()

        createViewModel()

        createSearchView()

        addEvent()

        sch_cco_edt_search_cco.clearFocus()
    }

    private fun createManagers () {
        keyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun createViewModel () {
        viewModel = ViewModelProviders.of(this).get(MVVM_SearchCCoDictionaryActivityPresenter::class.java)
    }

    private fun addEvent () {

        sch_cco_edt_search_cco.setOnClickListener {
            if (!isOnSearchMode) {
                showKeyboard()
                isOnSearchMode = true
            }
        }

        sch_cco_edt_search_cco.setOnHideVirtualKeyboardListener (object : VirtualKeyBoardSensor.OnHideVirtualKeyboardListener {
            override fun onHide() {
                isOnSearchMode = false
                makeFullScreen()
            }
        })

        sch_cco_btn_cancel_search.setOnClickListener {
            if (isOnSearchMode) {
                hideKeyboard()
                makeFullScreen()
                isOnSearchMode = false
            }
        }

        sch_cco_btn_back.setOnClickListener {
            finish()
        }

    }

    /***/
    private fun createSearchView () {

        fun createAdapterProperties () {
            chem_cco_rcv_adapter = Search_CCo_RCV_Adapter(this)
            chem_cco_rcv_adapter.adaptFor(sch_cco_rcv_chemical_composition)
            chem_cco_rcv_adapter.observe(sch_cco_edt_search_cco)
        }

        fun setEvent () {
            sch_cco_edt_search_cco.setOnClickListener {
                if (isOnSearchMode) {
                    isOnSearchMode = false
                }
            }

            chem_cco_rcv_adapter.setOnItemClickListener(object : Search_CCo_RCV_Adapter.OnItemClickListener {

                override fun OnItemClickListener(view: View, composition: RO_Chemical_Composition, position: Int) {
                    // Do this to bring the just chosen composition to top of the list
                    chem_cco_rcv_adapter.list!!.remove(composition)
                    chem_cco_rcv_adapter.list!!.add(0, composition)
                    chem_cco_rcv_adapter.notifyDataSetChanged()
                    // Update the top in database
                    viewModel.bringToTop(composition)

                    val intent = Intent(this@SearchCCoDictionaryActivity, ChemicalCompositionActivity::class.java)
                    intent.putExtra(ChemicalCompositionActivity.CHEMICAL_COMPOSITION, composition)
                    startActivity(intent)
                }

            })
        }

        fun listenToViewModel_LiveData () {
            viewModel.ldt_ro_chemicalComposition.observe(this, Observer {
                chem_cco_rcv_adapter.setData(it!!)
            })
        }

        createAdapterProperties()
        setEvent()
        listenToViewModel_LiveData()
        viewModel.loadData()
    }

    private fun showKeyboard () {
        keyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard () {
        keyboardManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

}
