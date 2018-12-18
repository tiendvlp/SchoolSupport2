//package phamf.com.chemicalapp.Presenter
//
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import android.os.AsyncTask
//import android.util.Log
//
//import java.util.ArrayList
//
//import io.realm.RealmChangeListener
//import io.realm.RealmList
//import io.realm.RealmResults
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity
//import phamf.com.chemicalapp.Database.OfflineDatabaseManager
//import phamf.com.chemicalapp.LessonMenuActivity
//import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager
//import phamf.com.chemicalapp.RO_Model.RO_Chapter
//import phamf.com.chemicalapp.RO_Model.RO_Lesson
//import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons
//import phamf.com.chemicalapp.Supporter.ROConverter
//
//import phamf.com.chemicalapp.Supporter.ROConverter.toRO_Chapters_ArrayList
//
///**
// * @see LessonMenuActivity
// */
//class LessonMenuActivityPresenter(view: LessonMenuActivity) : Presenter<LessonMenuActivity>(view), ILessonMenuActivity.Presenter {
//
//    private var onDataLoadListener: DataLoadListener? = null
//
//    private var offlineDB_manager: OfflineDatabaseManager? = null
//
//    private var recentLearningLessonDataManager: RecentLearningLessonDataManager? = null
//
//    private var data: RealmResults<RO_Chapter>? = null
//
//    override fun loadData() {
//
//        offlineDB_manager = OfflineDatabaseManager(context)
//
//        recentLearningLessonDataManager = RecentLearningLessonDataManager(offlineDB_manager!!)
//        // Call this function to create some data for RecentLearningLessonDataManager class,
//        // look inside this method to get more info
//        recentLearningLessonDataManager!!.getData(null)
//        data = offlineDB_manager!!.readAsyncAllDataOf(RO_Chapter::class.java
//        ) { ro_chapters -> onDataLoadListener!!.onDataLoadedSuccess(toRO_Chapters_ArrayList(ro_chapters)) }
//
//    }
//
//    override fun pushCachingDataToDB() {
//        recentLearningLessonDataManager!!.updateDB()
//    }
//
//    override fun clearAllListenerToDatabase() {
//        data!!.removeAllChangeListeners()
//    }
//
//    /** Bring this lesson to top of recent learning lesson list in realm database   */
//    override fun bringToTop(ro_lesson: RO_Lesson) {
//        recentLearningLessonDataManager!!.bringToTop(ro_lesson)
//    }
//
//    fun setOnDataLoadListener(onDataLoadListener: DataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener
//    }
//
//    /** @see LessonMenuActivity
//     */
//    interface DataLoadListener {
//
//        fun onDataLoadedSuccess(ro_chapters: ArrayList<RO_Chapter>)
//
//    }
//
//}
//
//
