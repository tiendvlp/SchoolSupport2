package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import kotlinx.android.synthetic.main.activity_online_search_chemical_equation.*
import phamf.com.chemicalapp.Adapter.Search_CE_RCV_Adapter
import phamf.com.chemicalapp.Manager.OnReachASearch
import phamf.com.chemicalapp.Manager.OnlineSearchChemEquaManager
import phamf.com.chemicalapp.ViewModel.MVVM_OnlineSearchChemicalEquationActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.Supporter.removeAllSpace
import phamf.com.chemicalapp.Model.SupportModel.OnlineSearchChemicalEquationDataGetter
/**
 * @see OnlineSearchChemicalEquationDataGetter
 */
class OnlineSearchChemicalEquationActivity : AppCompatActivity() {

    companion object {
        val EQUATION = "onl_equa"
    }

    lateinit var onlineSearchManager : OnlineSearchChemEquaManager

    lateinit var onl_sch_chem_list_adapter : Search_CE_RCV_Adapter

    lateinit var viewModel : MVVM_OnlineSearchChemicalEquationActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_search_chemical_equation)
        initRecyclerView()

        initSearchView()

        initViewModel()

        addEvent()

        viewModel.loadData(this)
    }

    fun addEvent () {
        onl_sch_eq_btn_search.setOnClickListener {
            onl_sch_progress_bar.visibility = View.VISIBLE
            var link = "https://phuongtrinhhoahoc.com/?chat_tham_gia="
                    .plus(onl_sch_eq_edt_adding_chems.text.toString().removeAllSpace())
                    .plus("&chat_san_pham=")
                    .plus(onl_sch_eq_edt_result_chems.text.toString().removeAllSpace())

            onlineSearchManager.search(link)
        }

        onl_sch_chem_list_adapter.setOnItemClickListener(object : Search_CE_RCV_Adapter.OnItemClickListener {
            override fun OnItemClickListener(view: View, equation: RO_ChemicalEquation, position: Int) {
                val intent = Intent(this@OnlineSearchChemicalEquationActivity, ChemicalEquationActivity::class.java)
                intent.putExtra(ChemicalEquationActivity.CHEMICAL_EQUATION, equation)
                startActivity(intent)
            }
        })

        onl_sch_eq_btn_back2.setOnClickListener { finish() }
    }

    fun initSearchView () {
        var onReachASearch : OnReachASearch = {
            if (it != null) {
                onl_sch_chem_list_adapter.list!!.addAll(it)
            }
        }

        onlineSearchManager = OnlineSearchChemEquaManager(onReachASearch) {
            onl_sch_chem_list_adapter.notifyDataSetChanged()
            onl_sch_progress_bar.visibility = GONE
        }

    }

    fun initViewModel () {
        viewModel = ViewModelProviders.of(this).get(MVVM_OnlineSearchChemicalEquationActivityPresenter::class.java)
        viewModel.online_equation.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                onl_sch_eq_edt_adding_chems.setText(t)
            }
        })
    }


    fun initRecyclerView () {
        onl_sch_chem_list_adapter = Search_CE_RCV_Adapter(this)
        onl_sch_chem_list_adapter.adaptFor(onl_sch_eq_rcv_chemical_equations)
    }
}
