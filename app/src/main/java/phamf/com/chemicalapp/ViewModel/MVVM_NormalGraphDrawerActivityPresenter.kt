package phamf.com.chemicalapp.ViewModel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import phamf.com.chemicalapp.GraphDrawerActivity
import phamf.com.chemicalapp.Model.SupportModel.GraphDrawerDataGetter
import phamf.com.chemicalapp.Model.SupportModel.NormalGraphDrawerDataGetter
import phamf.com.chemicalapp.NormalGraphDrawerActivity

/**
 * @see NormalGraphDrawerActivity
 */

class MVVM_NormalGraphDrawerActivityPresenter(var view : Application) {

    var ldt_graph_info : MutableLiveData<ArrayList<GraphInfo>> = MutableLiveData()

    fun loadData (activity : NormalGraphDrawerActivity) {
        ldt_graph_info.value = NormalGraphDrawerDataGetter(activity).data
    }
}

