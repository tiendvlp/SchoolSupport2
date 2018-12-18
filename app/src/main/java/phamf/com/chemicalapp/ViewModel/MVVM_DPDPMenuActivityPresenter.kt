package phamf.com.chemicalapp.ViewModel


import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.realm.RealmChangeListener

import java.util.ArrayList

import io.realm.RealmResults
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_DPDP

import phamf.com.chemicalapp.Supporter.ROConverter.toRO_DPDPs_ArrayList

/**
 * @see phamf.com.chemicalapp.DPDPMenuActivity
 */
class MVVM_DPDPMenuActivityPresenter(view: Application) : AndroidViewModel(view) {


    private val offline_DB_manager: OfflineDatabaseManager

    private var data: RealmResults<RO_DPDP>? = null

    var ldt_ro_dpdp = MutableLiveData<ArrayList<RO_DPDP>>()

    init {
        offline_DB_manager = OfflineDatabaseManager(view.baseContext)
    }

    fun loadData() {

        data = offline_DB_manager.readAsyncAllDataOf(RO_DPDP::class.java, object : RealmChangeListener<RealmResults<RO_DPDP>> {
            override fun onChange(ro_DPDPs : RealmResults<RO_DPDP>) {
                ldt_ro_dpdp.value = toRO_DPDPs_ArrayList(ro_DPDPs)
            }

        } )
    }

}