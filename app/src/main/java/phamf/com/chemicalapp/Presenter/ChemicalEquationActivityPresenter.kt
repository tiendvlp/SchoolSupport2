//package phamf.com.chemicalapp.Presenter
//
//import android.app.Activity
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.os.Bundle
//
//import java.util.ArrayList
//
//import io.realm.RealmList
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.IChemicalEquationActivity
//import phamf.com.chemicalapp.ChemicalEquationActivity
//import phamf.com.chemicalapp.Database.OfflineDatabaseManager
//import phamf.com.chemicalapp.Manager.RecentSearching_CE_Data_Manager
//import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
//
//import phamf.com.chemicalapp.ChemicalEquationActivity.CHEMICAL_EQUATION
//
///**
// * @see ChemicalEquationActivity
// */
//class ChemicalEquationActivityPresenter(view: Application) : AndroidViewModel(view){
//
//    private var onDataLoadedListener: OnDataLoadedListener? = null
//
//    private val offlineDatabaseManager: OfflineDatabaseManager
//
//    init {
//        offlineDatabaseManager = OfflineDatabaseManager(view)
//    }
//
//    fun loadData() {
//
//        onDataLoadedListener!!.onGettingChemicalEquation(ChemicalEquationDataGetter(view).data)
//        // Decide load data in constructor to ensure that
//        RecentSearching_CE_Data_Manager(offlineDatabaseManager)
//                .getData { recent_Ces -> onDataLoadedListener!!.onDataLoadedFromDatabase(recent_Ces) }
//    }
//
//    fun setOnDataLoadedListener(onDataLoadedListener: OnDataLoadedListener) {
//        this.onDataLoadedListener = onDataLoadedListener
//    }
//
//    interface OnDataLoadedListener {
//
//        fun onGettingChemicalEquation(chemicalEquation: RO_ChemicalEquation?)
//
//        fun onDataLoadedFromDatabase(chemicalEquations: ArrayList<RO_ChemicalEquation>)
//    }
//}
//
//internal class ChemicalEquationDataGetter(var view: Activity) {
//
//    val data: RO_ChemicalEquation?
//        get() {
//            val bundle = view.intent.extras
//            return bundle!!.getParcelable(CHEMICAL_EQUATION)
//        }
//}
