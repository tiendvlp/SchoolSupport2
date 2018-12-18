//package phamf.com.chemicalapp.Presenter
//
//import android.app.Activity
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.os.Bundle
//import android.support.annotation.NonNull
//
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.IChemicalElementActivity
//import phamf.com.chemicalapp.BangTuanHoangActivity
//import phamf.com.chemicalapp.ChemicalElementActivity
//import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element
//
//
///**
// * @see phamf.com.chemicalapp.ChemicalElementActivity
// */
//class ChemicalElementActivityPresenter(var view: Application) : AndroidViewModel (view){
//
//    private var onDataReceived: OnDataReceived? = null
//
//    fun loadData(@NonNull activity : ChemicalElementActivity) {
//        // Don't check null to show error when we forget to register to this listener
//        onDataReceived!!.onDataReceived(ChemicalElementDataGetter(activity).data)
//    }
//
//    fun setOnDataReceived(onDataReceived: OnDataReceived) {
//        this.onDataReceived = onDataReceived
//    }
//
//    interface OnDataReceived {
//        fun onDataReceived(chemical_element: RO_Chemical_Element?)
//    }
//}
//
//internal class ChemicalElementDataGetter(var view: Activity) {
//
//    val data: RO_Chemical_Element?
//        get() {
//            val bundle = view.intent.extras
//            return bundle!!.getParcelable(BangTuanHoangActivity.CHEM_ELEMENT)
//        }
//}
