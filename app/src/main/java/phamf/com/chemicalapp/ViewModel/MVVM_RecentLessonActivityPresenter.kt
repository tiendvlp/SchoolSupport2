package phamf.com.chemicalapp.ViewModel


import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

import java.util.ArrayList

import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.RecentLessonsActivity

/**
 * @see RecentLessonsActivity
 */
class MVVM_RecentLessonActivityPresenter(view: Application) : AndroidViewModel(view) {

    internal var offline_DBManager: OfflineDatabaseManager

    internal lateinit var recentLearningLessonDataManager: RecentLearningLessonDataManager

    var ldt_recent_lessons = MutableLiveData<ArrayList<RO_Lesson>>()

    init {
        offline_DBManager = OfflineDatabaseManager(view)
    }

    fun loadData() {
        recentLearningLessonDataManager = RecentLearningLessonDataManager(offline_DBManager)
        recentLearningLessonDataManager.getData (object : RecentLearningLessonDataManager.OnGetDataSuccess {
            override fun onLoadSuccess(recent_Ces: ArrayList<RO_Lesson>) {
                ldt_recent_lessons.setValue(recent_Ces)
            }
        })
    }

    fun bringToTop(item: RO_Lesson) {
        recentLearningLessonDataManager.bringToTop(item)
    }

    fun pushCachingData() {
        recentLearningLessonDataManager.updateDB()
    }

}