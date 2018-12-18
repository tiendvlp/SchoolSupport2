package phamf.com.chemicalapp.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import phamf.com.chemicalapp.ChemicalElementActivity
import phamf.com.chemicalapp.Model.SupportModel.ChemicalElementDataGetter
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element


/**
 * @see phamf.com.chemicalapp.ChemicalElementActivity
 */
class MVVM_ChemicalElementActivityPresenter : ViewModel() {

    var ldt_chemical_element = MutableLiveData<RO_Chemical_Element>()

    fun loadData(activity: ChemicalElementActivity) {
        // Don't check null to show error when we forget to register to this listener
        ldt_chemical_element.value = ChemicalElementDataGetter(activity).data
    }

}

