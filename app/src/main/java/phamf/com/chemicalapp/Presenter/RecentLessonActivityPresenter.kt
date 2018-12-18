//package phamf.com.chemicalapp.Presenter
//
//import java.util.ArrayList
//
//import io.realm.RealmList
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity
//import phamf.com.chemicalapp.Database.OfflineDatabaseManager
//import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager
//import phamf.com.chemicalapp.RO_Model.RO_Lesson
//import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons
//import phamf.com.chemicalapp.RecentLessonsActivity
//
//
///**
// * @see RecentLessonsActivity
// */
//class RecentLessonActivityPresenter(view: RecentLessonsActivity) : Presenter<RecentLessonsActivity>(view), IRecentLessonActivity {
//
//    private var onDataLoadListener: DataLoadListener? = null
//
//    internal var offline_DBManager: OfflineDatabaseManager
//
//    internal var recentLearningLessonDataManager: RecentLearningLessonDataManager
//
//
//    init {
//        offline_DBManager = OfflineDatabaseManager(view)
//    }
//
//    fun loadData() {
//        recentLearningLessonDataManager = RecentLearningLessonDataManager(offline_DBManager)
//        recentLearningLessonDataManager.getData { recent_Ces -> onDataLoadListener!!.onDataLoadSuccess(recent_Ces) }
//    }
//
//    fun setOnDataLoadListener(onDataLoadListener: DataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener
//    }
//
//    fun bringToTop(item: RO_Lesson) {
//        recentLearningLessonDataManager.bringToTop(item)
//    }
//
//    fun pushCachingData() {
//        recentLearningLessonDataManager.updateDB()
//    }
//
//
//    interface DataLoadListener {
//        fun onDataLoadSuccess(ro_lessons: ArrayList<RO_Lesson>)
//    }
//}
