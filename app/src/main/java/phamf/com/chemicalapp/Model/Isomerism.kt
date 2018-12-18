package phamf.com.chemicalapp.Model

class Isomerism {

    //    public void setType(@IsomerismType.Type String type) {
    //        this.type = type;
    //    }
    //
    //    public String getType() {
    //        return this.type;
    //    }


    var id: Int = 0

    lateinit var molecule_formula: String

    lateinit var normal_name: String

    //    String type;

    lateinit var replace_name: String

    var structure_image_id = ""

    var compact_structure_image_id = ""

    constructor() {

    }

    constructor(/*String type,*/molecule_formula: String, normal_name: String, replace_name: String, structure_image_id: String, compact_structure_image_id: String) {
        this.molecule_formula = molecule_formula
        this.normal_name = normal_name
        this.replace_name = replace_name
        this.structure_image_id = structure_image_id
        this.compact_structure_image_id = compact_structure_image_id
        //        this.type = type;
    }


}



