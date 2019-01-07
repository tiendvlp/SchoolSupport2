package phamf.com.chemicalapp.Model.SupportModel

import android.app.Activity
import phamf.com.chemicalapp.ChemicalEquationActivity.Companion.CHEMICAL_EQUATION
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation

internal class ChemicalEquationDataGetter(var view: Activity) {

    val data: RO_ChemicalEquation?
        get() {
            val bundle = view.intent.extras
            return bundle!!.getParcelable(CHEMICAL_EQUATION)
        }
}