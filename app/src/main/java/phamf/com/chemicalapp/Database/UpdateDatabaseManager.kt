package phamf.com.chemicalapp.Database

import android.content.Context
import android.util.Log

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import java.util.ArrayList

import phamf.com.chemicalapp.Model.Chapter
import phamf.com.chemicalapp.Model.ChemicalEquation
import phamf.com.chemicalapp.Model.Chemical_Element
import phamf.com.chemicalapp.Model.DPDP
import phamf.com.chemicalapp.Model.Lesson
import phamf.com.chemicalapp.Model.OrganicMolecule
import phamf.com.chemicalapp.Model.UpdateData
import phamf.com.chemicalapp.Model.UpdateFile
import phamf.com.chemicalapp.RO_Model.RO_Chapter
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Image
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.Supporter.ROConverter

class UpdateDatabaseManager(context: Context, app_version: Long) {

    private val IMAGEs = "images"

    private val LASTED_UPDATE_VERSION = "lasted_update_version"

    private val offlineDatabaseManager: OfflineDatabaseManager

    internal var firebase_database: DatabaseReference

    lateinit var firebase_storage: StorageReference

    private var lasted_update_version: Long = 0

    private val app_version: Float

    private var onASectionUpdated: OnASectionUpdated? = null


    internal val ONE_AND_HAFT_MEGABYTE = (1024.0 * 1024.0 * 1.5).toLong()

    init {

        offlineDatabaseManager = OfflineDatabaseManager(context)
        firebase_database = FirebaseDatabase.getInstance().reference
        this.app_version = app_version.toFloat()
    }

    fun update() {
        firebase_storage = FirebaseStorage.getInstance().reference

        firebase_database.child(LASTED_UPDATE_VERSION).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lasted_update_version = dataSnapshot.value as Long

                try {
                    download_And_Process_UpdateFile(app_version.toLong(), lasted_update_version)
                } catch (ex: Exception) {
                    Log.e("error", "UpdateDatabaseManage.java/71")
                    ex.printStackTrace()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    /** @see  phamf.com.chemicalapp.MainActivity
     *
     */
    fun getLastestVersionUpdate(onVersionChecked: OnVersionChecked) {
        firebase_database.child(LASTED_UPDATE_VERSION).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onVersionChecked.onVersionLoaded((dataSnapshot.value as Long?)!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    fun download_And_Process_UpdateFile(version: Long, last_version: Long) {
        /*   ^   */
        /*  /^\  */
        /* / ^ \ */
        val _version = version + 1
        /*   ^   */
        /*   ^   */    if (_version > last_version) {
            /*   ^   */        if (onASectionUpdated != null) onASectionUpdated!!.on_A_Version_Updated_Success(last_version, true)
            /*   ^   */        return
            /*   ^   */
        }
        /*   ^   */
        /*   ^   */    firebase_database.child(_version.toInt().toString()).addValueEventListener(object : ValueEventListener {
            /*   ^   */        override/*   ^   */ fun onDataChange(dataSnapshot: DataSnapshot) {
                /*   ^   */            //Download
                /*   ^   */
                val updateFile = dataSnapshot.getValue(UpdateFile::class.java)
                /*   ^   */
                /*   ^   */            try {
                    /*   ^   */
                    /*   ^   */                // "Update File" is retrieved
                    /*   ^   */                assert(updateFile != null)
                    /*   ^   */                processUpdateFile(updateFile)
                    /*   ^   */                // firebase database get data asynchronously So can't use for loop to get
                    /*   ^   */                // every update file on firebase
                    /*   ^   */                // Solution is using Recursive : Đệ quy,
                    /*   ^<<<<<<<<<<<<<<<<<<<<<*/ download_And_Process_UpdateFile(_version, last_version)
                    if (onASectionUpdated != null) onASectionUpdated!!.on_A_Version_Updated_Success(_version, false)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    /**
     * Include functions:
     * + process_Lessons()
     * + getRO_LessonListFromIds()
     * + process_Chapters()
     * + process_Chemical_Elements()
     * + process_Chemical_Equations()
     * + process_DPDPs()
     * + process_Images()
     * + downLoadImage()
     */
    private fun processUpdateFile(updateFile: UpdateFile?) {

        if (updateFile == null) {
            Log.e("Update Error", "Update file is null")
            return
        }

        try {
            //Warning : must update "lessons" before update "chapters" because chapter will find
            // lessons in database depend on the lesson_id which update_file contains in updatefile.lessons

            if (updateFile.lessons != null) {
                process_Lessons(updateFile.lessons)
            } else {
                Log.e("Lessons ", "null")
            }

            if (updateFile.chapters != null) {
                process_Chapters(updateFile.chapters)
            } else {
                Log.e("Chapters ", "null")
            }

            if (updateFile.images != null) {
                process_Images(updateFile.images)
            } else {
                Log.e("Images ", "null")
            }

            if (updateFile.dpdps != null) {
                process_DPDPs(updateFile.dpdps)
            } else {
                Log.e("Dpdps ", "null")
            }

            if (updateFile.chemical_equations != null) {
                process_Chemical_Equations(updateFile.chemical_equations)
            } else {
                Log.e("Chemical equations ", "null")
            }

            if (updateFile.chemical_elements != null) {
                process_Chemical_Elements(updateFile.chemical_elements)
            } else {
                Log.e("Chemical Elements ", "null")
            }

            if (updateFile.update_data != null) {
                processUpdateData(updateFile.update_data)
            } else {
                Log.e("Update data", "null")
            }

        } catch (ex: Exception) {
            Log.e("Error", "Error source: UpdateDatabaseManager.java")
            ex.printStackTrace()
        }

    }

    // Lesson
    private fun process_Lessons(chapter_list: ArrayList<Lesson>) {

        val ro_lessons = ROConverter.toRO_Lessons(chapter_list)
        offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson::class.java, ro_lessons)
    }

    private fun getRO_LessonListFromIds(id_list: ArrayList<Long>): ArrayList<RO_Lesson>? {

        if ((id_list == null) or (id_list.size == 0)) {
            Log.e("Error happened", "Please check UpdateDatabaseManager.java, line 206")
            return null
        }

        val ro_lessons = ArrayList<RO_Lesson>()

        for (id in id_list) {
            val ro_lesson = offlineDatabaseManager.readOneObjectOf(RO_Lesson::class.java, "id", id.toInt())
            if (ro_lesson != null) ro_lessons.add(ro_lesson)
        }

        return ro_lessons
    }


    // Chapter
    private fun process_Chapters(chapter_list: ArrayList<Chapter>) {

        // Bind RO_Lesson from db to Chapter by lesson's id
        for (chapter in chapter_list) {
            val ro_lessons_list = getRO_LessonListFromIds(chapter.lessons)
            if (ro_lessons_list != null)
                chapter.ro_lessons = ro_lessons_list
        }

        val ro_chapters = ROConverter.toRO_Chapters(chapter_list)
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter::class.java, ro_chapters)
    }


    // Chemical Element
    private fun process_Chemical_Elements(chemical_element_list: ArrayList<Chemical_Element>) {
        val ro_chemical_elements = ROConverter.toRO_Chemical_Elements(chemical_element_list)
        offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Element::class.java, ro_chemical_elements)
    }


    // Chemical Equation
    private fun process_Chemical_Equations(chemical_equations_list: ArrayList<ChemicalEquation>) {
        val ro_chemicalEquations = ROConverter.toRO_CEs(chemical_equations_list)
        offlineDatabaseManager.addOrUpdateDataOf(RO_ChemicalEquation::class.java, ro_chemicalEquations)
    }


    // DPDP
    private fun process_DPDPs(dpdp_list: ArrayList<DPDP>) {
        val ro_dpdps = ROConverter.toRO_DPDPs(dpdp_list)
        offlineDatabaseManager.addOrUpdateDataOf(RO_DPDP::class.java, ro_dpdps)
    }


    // Image
    private fun process_Images(link_list: ArrayList<String>) {
        downLoadImage(link_list, 0)
    }

    private fun downLoadImage(link_list: ArrayList<String>, pointer: Int) {
        if (pointer >= link_list.size) return

        val link = link_list[pointer]

        // Get image
        firebase_storage.child(link).getBytes(ONE_AND_HAFT_MEGABYTE).addOnSuccessListener { byte_code_resouces ->
            val chemical_image = RO_Chemical_Image(link, byte_code_resouces)
            offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Image::class.java, chemical_image)

            // Recursive
            downLoadImage(link_list, pointer + 1)
        }

    }

    fun setOnASectionUpdated(onASectionUpdated: OnASectionUpdated) {
        this.onASectionUpdated = onASectionUpdated
    }


    /**
     * Update data
     * Include methods:
     * + updateLessons()
     * + updateChapter()
     * + updateChemical_Elements()
     * + updateChemical_Equations()
     * + updateDpdps()
     */
    private fun processUpdateData(update_data: UpdateData) {

        if (update_data.lessons != null) {
            updateLessons(0, update_data.lessons)
        }

        if (update_data.chapters != null) {
            updateChapter(0, update_data.chapters)
        }

        if (update_data.chemical_elements != null) {
            updateChemical_Elements(0, update_data.chemical_elements)

        }

        if (update_data.chemical_equations != null) {
            updateChemical_Equations(0, update_data.chemical_equations)
        }

        if (update_data.dpdps != null) {
            updateDpdps(0, update_data.dpdps)
        }
    }

    private fun updateLessons(pointer: Int, lessons_link: ArrayList<String>) {

        if (pointer >= lessons_link.size) return

        firebase_database.child(lessons_link[pointer]).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lesson = dataSnapshot.getValue(Lesson::class.java)
                offlineDatabaseManager.addOrUpdateDataOf(RO_Lesson::class.java, ROConverter.toRO_Lesson(lesson!!))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun updateChapter(pointer: Int, chapters_link: ArrayList<String>) {
        if (pointer >= chapters_link.size) return

        firebase_database.child(chapters_link[pointer]).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chapter = dataSnapshot.getValue(Chapter::class.java)
                val ro_lessons_list = getRO_LessonListFromIds(chapter!!.lessons)
                if (ro_lessons_list != null) chapter.ro_lessons = ro_lessons_list
                offlineDatabaseManager.addOrUpdateDataOf(RO_Chapter::class.java, ROConverter.toRO_Chapter(chapter))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateChemical_Elements(pointer: Int, chemical_elements_link: ArrayList<String>) {
        if (pointer >= chemical_elements_link.size) return

        firebase_database.child(chemical_elements_link[pointer]).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chemical_element = dataSnapshot.getValue(Chemical_Element::class.java)
                offlineDatabaseManager.addOrUpdateDataOf(RO_Chemical_Element::class.java, ROConverter.toRO_Chemical_Element(chemical_element!!))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateChemical_Equations(pointer: Int, chemical_equations_link: ArrayList<String>) {
        if (pointer >= chemical_equations_link.size) return

        firebase_database.child(chemical_equations_link[pointer]).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chemical_equation = dataSnapshot.getValue(ChemicalEquation::class.java)
                offlineDatabaseManager.addOrUpdateDataOf(RO_ChemicalEquation::class.java, ROConverter.toRO_CE(chemical_equation!!))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateDpdps(pointer: Int, dpdps_link: ArrayList<String>) {
        if (pointer >= dpdps_link.size) return

        firebase_database.child(dpdps_link[pointer]).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dpdp = dataSnapshot.getValue(DPDP::class.java)
                offlineDatabaseManager.addOrUpdateDataOf(RO_DPDP::class.java, ROConverter.toRO_DPDP(dpdp!!))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    interface OnVersionChecked {
        fun onVersionLoaded(version: Long)
    }

    interface OnASectionUpdated {
        fun on_A_Version_Updated_Success(version: Long, isLastVersion: Boolean)
    }

}
