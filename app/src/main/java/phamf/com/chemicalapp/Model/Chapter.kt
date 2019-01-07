package phamf.com.chemicalapp.Model

import java.util.ArrayList

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_Lesson

@Suppress("unused")
class Chapter {

    internal var id: Int = 0

    lateinit var name: String

    lateinit var type : String

    // This list contains id of lessons used for finding them in database and then bind
    // to ro_lessons field below
    lateinit var lessons: ArrayList<Long>

    var ro_lessons = ArrayList<RO_Lesson>()

    //used in firebase
    constructor()

    constructor(id: Int, name: String, lessons: ArrayList<Long>) {
        this.id = id
        this.name = name
        this.lessons = lessons
    }


    fun getid(): Int {
        return id
    }

    fun setid(id: Int) {
        this.id = id
    }
}
