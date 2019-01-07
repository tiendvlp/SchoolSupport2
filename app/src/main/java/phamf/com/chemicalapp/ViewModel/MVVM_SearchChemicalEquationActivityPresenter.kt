package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.realm.RealmChangeListener
import io.realm.RealmResults
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.Database.UpdateDatabaseManager
import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
import phamf.com.chemicalapp.Model.SupportModel.ThemeUpdater
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import java.util.ArrayList


/**
 * @see phamf.com.chemicalapp.SearchChemicalEquationActivity
 * @see phamf.com.chemicalapp.ViewModel.MVVM_MainActivityPresenter
 */
class MVVM_SearchChemicalEquationActivityPresenter(application: Application) : AndroidViewModel(application){

    private val offlineDB_manager: OfflineDatabaseManager

    private var recentSearching_ce_data_manager: RecentSearching_CE_Data_Manager? = null

    var ldt_ro_chemicalEquation = MutableLiveData<ArrayList<RO_ChemicalEquation>>()

    init {
        offlineDB_manager = OfflineDatabaseManager(application)
    }

    fun loadData () {

        recentSearching_ce_data_manager = RecentSearching_CE_Data_Manager(offlineDB_manager)
        recentSearching_ce_data_manager!!.getData(object : RecentSearching_CE_Data_Manager.OnGetDataSuccess {
            override fun onLoadSuccess(all_ro_chemical_equations : ArrayList<RO_ChemicalEquation>) {
                ldt_ro_chemicalEquation.value = all_ro_chemical_equations
            }

        })

    }

    /** Update recent searching chemical equation list into database  */
    fun pushCachingDataToDB() {
        recentSearching_ce_data_manager!!.updateDB()
    }

    /** Bring this Chemical Equation to top of recent learning lesson list in realm database   */
    fun bringToTop(ro_ce: RO_ChemicalEquation) {
        recentSearching_ce_data_manager!!.bringToTop(ro_ce)
    }

}