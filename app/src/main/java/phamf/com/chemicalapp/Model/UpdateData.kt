package phamf.com.chemicalapp.Model

import java.util.ArrayList

class UpdateData {
    lateinit var chapters: ArrayList<String>
    lateinit var lessons: ArrayList<String>
    lateinit var chemical_elements: ArrayList<String>
    lateinit var chemical_equations: ArrayList<String>
    lateinit var dpdps: ArrayList<String>

    constructor(chapters: ArrayList<String>, lessons: ArrayList<String>, chemical_elements: ArrayList<String>, chemical_equations: ArrayList<String>, dpdps: ArrayList<String>) {
        this.chapters = chapters
        this.lessons = lessons
        this.chemical_elements = chemical_elements
        this.chemical_equations = chemical_equations
        this.dpdps = dpdps
    }

    constructor() {

    }
}
