package phamf.com.chemicalapp.Model.SupportModel

import android.content.Intent

import phamf.com.chemicalapp.LessonActivity
import phamf.com.chemicalapp.LessonMenuActivity
import phamf.com.chemicalapp.RO_Model.RO_Lesson

class MVVM_LessonDataGetter(internal var view: LessonActivity) {

    val data: RO_Lesson
        get() {
            val intent = view.intent
            return intent.getParcelableExtra(LessonMenuActivity.LESSON_NAME)
        }
}
