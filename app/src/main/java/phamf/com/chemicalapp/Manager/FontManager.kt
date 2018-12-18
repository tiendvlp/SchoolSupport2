package phamf.com.chemicalapp.Manager

import android.content.res.AssetManager
import android.graphics.Typeface

/**
 * Created by phamf on 04-May-18.
 */

object FontManager {

    lateinit var comfortaa_bold: Typeface

    lateinit var comfortaa_regular: Typeface

    lateinit var myriad_pro_bold: Typeface

    lateinit var myriad_pro_regular: Typeface

    lateinit var arial: Typeface

    fun createFont(assetManager: AssetManager) {

        comfortaa_bold = Typeface.createFromAsset(assetManager, "fonts/comfortaa_bold.ttf")

        comfortaa_regular = Typeface.createFromAsset(assetManager, "fonts/comfortaa_regular.ttf")

        myriad_pro_bold = Typeface.createFromAsset(assetManager, "fonts/myriad_pro_semi_bold.ttf")

        myriad_pro_regular = Typeface.createFromAsset(assetManager, "fonts/myriad_pro_regular.ttf")

        arial = Typeface.createFromAsset(assetManager, "fonts/arial.ttf")
    }


}
