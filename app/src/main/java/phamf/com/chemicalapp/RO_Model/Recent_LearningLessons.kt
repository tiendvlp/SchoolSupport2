package phamf.com.chemicalapp.RO_Model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Recent_LearningLessons : RealmObject {

    @PrimaryKey @JvmField
    var id: Int = 0

    @JvmField
    var recent_learning_lessons: RealmList<RO_Lesson> = RealmList()

    constructor()

    constructor(id: Int, recent_learning_lessons: RealmList<RO_Lesson>) {
        this.recent_learning_lessons = recent_learning_lessons
        this.id = id
    }
}
