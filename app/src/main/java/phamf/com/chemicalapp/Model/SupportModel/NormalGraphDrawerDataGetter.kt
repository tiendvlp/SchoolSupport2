package phamf.com.chemicalapp.Model.SupportModel

import android.app.Activity
import android.util.Log
import phamf.com.chemicalapp.NormalGraphDrawerActivity
import phamf.com.chemicalapp.SpecialGraphMenuActivity
import phamf.com.chemicalapp.ViewModel.GraphInfo

/**
 * @see phamf.com.chemicalapp.ViewModel.MVVM_NormalGraphDrawerActivityPresenter
 * @see phamf.com.chemicalapp.GraphMenu
 * @see NormalGraphDrawerActivity
 */
internal class NormalGraphDrawerDataGetter(var view: Activity) {
    val data: ArrayList<GraphInfo>
        get() {
            val bundle = view.intent.extras
            return bundle.getParcelableArrayList<GraphInfo>(NormalGraphDrawerActivity.GRAPH_INFO)
        }
}
