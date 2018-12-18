package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import phamf.com.chemicalapp.Abstraction.Interface.Menuable
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem

@RealmClass
open class RO_Lesson (

        @PrimaryKey
        override var id: Int = 0,

        override var name: String = "",

        @JvmField
        var content: String = ""

) : RealmObject(), Menuable, Parcelable, QuickChangeableItem {

    protected constructor(`in`: Parcel) : this() {
        id = `in`.readInt()
        name = `in`.readString()
        content = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(content)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_Lesson> = object : Parcelable.Creator<RO_Lesson> {
            override fun createFromParcel(`in`: Parcel): RO_Lesson {
                return RO_Lesson(`in`)
            }

            override fun newArray(size: Int): Array<RO_Lesson?> {
                return arrayOfNulls(size)
            }
        }
    }
}
