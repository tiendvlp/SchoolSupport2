package phamf.com.chemicalapp.Abstraction.Interface

//import phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter

interface IChemicalElementActivity {
    /**
     * @see phamf.com.chemicalapp.ChemicalElementActivity
     */
    interface View {
        fun createNeccesaryInfo()

        fun addControl()

        fun addEvent()

        fun loadAnimation()

        fun showSoftKeyboard()

        fun hideSoftKeyboard()
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.ChemicalElementActivityPresenter
     */
    interface Presenter {
        fun loadData()

//        fun setOnDataReceived(onDataReceived: ChemicalElementActivityPresenter.OnDataReceived)
    }
}
