package phamf.com.chemicalapp.Abstraction.SpecialDataType

import android.support.annotation.StringDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

object IsomerismType {

    const val CARBON_CHAIN_ISOMERISM = "Đồng phân mạch cacbon"

    const val GEOMETRIC_ISOMERISM = "Đồng phân hình học"

    @StringDef(CARBON_CHAIN_ISOMERISM, GEOMETRIC_ISOMERISM)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Type

}
