package phamf.com.chemicalapp.Model.SupportModel

import android.app.Activity
import android.content.Intent

import phamf.com.chemicalapp.DPDPMenuActivity
import phamf.com.chemicalapp.RO_Model.RO_DPDP

class DPDPDataGetter(internal var activity: Activity) {

    val data: RO_DPDP
        get() {
            val intent = activity.intent
            return intent.getParcelableExtra(DPDPMenuActivity.DPDP_NAME)
        }
}
