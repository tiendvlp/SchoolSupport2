//package phamf.com.chemicalapp.Presenter
//
//import android.content.Intent
//import android.widget.Toast
//
//import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter
//import phamf.com.chemicalapp.CustomView.LessonViewCreator
//import phamf.com.chemicalapp.Abstraction.Interface.IDPDPActivity
//import phamf.com.chemicalapp.DPDPActivity
//import phamf.com.chemicalapp.DPDPMenuActivity
//import phamf.com.chemicalapp.RO_Model.RO_DPDP
//import phamf.com.chemicalapp.RO_Model.RO_Isomerism
//import phamf.com.chemicalapp.RO_Model.RO_OrganicMolecule
//
//import phamf.com.chemicalapp.CustomView.LessonViewCreator.ViewCreator.*
//
///** Send Data
// * @see DPDPActivity
// * implement this listener
// */
//
//class DPDPActivityPresenter(view: DPDPActivity) : Presenter<DPDPActivity>(view), IDPDPActivity.Presenter {
//
//    private var onDataLoadListener: DataLoadListener? = null
//
//    override fun loadData() {
//        val intent = view.intent
//
//        val dpdp = intent.getParcelableExtra<RO_DPDP>(DPDPMenuActivity.DPDP_NAME)
//
//        if (dpdp != null) {
//            view.qc_organic_adapter.setData(dpdp.organicMolecules)
//            convertObjectToData(dpdp)
//        } else {
//            Toast.makeText(view, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    /**
//     * Symbol "<>" : TAG_DEVIDER for devide image's info
//     * @see LessonViewCreator.ViewCreator
//     */
//    override fun convertContent(orM: RO_OrganicMolecule): String {
//        val content = StringBuilder()
//        content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.id.toString() + ") ")
//                .append(orM.molecule_formula)
//                .append(" - " + orM.name).append(COMPONENT_DEVIDER)
//                .append(CONTENT).append(". - Tên thay thế: " + orM.replace_name).append(COMPONENT_DEVIDER)
//                .append(CONTENT).append(". - Công thức cấu tạo : ")
//                .append(IMAGE + orM.structure_image_id + "<>" + 200 + "<>" + 250).append(COMPONENT_DEVIDER)
//                .append(CONTENT).append(". - Công thức cấu tạo thu gọn: " + orM.compact_structure_image_id).append(COMPONENT_DEVIDER)
//                .append(CONTENT).append(". - Số đồng phân: " + orM.isomerisms.size).append(COMPONENT_DEVIDER)
//
//        for (iso in orM.isomerisms) {
//
//            content.append(CONTENT).append((orM.isomerisms.indexOf(iso) + 1).toString() + ") " + iso.replace_name).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(".   - Tên thường: " + orM.normal_name).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(".   - Công thức cấu tạo: " + iso.compact_structure_image_id).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(".   - Công thức cấu tạo thu gọn: ")
//        }
//
//        return content.toString()
//    }
//
//    //** Needing it
//    override fun convertObjectToData(dpdp: RO_DPDP) {
//        val content = StringBuilder()
//        val title = dpdp.name
//        for (orM in dpdp.organicMolecules) {
//            content.append(COMPONENT_DEVIDER).append(BIG_TITLE).append(orM.id.toString() + ") ")
//                    .append(orM.molecule_formula).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(" - Tên thông thường: " + orM.normal_name).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(" - Tên thay thế: " + orM.replace_name).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(" - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
//                    .append(IMAGE).append(orM.structure_image_id + "<>400<>400").append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(" - Công thức cấu tạo thu gọn: " + orM.compact_structure_image_id).append(COMPONENT_DEVIDER)
//                    .append(CONTENT).append(" - Số đồng phân: " + orM.isomerisms.size).append(COMPONENT_DEVIDER)
//
//            for (iso in orM.isomerisms) {
//                content.append(SMALL_TITLE).append("   " + iso.replace_name).append(COMPONENT_DEVIDER)
//                        .append(CONTENT).append("   - Công thức cấu tạo: ").append(COMPONENT_DEVIDER)
//                        .append(IMAGE).append(iso.structure_image_id + "<>400<>400").append(COMPONENT_DEVIDER)
//                        .append(CONTENT).append("   - Công thức cấu tạo thu gọn: ").append(COMPONENT_DEVIDER)
//            }
//        }
//
//
//        /** Send Data
//         * @see DPDPActivity
//         * implement this listener
//         */
//        if (onDataLoadListener != null)
//            onDataLoadListener!!.onDataLoadSuccess(title, content.toString())
//    }
//
//    override fun setOnDataLoadListener(onDataLoadListener: DataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener
//    }
//
//    interface DataLoadListener {
//
//        fun onDataLoadSuccess(title: String, content: String)
//
//    }
//}
