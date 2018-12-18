
//package phamf.com.chemicalapp.Presenter
//
//import android.app.Activity
//import android.app.Application
//import android.arch.lifecycle.AndroidViewModel
//import android.arch.lifecycle.MutableLiveData
//import android.content.Intent
//import android.util.Log
//
//import io.realm.RealmResults
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.Abstraction.Interface.ILessonActivity
//import phamf.com.chemicalapp.LessonActivity
//import phamf.com.chemicalapp.LessonMenuActivity
//import phamf.com.chemicalapp.RO_Model.RO_Lesson
//
//class LessonActivityPresenter(view: LessonActivity) : Presenter<LessonActivity>(view), ILessonActivity.Presenter {
//
//    private var onDataLoadListener: DataLoadListener? = null
//
//    override fun setOnDataLoadListener(onDataLoadListener: DataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener
//    }
//
//    /**
//     * @see LessonActivity
//     */
//    override fun loadData() {
//        val ro_lesson = LessonDataGetter(view).data
//        if (ro_lesson != null)
//            onDataLoadListener!!.OnDataLoadSuccess(ro_lesson.name, ro_lesson.content)
//        else
//            Log.e("Null lesson", "LessonActivityPresenter.java in loadData() function")
//    }
//
//
//    /**
//     * @see LessonActivity
//     */
//    interface DataLoadListener {
//
//        fun OnDataLoadSuccess(title: String?, content: String?)
//
//    }
//}
//
//internal class LessonDataGetter(var view: LessonActivity) {
//
//    val data: RO_Lesson?
//        get() {
//            val intent = view.intent
//            return intent.getParcelableExtra(LessonMenuActivity.LESSON_NAME)
//        }
//}
//
