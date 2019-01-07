package phamf.com.chemicalapp.Model.SupportModel

import phamf.com.chemicalapp.LessonMenuActivity

class LessonMenuDataGetter (internal var view : LessonMenuActivity) {

    //Lesson type
    var data : String = ""
        get() {
            var bundle = view.intent.extras
            return bundle.getString(LessonMenuActivity.LESSON_TYPE, "H")
        }


}