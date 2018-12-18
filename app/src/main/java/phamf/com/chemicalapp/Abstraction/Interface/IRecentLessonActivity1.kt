package phamf.com.chemicalapp.Abstraction.Interface

//import phamf.com.chemicalapp.Presenter.RecentLessonActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_Lesson


interface IRecentLessonActivity {
    /**
     * @see phamf.com.chemicalapp.RecentLessonsActivity
     */
    interface View {
        fun addControl()

        fun addEvent()

        fun setTheme()
    }

    /**
     * @see RecentLessonActivityPresenter
     */
    interface Presenter {
        fun loadData()

//        fun setOnDataLoadListener(onDataLoadListener: RecentLessonActivityPresenter.DataLoadListener)

        fun bringToTop(item: RO_Lesson)

        fun pushCachingData()
    }
}
