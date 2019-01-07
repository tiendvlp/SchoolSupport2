package phamf.com.chemicalapp.RO_Model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Recent_SearchingCCo : RealmObject {
    @PrimaryKey
    var id: Int = 0

    @JvmField
    var recent_searching_cco = RealmList<RO_Chemical_Composition>()

    constructor()

    fun setRecent_searching_cco(recent_searching_cco: Collection<RO_Chemical_Composition>) {
        this.recent_searching_cco.clear()
        this.recent_searching_cco.addAll(recent_searching_cco)
    }

}