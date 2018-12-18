package phamf.com.chemicalapp.Model.SupportModel

import android.app.Activity
import android.os.Bundle

import phamf.com.chemicalapp.BangTuanHoangActivity
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element

class ChemicalElementDataGetter(internal var view: Activity) {

    val data: RO_Chemical_Element?
        get() {
            val bundle = view.intent.extras
            return bundle!!.getParcelable(BangTuanHoangActivity.CHEM_ELEMENT)
        }
}
