package phamf.com.chemicalapp.Model.SupportModel

import android.app.Activity
import phamf.com.chemicalapp.SpecialGraphMenuActivity.Companion.GRAPH_INFO
import phamf.com.chemicalapp.ViewModel.GraphInfo

/**
 * @see phamf.com.chemicalapp.ViewModel.MVVM_GraphDrawerActivityPresenter
 */
internal class GraphDrawerDataGetter(var view: Activity) {
    val data: GraphInfo
        get() {
            val bundle = view.intent.extras
            return bundle.getParcelable(GRAPH_INFO)
        }
}