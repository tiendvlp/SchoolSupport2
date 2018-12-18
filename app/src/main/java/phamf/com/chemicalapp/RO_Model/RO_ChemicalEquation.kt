package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RO_ChemicalEquation (@PrimaryKey @JvmField
                                var id: Int = 0,

                                @JvmField
                                var equation: String = "",

                                @JvmField
                                var cleanedEquation : String = "",

                                @JvmField
                                var condition: String = "",

                                @JvmField
                                var phenonema: String = "",

                                @JvmField
                                var how_to_do: String = "") : RealmObject(), Cloneable, Parcelable {

    protected constructor(`in`: Parcel) : this() {
        id = `in`.readInt()
        equation = `in`.readString()
        condition = `in`.readString()
        phenonema = `in`.readString()
        how_to_do = `in`.readString()
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is RO_ChemicalEquation) {
            if (obj.id == this.id) return true
        } else {
            Log.e("Class cast exeption", "")
        }
        return false
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(equation)
        dest.writeString(condition)
        dest.writeString(phenonema)
        dest.writeString(how_to_do)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_ChemicalEquation> = object : Parcelable.Creator<RO_ChemicalEquation> {
            override fun createFromParcel(`in`: Parcel): RO_ChemicalEquation {
                return RO_ChemicalEquation(`in`)
            }

            override fun newArray(size: Int): Array<RO_ChemicalEquation?> {
                return arrayOfNulls(size)
            }
        }
    }
}
