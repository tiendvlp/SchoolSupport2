package phamf.com.chemicalapp.Model.SupportModel

import phamf.com.chemicalapp.OnlineSearchChemicalEquationActivity

class OnlineSearchChemicalEquationDataGetter(var view : OnlineSearchChemicalEquationActivity) {
    var data : String = ""
    get() {
        var bundle = view.intent.extras
        return bundle.getString(OnlineSearchChemicalEquationActivity.EQUATION, "")
    }
}