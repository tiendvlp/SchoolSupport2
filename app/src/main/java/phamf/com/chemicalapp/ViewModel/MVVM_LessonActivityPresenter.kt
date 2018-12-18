package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import phamf.com.chemicalapp.LessonActivity
import phamf.com.chemicalapp.Model.SupportModel.MVVM_LessonDataGetter
import phamf.com.chemicalapp.RO_Model.RO_Lesson

/**
 * @see LessonActivity
 */

class MVVM_LessonActivityPresenter(view: Application) : AndroidViewModel(view) {

    var ldt_ro_lesson = MutableLiveData<RO_Lesson>()

    fun loadData(activity: LessonActivity) {
        val ro_lesson = MVVM_LessonDataGetter(activity).data
        if (ro_lesson != null)
            ldt_ro_lesson.setValue(ro_lesson)
        else
            Log.e("Null lesson", "LessonActivityPresenter.java in loadData() function")
    }


}

