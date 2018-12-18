package phamf.com.chemicalapp.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.widget.Toast

import phamf.com.chemicalapp.CustomView.LessonViewCreator
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.BIG_TITLE
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.COMPONENT_DEVIDER
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.CONTENT
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.IMAGE
import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.Companion.SMALL_TITLE
import phamf.com.chemicalapp.DPDPActivity
import phamf.com.chemicalapp.Model.SupportModel.DPDPDataGetter
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule


/** Send Data
 * @see DPDPActivity
 * implement this listener
 */

class MVVM_DPDPActivityPresenter(application: Application) : AndroidViewModel(application) {

    var ldt_ro_organicMolecule = MutableLiveData<Collection<RO_OrganicMolecule>>()
    var ldt_content_data = MutableLiveData<String>()
    var ldt_dpdp_name_data = MutableLiveData<String>()

    fun loadData(activity: DPDPActivity) {
        val dpdp = DPDPDataGetter(activity).data
        if (dpdp != null) {
            ldt_ro_organicMolecule.value = dpdp.organicMolecules
            convertDPDPToTextData(dpdp)
        } else {
            Toast.makeText(activity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Symbol "<>" : TAG_DEVIDER for devide image's info
     * @see LessonViewCreator.ViewCreator
     */
    fun convertContent(orM: RO_OrganicMolecule) {
        val content = StringBuilder()
        content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.id.toString() + ") ")
                .append(orM.molecule_formula)
                .append(" - " + orM.name).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Tên thay thế: " + orM.replace_name).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Công thức cấu tạo : ")
                .append(IMAGE + orM.structure_image_id + "<>" + 200 + "<>" + 250).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Công thức cấu tạo thu gọn: " + orM.compact_structure_image_id).append(COMPONENT_DEVIDER)
                .append(CONTENT).append(". - Số đồng phân: " + orM.isomerisms.size).append(COMPONENT_DEVIDER)

        for (iso in orM.isomerisms) {

            content.append(CONTENT).append((orM.isomerisms.indexOf(iso) + 1).toString() + ") " + iso.replace_name).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Tên thường: " + orM.normal_name).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Công thức cấu tạo: " + iso.compact_structure_image_id).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(".   - Công thức cấu tạo thu gọn: ")
        }

        ldt_content_data.value = content.toString()
    }

    //** Needing it
    internal fun convertDPDPToTextData(dpdp: RO_DPDP) {
        val content = StringBuilder()

        val title = dpdp.name

        for (orM in dpdp.organicMolecules) {
            content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.id.toString() + ") ")
                    .append(orM.molecule_formula).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Tên thông thường: " + orM.normal_name).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Tên thay thế: " + orM.replace_name).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                    .append(IMAGE).append(orM.structure_image_id + "<>400<>400").append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Công thức cấu tạo thu gọn: " + orM.compact_structure_image_id).append(COMPONENT_DEVIDER)
                    .append(CONTENT).append(" - Số đồng phân: " + orM.isomerisms.size).append(COMPONENT_DEVIDER)

            for (iso in orM.isomerisms) {
                content.append(SMALL_TITLE).append("   " + iso.replace_name).append(COMPONENT_DEVIDER)
                        .append(CONTENT).append("   - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
                        .append(IMAGE).append(iso.structure_image_id + "<>400<>400").append(COMPONENT_DEVIDER)
                        .append(CONTENT).append("   - Công thức cấu tạo thu gọn: ").append(COMPONENT_DEVIDER)
            }
        }

        /** Send Data
         * @see DPDPActivity
         * implement this listener
         */
        ldt_content_data.value = content.toString()
        ldt_dpdp_name_data.value = title
    }

}

