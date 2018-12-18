package phamf.com.chemicalapp.CustomView

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import phamf.com.chemicalapp.Adapter.ViewPager_Lesson_Adapter

import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import java.util.Observable
import java.util.Random

import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Image
import phamf.com.chemicalapp.Supporter.UnitConverter

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import phamf.com.chemicalapp.Supporter.UnitConverter.DpToPixel

class LessonViewCreator(private val viewPager_adapter: ViewPager_Lesson_Adapter) {

    class ViewCreator// Constructor
    (private val context: Context, private val parent: LinearLayout) {

        internal var offlineDatabaseManager: OfflineDatabaseManager


        init {
            this.offlineDatabaseManager = OfflineDatabaseManager(context)
        }

        // Functions
        fun addContent(content: String, style: String) {
            val textView = TextView(context)
            textView.textSize = DpToPixel(content_text_size).toFloat()
            textView.setTextColor(Color.parseColor("#222222"))
            textView.text = Html.fromHtml(content)
            textView.setTypeface(textView.typeface, getTextStyle(style))

            val params = LinearLayout.LayoutParams(content_width, content_height)
            params.setMargins(content_mLeft, content_mTop, content_mRight, content_mBottom)
            textView.layoutParams = params
            parent.addView(textView)
        }

        fun addBigTitle(title: String, style: String) {
            val textView = TextView(context)
            textView.text = Html.fromHtml(title)
            textView.setTypeface(textView.typeface, getTextStyle(style))
            textView.textSize = DpToPixel(big_title_text_size).toFloat()
            textView.setTextColor(Color.parseColor("#BA1308"))

            val params = LinearLayout.LayoutParams(bt_width, bt_height)
            params.setMargins(bt_mLeft, bt_mTop, bt_mRight, bt_mBottom)
            textView.layoutParams = params
            parent.addView(textView)
        }

        fun addSmallTitle(title: String, style: String) {
            val textView = TextView(context)
            textView.text = Html.fromHtml(title)
            textView.textSize = DpToPixel(small_title_text_size).toFloat()
            textView.setTextColor(Color.parseColor("#008080"))
            textView.setTypeface(textView.typeface, getTextStyle(style))

            val params = LinearLayout.LayoutParams(smt_width, smt_height)
            params.setMargins(smt_mLeft, smt_mTop, smt_mRight, smt_mBottom)
            textView.layoutParams = params
            parent.addView(textView)
        }

        fun addSmallerTitle(title: String, style: String) {
            val textView = TextView(context)
            textView.text = Html.fromHtml(title)
            textView.textSize = DpToPixel(smaller_title_text_size).toFloat()
            textView.setTextColor(Color.parseColor("#191970"))
            textView.setTypeface(textView.typeface, getTextStyle(style))

            val params = LinearLayout.LayoutParams(smlt_width, smlt_height)
            params.setMargins(smlt_mLeft, smlt_mTop, smlt_mRight, smlt_mBottom)
            textView.layoutParams = params
            parent.addView(textView)
        }

        fun addImageContent(imageId: String,
                            mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {

            val image_resouces = offlineDatabaseManager.readOneObjectOf(RO_Chemical_Image::class.java, "link", imageId)
            val byte_code_resouces = image_resouces!!.byte_code_resouces
            val image_bitmap = BitmapFactory.decodeByteArray(byte_code_resouces, 0, byte_code_resouces.size)
            val ratio = image_bitmap.width.toDouble() / image_bitmap.height.toDouble()

            val imageView = ImageView(context)
            imageView.background = BitmapDrawable(context.resources, image_bitmap)
            val params = LinearLayout.LayoutParams(screenWidthPixels.toInt(), (screenWidthPixels / ratio).toInt())
            params.setMargins(bt_mLeft, bt_mTop, bt_mRight, bt_mBottom)
            imageView.layoutParams = params
            parent.addView(imageView)
        }

        fun addView(content: String) {
            val component_list = content.split(COMPONENT_DEVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (component_ in component_list) {
                var component : String = component_;
                if (component.trim { it <= ' ' } != "") {
                    component = component.trim { it <= ' ' }
                    if (component.startsWith(IMAGE)) {
                        try {
                            //all Image has form like <<picture<> link <> width <> height

                            val image_info = component.split(TAG_DIVIDER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val id = image_info[IMAGE_LINK]
                            val width = Integer.valueOf(image_info[IMAGE_WIDTH])
                            val height = Integer.valueOf(image_info[IMAGE_HEIGHT])

                            addImageContent(id,
                                    DpToPixel(10),
                                    DpToPixel(10),
                                    DpToPixel(10),
                                    DpToPixel(10))
                        } catch (ex: NumberFormatException) {
                            ex.printStackTrace()
                            Log.e("Error when add image", "Đã xảy ra lỗi khi xử lí ảnh")
                            Log.e("Error when add image", "Error happened when process image")
                        }

                    } else if (component.startsWith(SMALL_TITLE)) {

                        val small_title_info = process_GetTextInfo(component)
                        addSmallTitle(small_title_info[TEXT_CONTENT], small_title_info[TEXT_STYLE])

                    } else if (component.startsWith(BIG_TITLE)) {

                        val big_title_info = process_GetTextInfo(component)

                        addBigTitle(big_title_info[TEXT_CONTENT], big_title_info[TEXT_STYLE])

                    } else if (component.startsWith(SMALLER_TITLE)) {

                        val smaller_title_info = process_GetTextInfo(component)
                        addSmallerTitle(smaller_title_info[TEXT_CONTENT], smaller_title_info[TEXT_STYLE])

                    } else if (component.startsWith(CONTENT)) {

                        val content_info = process_GetTextInfo(component)
                        addContent(content_info[TEXT_CONTENT], content_info[TEXT_STYLE])

                    } else {
                        Log.e("wrong component " + Random().nextInt(9), component)
                    }
                }
            }
        }

        private fun hasTextStyle(content: String): Boolean {
            return content.startsWith(BOLD_TEXT) || content.startsWith(ITALICED_TEXT)
        }

        private fun getTextStyle(type: String?): Int {

            if (type == null) {
                Log.e("Null style", "is " + type!!)
                return Typeface.NORMAL
            }

            if (type === BOLD_TEXT) {
                return Typeface.BOLD
            } else if (type === ITALICED_TEXT) {
                return Typeface.BOLD_ITALIC
            } else {
                Log.e("Wrong style", "is $type")
                return Typeface.NORMAL
            }
        }


        private val TEXT_CONTENT = 0
        private val TEXT_STYLE = 1

        private fun process_GetTextInfo(text: String): Array<String> {
            var text = text
            var text_style = ""
            var text_content = ""
            val text_info : Array<String> = arrayOf<String>(text_content, text_style)
            //Remove identifiers like <<smlr_tt>>, <<b_title>>, <<s_title>>
            text = text.substring(BEGIN_TEXT_STYLE_POSITION)

            if (hasTextStyle(text)) {

                text_style = text.substring(BEGIN_TEXT_STYLE_POSITION, END_TEXT_STYLE_POSITION)
                text_content = text.substring(END_TEXT_STYLE_POSITION)

            } else
                text_content = text

            text_info[TEXT_CONTENT] = text_content
            text_info[TEXT_STYLE] = text_style

            return text_info
        }

        companion object {

            val COMPONENT_DEVIDER = "<co_divi>"

            val BIG_TITLE = "<<b_title>>"

            val SMALL_TITLE = "<<s_title>>"

            val SMALLER_TITLE = "<<smlr_tt>>"

            val TAG_DIVIDER = "<>"

            val IMAGE = "<<picture$TAG_DIVIDER"

            val CONTENT = "<<content>>"

            // Text data usually has form as follow : <<B_TITLE>><<BoldTxt>>Hello World
            // 11 is length of <<B_TITLE>> (Type) as well as the START position of <<BoldTxt>> (Text style)
            // 22 is END position of <<BoldTxt>> and START position of content too
            private val BEGIN_TEXT_STYLE_POSITION = 11

            private val END_TEXT_STYLE_POSITION = 22

            // Properties
            private var bt_mLeft: Int = 0
            private var bt_mTop: Int = 0
            private var bt_mRight: Int = 0
            private var bt_mBottom: Int = 0
            private val bt_width = WRAP_CONTENT
            private val bt_height = WRAP_CONTENT

            private var smt_mLeft: Int = 0
            private var smt_mTop: Int = 0
            private var smt_mRight: Int = 0
            private var smt_mBottom: Int = 0
            private val smt_width = WRAP_CONTENT
            private val smt_height = WRAP_CONTENT

            private var smlt_mLeft: Int = 0
            private var smlt_mTop: Int = 0
            private var smlt_mRight: Int = 0
            private var smlt_mBottom: Int = 0
            private val smlt_width = WRAP_CONTENT
            private val smlt_height = WRAP_CONTENT

            private var content_mLeft: Int = 0
            private var content_mTop: Int = 0
            private var content_mRight: Int = 0
            private var content_mBottom: Int = 0
            private val content_width = WRAP_CONTENT
            private val content_height = WRAP_CONTENT

            private var content_text_size: Int = 0
            private var big_title_text_size: Int = 0
            private var small_title_text_size: Int = 0
            private var smaller_title_text_size: Int = 0


            // Setter
            fun setMarginBigTitle(mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {
                bt_mLeft = mLeft
                bt_mTop = mTop
                bt_mRight = mRight
                bt_mBottom = mBottom
            }

            fun setMarginSmallTitle(mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {
                smt_mLeft = mLeft
                smt_mTop = mTop
                smt_mRight = mRight
                smt_mBottom = mBottom
            }

            fun setMarginSmallerTitle(mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {
                smlt_mLeft = mLeft
                smlt_mTop = mTop
                smlt_mRight = mRight
                smlt_mBottom = mBottom
            }

            fun setMarginContent(mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {
                content_mLeft = mLeft
                content_mTop = mTop
                content_mRight = mRight
                content_mBottom = mBottom
            }

            fun setContent_text_size(content_text_size: Int) {
                ViewCreator.content_text_size = content_text_size
            }

            fun setBig_title_text_size(big_title_text_size: Int) {
                ViewCreator.big_title_text_size = big_title_text_size
            }

            fun setSmall_title_text_size(small_title_text_size: Int) {
                ViewCreator.small_title_text_size = small_title_text_size
            }

            fun setSmaller_title_text_size(smaller_title_text_size: Int) {
                ViewCreator.smaller_title_text_size = smaller_title_text_size
            }


            private val IMAGE_LINK = 1
            private val IMAGE_WIDTH = 2
            private val IMAGE_HEIGHT = 3
        }
    }

    companion object {

        // all of this content identifier has same length and now it's 11
        val PART_DEVIDER = "<pa_divi>"

        val BOLD_TEXT = "<<boldTxt>>"

        val ITALICED_TEXT = "<<italTxt>>"

        val NORMAL_TEXT = "<<normTxt>>"

        var isSetUp = false

        var screenWidthPixels = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    }
}

