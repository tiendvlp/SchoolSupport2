//package phamf.com.chemicalapp.Presenter
//
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.arch.lifecycle.MutableLiveData
//
//import java.util.ArrayList
//
//import io.realm.RealmChangeListener
//import io.realm.RealmResults
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.IDPDPMenuActivity
//import phamf.com.chemicalapp.DPDPMenuActivity
//import phamf.com.chemicalapp.Database.OfflineDatabaseManager
//import phamf.com.chemicalapp.RO_Model.RO_DPDP
//import phamf.com.chemicalapp.Supporter.ROConverter
//
//import phamf.com.chemicalapp.Supporter.ROConverter.toRO_DPDPs_ArrayList
//
///** @see phamf.com.chemicalapp.DPDPMenuActivity
// */
//
//class DPDPMenuActivityPresenter(view: DPDPMenuActivity) : Presenter<DPDPMenuActivity>(view), IDPDPMenuActivity.Presenter {
//
//
//    private val offline_DB_manager: OfflineDatabaseManager
//
//    private var data: RealmResults<RO_DPDP>? = null
//
//    private var onDataLoadSuccess: OnDataLoadSuccess? = null
//
//    init {
//
//        offline_DB_manager = OfflineDatabaseManager(context)
//
//    }
//
//    override fun loadData() {
//        data = offline_DB_manager.readAsyncAllDataOf(RO_DPDP::class.java) { ro_dpdps -> onDataLoadSuccess!!.onDataLoadSuccess(toRO_DPDPs_ArrayList(ro_dpdps)) }
//    }
//
//    fun setOnDataLoadListener(onDataLoadListener: OnDataLoadSuccess) {
//        this.onDataLoadSuccess = onDataLoadListener
//    }
//
//    interface OnDataLoadSuccess {
//        fun onDataLoadSuccess(ro_dpdps: ArrayList<RO_DPDP>)
//    }
//
//}
