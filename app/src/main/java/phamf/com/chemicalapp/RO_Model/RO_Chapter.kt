package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

import java.util.ArrayList
import java.util.Random

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import phamf.com.chemicalapp.Abstraction.Interface.Menuable

@RealmClass
open class RO_Chapter (
        @PrimaryKey
        override var id: Int = 0,

        override var name : String = "",

        var lessons : RealmList<RO_Lesson> = RealmList()

) : RealmObject() , Menuable, Parcelable {

    protected constructor(`in`: Parcel) : this() {
        id = `in`.readInt()
        name = `in`.readString()
        `in`.readList(lessons, javaClass.classLoader)
    }

    fun setLessons(lessons: Collection<RO_Lesson>) {
        this.lessons.clear()
        this.lessons.addAll(lessons)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeList(lessons)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_Chapter> = object : Parcelable.Creator<RO_Chapter> {
            override fun createFromParcel(`in`: Parcel): RO_Chapter {
                return RO_Chapter(`in`)
            }

            override fun newArray(size: Int): Array<RO_Chapter?> {
                return arrayOfNulls(size)
            }
        }
    }
}
