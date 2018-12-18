package phamf.com.chemicalapp.Abstraction.Interface

//import phamf.com.chemicalapp.Presenter.LessonActivityPresenter

interface ILessonActivity {
    /**
     * @see phamf.com.chemicalapp.LessonActivity
     */
    interface View {
        fun addControl()

        fun addEvent()

        fun setTheme()

        fun setUpViewCreator()
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.LessonActivityPresenter
     */
    interface Presenter {

//        fun setOnDataLoadListener(onDataLoadListener: LessonActivityPresenter.DataLoadListener)

        fun loadData()

    }

}
