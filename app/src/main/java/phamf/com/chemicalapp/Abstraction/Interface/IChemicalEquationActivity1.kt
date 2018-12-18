package phamf.com.chemicalapp.Abstraction.Interface

import android.app.Activity

//import phamf.com.chemicalapp.Presenter.ChemicalEquationActivityPresenter

interface IChemicalEquationActivity {
    /**
     * @see phamf.com.chemicalapp.ChemicalEquationActivity
     */

    interface View {

        fun setUpNecessaryInfos()

        fun addControl()

        fun addEvent()

        fun loadAnim()

        fun setEdt_SearchAdvanceFunctions()

        fun hideSoftKeyboard(activity: Activity)

        fun showSofKeyboard()

    }


    /**
     * @see phamf.com.chemicalapp.Presenter.ChemicalEquationActivityPresenter
     */
    interface Presenter {
        fun loadData()

//        fun setOnDataLoadedListener(onDataLoadedListener: ChemicalEquationActivityPresenter.OnDataLoadedListener)
    }
}
