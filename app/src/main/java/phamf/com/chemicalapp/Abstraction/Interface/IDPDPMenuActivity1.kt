package phamf.com.chemicalapp.Abstraction.Interface

interface IDPDPMenuActivity {
    /**
     * @see phamf.com.chemicalapp.DPDPMenuActivity
     */
    interface View {

        fun addControl()

        fun addEvent()

        fun setTheme()
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.DPDPMenuActivityPresenter
     */
    interface Presenter {
        fun loadData()
    }
}
