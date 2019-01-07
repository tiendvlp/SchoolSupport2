package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RO_Chemical_Composition (

        @PrimaryKey
        var id : Int = 0,

        @JvmField
        var name : String = "",

        @JvmField
        var formula : String = "",

        @JvmField
        var clean_formula : String = "",

        @JvmField
        var unique_mass : Float = 0f,

        @JvmField
        var inReality : String = ""

) : RealmObject (), Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readFloat(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(name)
                parcel.writeString(formula)
                parcel.writeString(clean_formula)
                parcel.writeFloat(unique_mass)
                parcel.writeString(inReality)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<RO_Chemical_Composition> {
                override fun createFromParcel(parcel: Parcel): RO_Chemical_Composition {
                        return RO_Chemical_Composition(parcel)
                }

                override fun newArray(size: Int): Array<RO_Chemical_Composition?> {
                        return arrayOfNulls(size)
                }
        }

}
