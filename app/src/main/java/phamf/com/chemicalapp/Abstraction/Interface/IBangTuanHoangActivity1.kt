package phamf.com.chemicalapp.Abstraction.Interface

interface IBangTuanHoangActivity {
    /**
     * @see phamf.com.chemicalapp.BangTuanHoangActivity
     */
    interface View {
        fun addControl()

        fun addEvent()

        fun loadAnim()

        fun setPeriodicTableWidth()

        fun setTheme()
    }

    interface Presenter
}
