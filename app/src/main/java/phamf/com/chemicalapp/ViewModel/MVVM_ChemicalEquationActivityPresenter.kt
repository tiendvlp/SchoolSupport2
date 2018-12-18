package phamf.com.chemicalapp.ViewModel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.NonNull

import java.util.ArrayList

import phamf.com.chemicalapp.ChemicalEquationActivity
import phamf.com.chemicalapp.ChemicalEquationActivity.Companion.CHEMICAL_EQUATION
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation

/**
 * @see ChemicalEquationActivity
 */
class MVVM_ChemicalEquationActivityPresenter(view: Application) : AndroidViewModel(view){

    private val offlineDatabaseManager: OfflineDatabaseManager = OfflineDatabaseManager(view)

    var ldt_ro_recent_ces : MutableLiveData<ArrayList<RO_ChemicalEquation>> = MutableLiveData()

    var ldt_ro_chem_equation : MutableLiveData<RO_ChemicalEquation?> = MutableLiveData()

    fun loadData(@NonNull activity : ChemicalEquationActivity) {

        // Get the chemical equation sent by MainActivity
        ldt_ro_chem_equation.value = ChemicalEquationDataGetter(activity).data
        // Decide load data in constructor to ensure that
        RecentSearching_CE_Data_Manager(offlineDatabaseManager).getData (object : RecentSearching_CE_Data_Manager.OnGetDataSuccess {
            override fun onLoadSuccess(recent_Ces: ArrayList<RO_ChemicalEquation>) {
                ldt_ro_recent_ces.value = recent_Ces
            }
        })
    }

}

internal class ChemicalEquationDataGetter(var view: Activity) {

    val data: RO_ChemicalEquation?
        get() {
            val bundle = view.intent.extras
            return bundle!!.getParcelable(CHEMICAL_EQUATION)
        }
}
