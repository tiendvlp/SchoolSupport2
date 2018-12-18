package phamf.com.chemicalapp.Manager

import android.util.Log

import java.util.ArrayList

import io.realm.RealmChangeListener
import io.realm.RealmList
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCEs

class RecentSearching_CE_Data_Manager(private val offline_DBManager: OfflineDatabaseManager) {

    private var recent_searchingCEs: Recent_SearchingCEs? = null

    private var recent_CEs: RealmList<RO_ChemicalEquation>? = null

    init {


        // Afraid of this code block is executed after the one beneath
        if (offline_DBManager.readOneOf(Recent_SearchingCEs::class.java) == null) {
            val recent_searchingCEs = Recent_SearchingCEs()
            offline_DBManager.addOrUpdateDataOf(Recent_SearchingCEs::class.java, recent_searchingCEs)
        }

    }


    /**
     * This function works as follow:
     * Fisrt if recent ce data is null, add all chemical equation of database into it
     * Then in uses process, the order of data of recent ce data would be changed
     * when user search a chemical equation. Then when user exits app, that order will be saved in database
     * The next time user uses this app. The app load that order and bind to search list
     */

    fun getData(onGetDataSuccess: OnGetDataSuccess) {
        recent_searchingCEs = offline_DBManager.readAsyncOneOf(Recent_SearchingCEs::class.java, object : RealmChangeListener<Recent_SearchingCEs> {
            override fun onChange(t: Recent_SearchingCEs) {
                recent_CEs = recent_searchingCEs!!.recent_searching_ces

                // Check if recen_Ces null, then add all default Chemical equations
                if (recent_CEs!!.size == 0) {
                    offline_DBManager.beginTransaction()
                    recent_CEs!!.addAll(offline_DBManager.readAllDataOf(RO_ChemicalEquation::class.java))
                    offline_DBManager.commitTransaction()
                }

                val data = ArrayList<RO_ChemicalEquation>()
                data.addAll(recent_CEs!!)

                // This function is implemented by MainActivityPresenter.java
                onGetDataSuccess.onLoadSuccess(data)
            }

        })
    }

    fun bringToTop(ro_ce: RO_ChemicalEquation) {

        if (recent_CEs!!.contains(ro_ce) && recent_CEs!![0] != ro_ce) {

            offline_DBManager.beginTransaction()

            recent_CEs!!.remove(ro_ce)
            recent_CEs!!.add(0, ro_ce)

            offline_DBManager.commitTransaction()

        } else {
            Log.e("Error happened", "ro_ce not found in list, MainActivityPresenter.java, line 139 " + recent_searchingCEs!!.recent_searching_ces.size)
        }

    }

    fun updateDB() {
        offline_DBManager.addOrUpdateDataOf(Recent_SearchingCEs::class.java, recent_searchingCEs!!)
    }

    interface OnGetDataSuccess {
        fun onLoadSuccess(recent_Ces: ArrayList<RO_ChemicalEquation>)
    }
}
