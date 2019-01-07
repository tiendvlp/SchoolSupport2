package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import phamf.com.chemicalapp.Model.SupportModel.OnlineSearchChemicalEquationDataGetter
import phamf.com.chemicalapp.OnlineSearchChemicalEquationActivity

/**
 * @see OnlineSearchChemicalEquationActivity
 */
class MVVM_OnlineSearchChemicalEquationActivityPresenter(application: Application) : AndroidViewModel(application) {

    val online_equation : MutableLiveData<String> = MutableLiveData()

    fun loadData (view: OnlineSearchChemicalEquationActivity) {
        online_equation.value = OnlineSearchChemicalEquationDataGetter(view).data
    }

}