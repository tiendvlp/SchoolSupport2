package phamf.com.chemicalapp.Manager

import android.util.Log

import java.util.ArrayList

import io.realm.RealmChangeListener
import io.realm.RealmList
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Composition
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCCo

class RecentSearching_CCo_Data_Manager(private val offline_DBManager: OfflineDatabaseManager) {

    private var recent_searchingCCo: Recent_SearchingCCo? = null

    private var recent_CCo: RealmList<RO_Chemical_Composition>? = null

    init {


        // Afraid of this code block is executed after the one beneath
        if (offline_DBManager.readOneOf(Recent_SearchingCCo::class.java) == null) {
            val recent_searchingCCo = Recent_SearchingCCo()
            offline_DBManager.addOrUpdateDataOf(Recent_SearchingCCo::class.java, recent_searchingCCo)
        }

    }


    /**
     * This function works as follow:
     * Fisrt if recent ce data is null, add all chemical composition of database into it
     * Then in uses process, the order of data of recent ce data would be changed
     * when user search a chemical compositionn. Then when user exits app, that order will be saved in database
     * The next time user uses this app. The app load that order and bind to search list
     */

    fun getData(onGetDataSuccess: OnGetDataSuccess) {
        recent_searchingCCo = offline_DBManager.readAsyncOneOf(Recent_SearchingCCo::class.java, object : RealmChangeListener<Recent_SearchingCCo> {
            override fun onChange(t: Recent_SearchingCCo) {
                recent_CCo = recent_searchingCCo!!.recent_searching_cco

                // Check if recen_Ces null, then add all default Chemical compositions
                if (recent_CCo!!.size == 0) {
                    offline_DBManager.beginTransaction()
                    recent_CCo!!.addAll(offline_DBManager.readAllDataOf(RO_Chemical_Composition::class.java))
                    offline_DBManager.commitTransaction()
                }

                val data = ArrayList<RO_Chemical_Composition>()
                data.addAll(recent_CCo!!)

                // This function is implemented by MainActivityPresenter.java
                onGetDataSuccess.onLoadSuccess(data)
            }

        })
    }

    fun bringToTop(ro_ce: RO_Chemical_Composition) {

        if (recent_CCo!!.contains(ro_ce) && recent_CCo!![0] != ro_ce) {

            offline_DBManager.beginTransaction()

            recent_CCo!!.remove(ro_ce)
            recent_CCo!!.add(0, ro_ce)

            offline_DBManager.commitTransaction()

        } else {
            Log.e("Error happened", "ro_cco not found in list, RecentSearching_CCo_Data_Manager.java, line 73 " + recent_searchingCCo!!.recent_searching_cco.size)
        }

    }

    fun updateDB() {
        offline_DBManager.addOrUpdateDataOf(Recent_SearchingCCo::class.java, recent_searchingCCo!!)
    }

    interface OnGetDataSuccess {
        fun onLoadSuccess(recent_CCo: ArrayList<RO_Chemical_Composition>)
    }
}
