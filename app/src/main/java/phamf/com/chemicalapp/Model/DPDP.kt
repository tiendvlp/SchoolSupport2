package phamf.com.chemicalapp.Model

import java.util.ArrayList

class DPDP {

    var id: Int = 0

    var name = ""

    var organicMolecules = ArrayList<OrganicMolecule>()

    constructor(id: Int, name: String, organicMolecules: ArrayList<OrganicMolecule>) {
        this.id = id
        this.name = name
        this.organicMolecules = organicMolecules
    }

    constructor() {

    }
}
