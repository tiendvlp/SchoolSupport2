package phamf.com.chemicalapp.Abstraction.Interface

//import phamf.com.chemicalapp.Presenter.DPDPActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule

interface IDPDPActivity {
    /**
     * @see phamf.com.chemicalapp.DPDPActivity
     */
    interface View {
        fun addControl()

        fun addAnimation()

        fun addEvent()

        fun setUpViewCreator()

        fun setTheme()
    }

    /**
     * @see DPDPActivityPresenter
     */
    interface Presenter {
        fun loadData()

        fun convertContent(orM: RO_OrganicMolecule): String

        fun convertObjectToData(dpdp: RO_DPDP)

//        fun setOnDataLoadListener(onDataLoadListener: DPDPActivityPresenter.DataLoadListener)
    }
}
