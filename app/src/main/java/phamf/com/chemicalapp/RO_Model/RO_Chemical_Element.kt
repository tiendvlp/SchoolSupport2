package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RO_Chemical_Element : RealmObject, Parcelable {

    @PrimaryKey
    var id: Int = 0

    @JvmField
    var name = ""

    @JvmField
    var symbol = ""

    @JvmField
    var electron_count = 0

    @JvmField
    var proton_count = 0

    @JvmField
    var notron_count = 0

    @JvmField
    var mass = 0f

    @JvmField
    var type = ""

    @JvmField
    var background_color = 0

    constructor() {

    }

    constructor(id: Int, name: String, symbol: String, electron_count: Int, proton_count: Int, notron_count: Int, mass: Float, type: String, background_color: Int) {

        this.name = name
        this.symbol = symbol
        this.electron_count = electron_count
        this.proton_count = proton_count
        this.notron_count = notron_count
        this.mass = mass
        this.type = type
        this.id = id
        this.background_color = background_color
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        name = `in`.readString()
        symbol = `in`.readString()
        electron_count = `in`.readInt()
        proton_count = `in`.readInt()
        notron_count = `in`.readInt()
        mass = `in`.readFloat()
        type = `in`.readString()
        background_color = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(symbol)
        dest.writeInt(electron_count)
        dest.writeInt(proton_count)
        dest.writeInt(notron_count)
        dest.writeFloat(mass)
        dest.writeString(type)
        dest.writeInt(background_color)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_Chemical_Element> = object : Parcelable.Creator<RO_Chemical_Element> {
            override fun createFromParcel(`in`: Parcel): RO_Chemical_Element {
                return RO_Chemical_Element(`in`)
            }

            override fun newArray(size: Int): Array<RO_Chemical_Element?> {
                return arrayOfNulls(size)
            }
        }
    }
}

