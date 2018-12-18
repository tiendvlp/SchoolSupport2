package phamf.com.chemicalapp.RO_Model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import phamf.com.chemicalapp.Abstraction.SpecialDataType.IsomerismType

@RealmClass
open class RO_Isomerism : RealmObject, Parcelable {

    @PrimaryKey
    var id: Int = 0

    //    public void setType(@IsomerismType.Type String type) {
    //        this.type = type;
    //    }
    //
    //    public String getType() {
    //        return this.type;
    //    }

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

    //    String type;

    constructor() {

    }

    constructor(molecule_formula: String, normal_name: String, replace_name: String, structure_image_id: String, compact_structure_image_id: String) {
        this.molecule_formula = molecule_formula
        this.normal_name = normal_name
        this.replace_name = replace_name
        this.structure_image_id = structure_image_id
        this.compact_structure_image_id = compact_structure_image_id
    }


    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        molecule_formula = `in`.readString()
        normal_name = `in`.readString()
        replace_name = `in`.readString()
        structure_image_id = `in`.readString()
        compact_structure_image_id = `in`.readString()
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
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<RO_Isomerism> = object : Parcelable.Creator<RO_Isomerism> {
            override fun createFromParcel(`in`: Parcel): RO_Isomerism {
                return RO_Isomerism(`in`)
            }

            override fun newArray(size: Int): Array<RO_Isomerism?> {
                return arrayOfNulls(size)
            }
        }
    }
}
