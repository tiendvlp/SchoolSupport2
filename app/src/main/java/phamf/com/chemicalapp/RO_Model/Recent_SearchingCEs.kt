package phamf.com.chemicalapp.RO_Model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Recent_SearchingCEs : RealmObject, Cloneable {

    @PrimaryKey
    var id: Int = 0

    @JvmField
    var recent_searching_ces = RealmList<RO_ChemicalEquation>()

    constructor()

    fun setRecent_searching_ces(recent_searching_ces: Collection<RO_ChemicalEquation>) {
        this.recent_searching_ces.clear()
        this.recent_searching_ces.addAll(recent_searching_ces)
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        return super.clone()
    }
}
