package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.realm.RealmChangeListener

import java.util.ArrayList

import io.realm.RealmResults
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.LessonMenuActivity
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_Lesson

/**
 * @see LessonMenuActivity
 */
class MVVM_LessonMenuActivityPresenter(application: Application) : AndroidViewModel(application), ILessonMenuActivity.Presenter {

    private var offlineDB_manager: OfflineDatabaseManager? = null

    private var recentLearningLessonDataManager: RecentLearningLessonDataManager? = null

    var l_data = MutableLiveData<RealmResults<RO_Chapter>>()

    private var data: RealmResults<RO_Chapter>? = null

    internal var application: Application? = application

    override fun loadData() {

        offlineDB_manager = OfflineDatabaseManager(application!!)

        recentLearningLessonDataManager = RecentLearningLessonDataManager(offlineDB_manager!!)
        // Call this function to create some data for RecentLearningLessonDataManager class,
        // look inside this method to get more info
        recentLearningLessonDataManager!!.getData(null)


        data = offlineDB_manager!!.readAsyncAllDataOf(RO_Chapter::class.java, RealmChangeListener {
            ro_chapters ->
            l_data.setValue(data)
        })

    }

    override fun pushCachingDataToDB() {
        recentLearningLessonDataManager!!.updateDB()
    }

    override fun clearAllListenerToDatabase() {
        data!!.removeAllChangeListeners()
    }

    /** Bring this lesson to top of recent learning lesson list in realm database   */
    override fun bringToTop(ro_lesson: RO_Lesson) {
        recentLearningLessonDataManager!!.bringToTop(ro_lesson)
    }


    /** @see LessonMenuActivity
     */
    interface DataLoadListener {

        fun onDataLoadedSuccess(ro_chapters: ArrayList<RO_Chapter>)

    }

}
