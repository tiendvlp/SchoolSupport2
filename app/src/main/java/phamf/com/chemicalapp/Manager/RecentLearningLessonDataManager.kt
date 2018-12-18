package phamf.com.chemicalapp.Manager

import android.util.Log
import io.realm.RealmChangeListener

import java.util.ArrayList

import io.realm.RealmList
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons

class RecentLearningLessonDataManager(private val offlineDB_manager: OfflineDatabaseManager) {

    private var recent_learningLessons: Recent_LearningLessons? = null

    private var recent_lessons: RealmList<RO_Lesson>? = null

    private val MAX_RECENT_LEARNING_LESSON_COUNT = 12


    init {


        // At the first time, check whether or not an object in realm db storing
        // a recent_learning_lessons object
        // If there's no object, create new one
        // If true, do nothing
        if (offlineDB_manager.readOneOf(Recent_LearningLessons::class.java) == null) {
            val recent_learningLessons = Recent_LearningLessons()
            offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons::class.java, recent_learningLessons)
        }
    }

    // This function works both data getter and recent learning lessons creator
    fun getData(onGetDataSuccess: OnGetDataSuccess?) {
        var x : RealmChangeListener<Recent_LearningLessons> = object : RealmChangeListener<Recent_LearningLessons>{
            override fun onChange(recent_learningLessons: Recent_LearningLessons) {
                recent_lessons = recent_learningLessons.recent_learning_lessons
                val data = ArrayList<RO_Lesson>()
                data.addAll(recent_lessons!!)
                onGetDataSuccess?.onLoadSuccess(data)
            }
        }
        recent_learningLessons = offlineDB_manager.readAsyncOneOf(Recent_LearningLessons::class.java, x)
    }

    fun bringToTop(lesson: RO_Lesson) {
        offlineDB_manager.beginTransaction()

        if (recent_lessons!!.size < MAX_RECENT_LEARNING_LESSON_COUNT) {

            if (recent_lessons!!.contains(lesson)) {

                if (recent_lessons!![0] != lesson) {
                    recent_lessons!!.remove(lesson)
                    recent_lessons!!.add(0, lesson)
                }

            } else {
                recent_lessons!!.add(0, lesson)
            }

        } else if (recent_lessons!!.size == MAX_RECENT_LEARNING_LESSON_COUNT) {
            if (recent_lessons!!.contains(lesson)) {

                if (recent_lessons!![0] != lesson) {
                    recent_lessons!!.remove(lesson)
                    recent_lessons!!.add(0, lesson)
                }

            } else {
                recent_lessons!!.removeAt(recent_lessons!!.size - 1)
                recent_lessons!!.add(0, lesson)
            }
        }

        offlineDB_manager.commitTransaction()
    }

    fun updateDB() {
        offlineDB_manager.addOrUpdateDataOf(Recent_LearningLessons::class.java, this.recent_learningLessons!!)
    }

    interface OnGetDataSuccess {

        fun onLoadSuccess(recent_Ces: ArrayList<RO_Lesson>)
    }
}
