package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import phamf.com.chemicalapp.Abstraction.Interface.Menuable
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem

@RealmClass
open class RO_DPDP : RealmObject, Menuable, Parcelable, QuickChangeableItem {

    @PrimaryKey
    override var id: Int = 0

    override lateinit var name: String

    @JvmField
    var organicMolecules = RealmList<RO_OrganicMolecule>()

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        name = `in`.readString()
        `in`.readList(organicMolecules, this.javaClass.classLoader)
    }

    constructor()


    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeList(organicMolecules)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_DPDP> = object : Parcelable.Creator<RO_DPDP> {
            override fun createFromParcel(`in`: Parcel): RO_DPDP {
                return RO_DPDP(`in`)
            }

            override fun newArray(size: Int): Array<RO_DPDP?> {
                return arrayOfNulls(size)
            }
        }
    }
}
