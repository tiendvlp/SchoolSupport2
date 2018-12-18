package phamf.com.chemicalapp.Supporter

import java.lang.reflect.Array
import java.util.ArrayList

import io.realm.RealmList
import phamf.com.chemicalapp.Model.Chapter
import phamf.com.chemicalapp.Model.ChemicalEquation
import phamf.com.chemicalapp.Model.Chemical_Element
import phamf.com.chemicalapp.Model.DPDP
import phamf.com.chemicalapp.Model.Isomerism
import phamf.com.chemicalapp.Model.Lesson
import phamf.com.chemicalapp.Model.OrganicMolecule
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.RO_Model.RO_Isomerism
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule

//Convert from object from firebase to realm model object
/** @see phamf.com.chemicalapp.Database.OnlineDatabaseManager
 */
object ROConverter {


    // Collections
    // Normal model List to Realm Model list
    fun toRO_Lessons(lessons: Collection<Lesson>): ArrayList<RO_Lesson> {
        val ro_lessons = ArrayList<RO_Lesson>()

        for (lesson in lessons) {
            val new_ro_lesson = toRO_Lesson(lesson)
            ro_lessons.add(new_ro_lesson)
        }

        return ro_lessons
    }

    fun toRO_Chapters(chapters: Collection<Chapter>): ArrayList<RO_Chapter> {
        val ro_chapters = ArrayList<RO_Chapter>()

        for (chapter in chapters) {
            val ro_chapter = toRO_Chapter(chapter)
            ro_chapters.add(ro_chapter)
        }
        return ro_chapters
    }

    fun toRO_DPDPs(ro_dpdps: Collection<DPDP>): ArrayList<RO_DPDP> {

        val ro_dpdps_list = ArrayList<RO_DPDP>()

        for (dpdp in ro_dpdps) {
            val new_ro_dpdp = RO_DPDP()
            new_ro_dpdp.id = dpdp.id
            new_ro_dpdp.name = dpdp.name
            new_ro_dpdp.organicMolecules = ROConverter.toRO_OrganicMolecules(dpdp.organicMolecules)
            ro_dpdps_list.add(new_ro_dpdp)
        }

        return ro_dpdps_list

    }

    fun toRO_Chapters_ArrayList(ro_chapters: Collection<RO_Chapter>): ArrayList<RO_Chapter> {
        val ro_chapters_list = ArrayList<RO_Chapter>()
        ro_chapters_list.addAll(ro_chapters)
        return ro_chapters_list
    }

    fun toRO_DPDPs_ArrayList(ro_dpdps: Collection<RO_DPDP>): ArrayList<RO_DPDP> {
        val ro_dpdps_list = ArrayList<RO_DPDP>()
        ro_dpdps_list.addAll(ro_dpdps)
        return ro_dpdps_list
    }

    fun toRO_Chemical_Elements(chemical_elements: Collection<Chemical_Element>): ArrayList<RO_Chemical_Element> {
        val ro_chemical_elements = ArrayList<RO_Chemical_Element>()
        for (element in chemical_elements) {
            val new_ro_ce = toRO_Chemical_Element(element)
            ro_chemical_elements.add(new_ro_ce)
        }
        return ro_chemical_elements
    }

    fun toRO_CEs(equations: ArrayList<ChemicalEquation>): ArrayList<RO_ChemicalEquation> {
        val ro_ces = ArrayList<RO_ChemicalEquation>()

        for (chemicalEquation in equations) {
            ro_ces.add(toRO_CE(chemicalEquation))
        }

        return ro_ces
    }

    fun toRO_OrganicMolecules(organicMolecules: Collection<OrganicMolecule>): RealmList<RO_OrganicMolecule> {
        val ro_organicMolecules = RealmList<RO_OrganicMolecule>()
        for (organicMolecule in organicMolecules) {
            ro_organicMolecules.add(toRO_OrganicMolecule(organicMolecule))
        }
        return ro_organicMolecules
    }

    fun toRO_Isomerisms(isomerisms: Collection<Isomerism>): RealmList<RO_Isomerism> {

        val ro_isomerisms = RealmList<RO_Isomerism>()

        for (isomerism in isomerisms) {
            ro_isomerisms.add(toRO_Isomerism(isomerism))
        }

        return ro_isomerisms
    }


    // Single object
    // Normal model to Realm Model
    fun toRO_Chapter(chapter: Chapter): RO_Chapter {
        val ro_chapter = RO_Chapter()
        ro_chapter.name = chapter.name
        ro_chapter.id = chapter.getid()
        ro_chapter.setLessons(chapter.ro_lessons)
        return ro_chapter
    }

    fun toRO_Lesson(lesson: Lesson): RO_Lesson {
        val ro_lesson = RO_Lesson()
        ro_lesson.id = lesson.id
        ro_lesson.name = lesson.name
        ro_lesson.content = lesson.content
        return ro_lesson
    }

    fun toRO_CE(equation: ChemicalEquation): RO_ChemicalEquation {
        val ro_ce = RO_ChemicalEquation()

        ro_ce.id = equation.id
        ro_ce.equation= equation.equation
        ro_ce.phenonema= equation.phenonema
        ro_ce.condition = equation.condition
        ro_ce.how_to_do = equation.how_to_do
        ro_ce.cleanedEquation = HtmlContentGetter.getContent(ro_ce.equation)

        return ro_ce
    }

    fun toRO_OrganicMolecule(organicMolecule: OrganicMolecule): RO_OrganicMolecule {
        val ro_organicMolecule = RO_OrganicMolecule()
        ro_organicMolecule.id = organicMolecule.id
        ro_organicMolecule.replace_name = organicMolecule.replace_name
        ro_organicMolecule.normal_name = organicMolecule.normal_name
        ro_organicMolecule.compact_structure_image_id = organicMolecule.compact_structure_image_id
        ro_organicMolecule.molecule_formula = organicMolecule.molecule_formula
        ro_organicMolecule.isomerisms = toRO_Isomerisms(organicMolecule.isomerisms)
        ro_organicMolecule.structure_image_id = organicMolecule.structure_image_id
        ro_organicMolecule.id = organicMolecule.id
        return ro_organicMolecule
    }

    fun toRO_Isomerism(isomerism: Isomerism): RO_Isomerism {
        val ro_isomerism = RO_Isomerism()
        ro_isomerism.id = isomerism.id
        ro_isomerism.structure_image_id = isomerism.structure_image_id
        ro_isomerism.compact_structure_image_id = isomerism.compact_structure_image_id
        ro_isomerism.molecule_formula = isomerism.molecule_formula
        ro_isomerism.normal_name = isomerism.normal_name
        ro_isomerism.replace_name = isomerism.replace_name
        ro_isomerism.compact_structure_image_id = isomerism.compact_structure_image_id
        return ro_isomerism
    }

    fun toRO_DPDP(dpdp: DPDP): RO_DPDP {
        val ro_dpdp = RO_DPDP()
        ro_dpdp.id = dpdp.id
        ro_dpdp.name = dpdp.name
        ro_dpdp.organicMolecules = ROConverter.toRO_OrganicMolecules(dpdp.organicMolecules)

        return ro_dpdp
    }

    fun toRO_Chemical_Element(element: Chemical_Element): RO_Chemical_Element {
        val ro_element = RO_Chemical_Element()
        ro_element.background_color = element.backgroundColor
        ro_element.electron_count = element.electron_count
        ro_element.id = element.id
        ro_element.mass = element.mass
        ro_element.notron_count = element.notron_count
        ro_element.name = element.name
        ro_element.proton_count = element.proton_count
        ro_element.type = element.type
        ro_element.symbol = element.symbol
        return ro_element
    }


}
