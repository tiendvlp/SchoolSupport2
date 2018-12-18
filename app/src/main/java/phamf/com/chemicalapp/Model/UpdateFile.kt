package phamf.com.chemicalapp.Model

import java.util.ArrayList

class UpdateFile {

    lateinit var chapters: ArrayList<Chapter>

    lateinit var lessons: ArrayList<Lesson>

    lateinit var dpdps: ArrayList<DPDP>

    lateinit var chemical_elements: ArrayList<Chemical_Element>

    lateinit var chemical_equations: ArrayList<ChemicalEquation>

    lateinit var images: ArrayList<String>

    lateinit var update_data: UpdateData

    constructor(chapters: ArrayList<Chapter>, lessons: ArrayList<Lesson>, dpdps: ArrayList<DPDP>, chemical_elements: ArrayList<Chemical_Element>, chemical_equations: ArrayList<ChemicalEquation>, images: ArrayList<String>) {
        this.chapters = chapters
        this.lessons = lessons
        this.dpdps = dpdps
        this.chemical_elements = chemical_elements
        this.chemical_equations = chemical_equations
        this.images = images
    }

    constructor() {

    }
}
