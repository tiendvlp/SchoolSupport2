package phamf.com.chemicalapp.Model

import android.content.Context
import android.content.Intent
import android.widget.Toast

import phamf.com.chemicalapp.RO_Model.RO_Lesson

class Lesson {
    var id: Int = 0

    var chapterId: Int = 0

    lateinit var name: String

    lateinit var content: String


    constructor(id: Int, name: String, content: String) {
        this.id = id
        this.name = name
        this.content = content
    }

    constructor() {

    }


    fun toRO_Lesson(): RO_Lesson {

        val ro_lesson = RO_Lesson()
        ro_lesson.id = this.id
        ro_lesson.name = this.name
        ro_lesson.content = this.content

        return ro_lesson
    }
}
