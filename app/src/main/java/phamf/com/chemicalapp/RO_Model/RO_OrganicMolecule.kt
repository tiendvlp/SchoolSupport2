package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem

@RealmClass
open class RO_OrganicMolecule : RealmObject, Parcelable, QuickChangeableItem {

    @PrimaryKey @JvmField
    var id: Int = 0

    @JvmField
    var molecule_formula: String = ""

    @JvmField
    var normal_name: String = ""

    @JvmField
    var replace_name: String = ""

    @JvmField
    var structure_image_id: String = ""

    @JvmField
    var compact_structure_image_id: String = ""

    @JvmField
    var isomerisms = RealmList<RO_Isomerism>()

    // For create List View
    override var name: String = ""
        get() = molecule_formula

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        molecule_formula = `in`.readString()
        normal_name = `in`.readString()
        replace_name = `in`.readString()
        structure_image_id = `in`.readString()
        compact_structure_image_id = `in`.readString()
        `in`.readList(isomerisms, this.javaClass.classLoader)
    }

    constructor() {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(molecule_formula)
        dest.writeString(normal_name)
        dest.writeString(replace_name)
        dest.writeString(structure_image_id)
        dest.writeString(compact_structure_image_id)
        dest.writeList(isomerisms)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_OrganicMolecule> = object : Parcelable.Creator<RO_OrganicMolecule> {
            override fun createFromParcel(`in`: Parcel): RO_OrganicMolecule {
                return RO_OrganicMolecule(`in`)
            }

            override fun newArray(size: Int): Array<RO_OrganicMolecule?> {
                return arrayOfNulls(size)
            }
        }
    }
}
