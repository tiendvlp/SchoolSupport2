package phamf.com.chemicalapp.RO_Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RO_Chemical_Image : RealmObject {

    @PrimaryKey @JvmField
    var link: String = ""

    @JvmField
    var byte_code_resouces: ByteArray = ByteArray(0)

    constructor(link: String, byte_code_resouces: ByteArray) {
        this.link = link
        this.byte_code_resouces = byte_code_resouces
    }

    constructor() {

    }
}
