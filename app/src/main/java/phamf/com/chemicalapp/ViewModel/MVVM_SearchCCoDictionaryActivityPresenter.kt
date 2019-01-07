package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.Manager.RecentSearching_CCo_Data_Manager
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Composition
import java.util.ArrayList

/**
 * @see phamf.com.chemicalapp.SearchCCoDictionaryActivity
 */

class MVVM_SearchCCoDictionaryActivityPresenter(application: Application) : AndroidViewModel(application){

    private val offlineDB_manager: OfflineDatabaseManager

    private var recentSearching_ce_data_manager: RecentSearching_CCo_Data_Manager? = null

    var ldt_ro_chemicalComposition = MutableLiveData<ArrayList<RO_Chemical_Composition>>()

    init {
        offlineDB_manager = OfflineDatabaseManager(application)
    }

    fun loadData () {

        recentSearching_ce_data_manager = RecentSearching_CCo_Data_Manager(offlineDB_manager)
        recentSearching_ce_data_manager!!.getData(object : RecentSearching_CCo_Data_Manager.OnGetDataSuccess {
            override fun onLoadSuccess(all_ro_chemical_Compositions : ArrayList<RO_Chemical_Composition>) {
                ldt_ro_chemicalComposition.value = all_ro_chemical_Compositions
            }
        })

    }

    /** Update recent searching chemical Composition list into database  */
    fun pushCachingDataToDB() {
        recentSearching_ce_data_manager!!.updateDB()
    }

    /** Bring this Chemical Composition to top of recent learning lesson list in realm database   */
    fun bringToTop(ro_ce: RO_Chemical_Composition) {
        recentSearching_ce_data_manager!!.bringToTop(ro_ce)
    }

}