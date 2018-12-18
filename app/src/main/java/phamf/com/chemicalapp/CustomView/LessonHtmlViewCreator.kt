package phamf.com.chemicalapp.CustomView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import jp.wasabeef.richeditor.RichEditor
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Image
import phamf.com.chemicalapp.Supporter.UnitConverter

class LessonHtmlViewCreator(private val context: Context, private val parent: LinearLayout) {

    private val simulated_content = "<..> Abc xyz <..>[*]image108_200_link[*]<..>uxyz<..>"

    private val offlineDatabaseManager: OfflineDatabaseManager

    /**
     * Link has form like " <...> ..... ...> [*] image|(width)|(height)|link [*] <...> ...... ...> "
     */
    internal val IMAGE_WIDTH = 0
    internal val IMAGE_HEIGHT = 1
    internal val IMAGE_LINK = 2
    internal val INFO_DEVIDER = "\\|"

    init {
        offlineDatabaseManager = OfflineDatabaseManager(context)
    }

    fun addView(content: String) {
        val parts = content.split("\\[\\*]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (part in parts) {
            if (part.startsWith(IMAGE)) {
                addImage(part.substring(IMAGE.length + 1))
            } else {
                addText(part)
            }
        }
    }

    private fun addText(html: String) {
        val text = RichEditor(context)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10))
        text.layoutParams = params
        text.html = html
        parent.addView(text)
    }

    fun separatePart(content: String): Array<String> {
        return content.split("\\[p]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun addImage(image_info: String) {
        try {
            Log.e("IMAGE INFO", image_info + "CON CAC")
            val info = image_info.split(INFO_DEVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val width = UnitConverter.DpToPixel(Integer.valueOf(info[IMAGE_WIDTH]))
            val height = UnitConverter.DpToPixel(Integer.valueOf(info[IMAGE_HEIGHT]))
            val link = info[IMAGE_LINK]

            val image_resource = offlineDatabaseManager.readOneObjectOf(RO_Chemical_Image::class.java, "link", link)!!.byte_code_resouces
            val image_bitmap = BitmapFactory.decodeByteArray(image_resource, 0, image_resource.size)

            val image = ImageView(context)
            val params = LinearLayout.LayoutParams(width, height)
            params.setMargins(UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10), UnitConverter.DpToPixel(10))
            image.layoutParams = params
            image.background = BitmapDrawable(context.resources, image_bitmap)
            parent.addView(image)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    companion object {

        val PART_DEVIDER = "[p]"

        val DEVIDER = "[*]"

        val IMAGE = "image"
    }
}

