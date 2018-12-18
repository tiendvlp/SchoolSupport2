package phamf.com.chemicalapp.Model

import java.util.ArrayList

class OrganicMolecule {

    var id: Int = 0

    lateinit var molecule_formula: String

    lateinit var normal_name: String

    var replace_name = ""

    lateinit var structure_image_id: String

    lateinit var compact_structure_image_id: String

    var isomerisms = ArrayList<Isomerism>()

    constructor() {

    }

    constructor(molecule_formula: String, normal_name: String, replace_name: String, structure_image_id: String, compact_structure_image_id: String, isomerisms: ArrayList<Isomerism>) {

        this.molecule_formula = molecule_formula
        this.normal_name = normal_name
        this.replace_name = replace_name
        this.structure_image_id = structure_image_id
        this.compact_structure_image_id = compact_structure_image_id
        this.isomerisms = isomerisms
    }
}
