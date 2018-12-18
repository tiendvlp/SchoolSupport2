package phamf.com.chemicalapp.Abstraction.Interface


import io.realm.RealmResults
import phamf.com.chemicalapp.LessonMenuActivity
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_Lesson

interface ILessonMenuActivity {

    /**
     * @see phamf.com.chemicalapp.LessonMenuActivity
     */

    interface View {

        fun addControl()

        fun addEvent()

        fun setTheme()

        fun loadAnim()
    }

    /**
     * @see phamf.com.chemicalapp.Presenter.LessonMenuActivityPresenter
     */
    interface Presenter {

        fun loadData()

        fun pushCachingDataToDB()

        /** Bring lesson to top of recent learning lesson list in realm database   */
        fun bringToTop(ro_lesson: RO_Lesson)

        fun clearAllListenerToDatabase()
    }

}
