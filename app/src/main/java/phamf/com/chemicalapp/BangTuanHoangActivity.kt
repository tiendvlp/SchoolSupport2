package phamf.com.chemicalapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import phamf.com.chemicalapp.Abstraction.Interface.IBangTuanHoangActivity
import phamf.com.chemicalapp.Adapter.BangTuanHoang_GridView_Adapter
import phamf.com.chemicalapp.Adapter.Search_Chem_Element_Adapter
import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.Manager.AppThemeManager
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element
import phamf.com.chemicalapp.Supporter.UnitConverter

import android.view.View.GONE
import kotlinx.android.synthetic.main.activity_bang_tuan_hoang.*


class BangTuanHoangActivity : FullScreenActivity(), IBangTuanHoangActivity.View {

    internal lateinit var grv_bth_adapter: BangTuanHoang_GridView_Adapter

    internal lateinit var rcv_element_search_adapter: Search_Chem_Element_Adapter


    internal var grv_bth_list = ArrayList<RO_Chemical_Element>()

    var list = ArrayList<String>()

    private lateinit var fade_out: Animation
    private lateinit var fade_in: Animation

    internal lateinit var virtualKeyboardManager: InputMethodManager

    private val CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE = 105 /* 105 dp*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bang_tuan_hoang)

        ButterKnife.bind(this)

        setTheme()

        loadAnim()

        setUpManagers()

        addData()

        addControl()

        addEvent()

        setPeriodicTableWidth()
    }

    private fun setUpManagers() {
        virtualKeyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun addData() {

        grv_bth_list.add(RO_Chemical_Element(1, "Hidro", "H", 1, 1, 0, 2f, "Không xác định", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(2, "Heli", "He", 2, 2, 2, 4f, "Khí trơ", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(3, "Liti", "Li", 3, 3, 4, 7f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(4, "Beri", "Be", 4, 4, 5, 9f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(5, "Bo", "B", 5, 4, 6, 11f, "Bo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(6, "Cacbon", "C", 5, 4, 6, 12f, "Cacbon", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(7, "Nitơ", "N", 5, 4, 7, 14f, "Nito", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(8, "Oxi", "O", 5, 4, 8, 16f, "Oxi", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(9, "Flo", "F", 5, 4, 10, 19f, "Flo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(10, "Neon", "Ne", 5, 4, 6, 20f, "Khí trơ", getColor(R.color.nonmetal_color)))

        grv_bth_list.add(RO_Chemical_Element(11, "Natri", "Na", 3, 3, 12, 23f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(12, "Magie", "Mg", 3, 3, 12, 24f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())
        grv_bth_list.add(RO_Chemical_Element())

        grv_bth_list.add(RO_Chemical_Element(13, "Nhôm", "Al", 5, 4, 14, 27f, "Bo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(14, "Silic", "Si", 5, 4, 14, 28f, "Cacbon", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(15, "Photpho", "P", 5, 4, 16, 31f, "Nito", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(16, "Lưu huỳnh", "S", 5, 4, 16, 32f, "Oxi", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(17, "Clo", "Cl", 5, 4, 10, 35f, "Flo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(18, "Argon", "Ar", 5, 4, 6, 40f, "Khí trơ", getColor(R.color.nonmetal_color)))

        grv_bth_list.add(RO_Chemical_Element(19, "Kali", "K", 3, 3, 12, 39f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(20, "Canxi", "Ca", 3, 3, 12, 40f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element(21, "Scanđi", "Sc", 3, 3, 24, 45f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(22, "Titan", "Ti", 3, 3, 12, 48f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(23, "Vanađi", "V", 3, 3, 12, 51f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(24, "Crôm", "Cr", 3, 3, 12, 52f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(25, "Mangan", "Mn", 3, 3, 12, 55f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(26, "Sắt", "Fe", 3, 3, 12, 56f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(27, "Coban", "Co", 3, 3, 12, 59f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(28, "Niken", "Ni", 3, 3, 12, 59f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(29, "Đồng", "Cu", 3, 3, 12, 64f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(30, "Kẽm", "Zn", 3, 3, 12, 65f, "IIIB", getColor(R.color.Group_B_metal)))

        grv_bth_list.add(RO_Chemical_Element(31, "Gali", "Ga", 5, 4, 14, 70f, "Bo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(32, "Gemani", "Ge", 5, 4, 14, 73f, "Cacbon", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(33, "Asen", "As", 5, 4, 16, 75f, "Nito", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(34, "Selen", "Se", 5, 4, 16, 79f, "Oxi", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(35, "Brom", "Br", 5, 4, 10, 80f, "Flo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(36, "Kripton", "Kr", 5, 4, 6, 84f, "Khí trơ", getColor(R.color.nonmetal_color)))

        grv_bth_list.add(RO_Chemical_Element(37, "Rubiđi", "Rb", 3, 3, 12, 86f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(38, "Stronti", "Sr", 3, 3, 12, 88f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element(39, "Ytri", "Y", 3, 3, 24, 89f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(40, "Ziriconi", "Zr", 3, 3, 12, 91f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(41, "Niobi", "Nb", 3, 3, 12, 93f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(42, "Molipđen", "Mo", 3, 3, 12, 96f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(43, "Tecnexi", "Tc", 3, 3, 12, 99f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(44, "Ruteni", "Ru", 3, 3, 12, 101f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(45, "Rođi", "Rh", 3, 3, 12, 103f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(46, "Palađi", "Pd", 3, 3, 12, 106f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(47, "Bạc", "Ag", 3, 3, 12, 108f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(48, "Cađimi", "Cd", 3, 3, 12, 113f, "IIIB", getColor(R.color.Group_B_metal)))

        grv_bth_list.add(RO_Chemical_Element(49, "Inđi", "In", 5, 4, 14, 115f, "Bo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(50, "Thiếc", "Sn", 5, 4, 14, 118f, "Cacbon", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(51, "Antimon", "An", 5, 4, 16, 122f, "Nito", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(52, "Telu", "Te", 5, 4, 16, 128f, "Oxi", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(53, "Iot", "I", 5, 4, 10, 127f, "Flo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(54, "Xenon", "Xe", 5, 4, 6, 131f, "Khí trơ", getColor(R.color.nonmetal_color)))

        grv_bth_list.add(RO_Chemical_Element(55, "Xesi", "Cs", 3, 3, 12, 86f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(56, "Bari", "Ba", 3, 3, 12, 88f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element(57, "Lantan", "La", 3, 3, 24, 89f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(58, "Hafini", "Hf", 3, 3, 12, 91f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(59, "Tantan", "Ta", 3, 3, 12, 93f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(61, "Volfam", "W", 3, 3, 12, 96f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(62, "Reni", "Re", 3, 3, 12, 99f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(63, "Osimi", "Os", 3, 3, 12, 101f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(64, "Iriđi", "Ir", 3, 3, 12, 103f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(65, "Platin", "Pt", 3, 3, 12, 106f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(66, "Vàng", "Au", 3, 3, 12, 108f, "IIIB", getColor(R.color.Group_B_metal)))
        grv_bth_list.add(RO_Chemical_Element(67, "Thủy ngân", "Hg", 3, 3, 12, 113f, "IIIB", getColor(R.color.Group_B_metal)))

        grv_bth_list.add(RO_Chemical_Element(68, "Tali", "Tl", 5, 4, 14, 115f, "Bo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(69, "Chì", "Pb", 5, 4, 14, 118f, "Cacbon", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(70, "Bitmut", "Bi", 5, 4, 16, 122f, "Nito", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(71, "Poloni", "Po", 5, 4, 16, 128f, "Oxi", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(72, "Atatin", "At", 5, 4, 10, 127f, "Flo", getColor(R.color.nonmetal_color)))
        grv_bth_list.add(RO_Chemical_Element(73, "Rađon", "Rn", 5, 4, 6, 131f, "Khí trơ", getColor(R.color.nonmetal_color)))

        grv_bth_list.add(RO_Chemical_Element(74, "Franxi", "Fr", 3, 3, 12, 86f, "Kiềm", getColor(R.color.alkali_metal_color)))
        grv_bth_list.add(RO_Chemical_Element(75, "Rađi", "Ra", 3, 3, 12, 88f, "Kiềm thổ", getColor(R.color.alkali_metal_color)))

        grv_bth_list.add(RO_Chemical_Element(76, "Actini", "Ac", 3, 3, 24, 89f, "IIIB", getColor(R.color.Group_B_metal)))

    }

    override fun addControl() {
        grv_bth_adapter = BangTuanHoang_GridView_Adapter(this)
        grv_bth_adapter.adaptFor(grv_bang_tuan_hoan!!)
        grv_bth_adapter.setData(grv_bth_list)

        rcv_element_search_adapter = Search_Chem_Element_Adapter(this)
        rcv_element_search_adapter.adaptFor(rcv_bangtuanhoan_chem_element_search!!)
        rcv_element_search_adapter.setData(grv_bth_list)
        rcv_element_search_adapter.observe(edt_bangtuanhoan_search!!)

        if (AppThemeManager.isOnNightMode) {
            bg_night_mode_bang_tuan_hoan!!.visibility = View.VISIBLE
        } else {
            bg_night_mode_bang_tuan_hoan!!.visibility = View.INVISIBLE
        }
    }

    override fun addEvent() {

        btn_bangtuanhoan_back!!.setOnClickListener { v -> finish() }

        grv_bth_adapter.setOnItemClickListener (object : BangTuanHoang_GridView_Adapter.OnItemClickListener {
            override fun onItemClickListener(chem_element: RO_Chemical_Element) {
                val intent = Intent(this@BangTuanHoangActivity, ChemicalElementActivity::class.java)
                intent.putExtra(CHEM_ELEMENT, chem_element)
                startActivity(intent)
                hideVirtualKeyboard()
            }
        })

        rcv_element_search_adapter.setOnItemClickListener (object : Search_Chem_Element_Adapter.OnItemClickListener {
            override fun onItemClick(element: RO_Chemical_Element) {
                bg_edt_search!!.visibility = GONE
                val intent = Intent(this@BangTuanHoangActivity, ChemicalElementActivity::class.java)
                intent.putExtra(CHEM_ELEMENT, element)
                startActivity(intent)
                hideVirtualKeyboard()
            }
        })

        btn_bottom_bangtuanhoan_search!!.setOnClickListener { v ->
            bangtuanhoan_search_chem_element_view_parent!!.startAnimation(fade_in)
            edt_bangtuanhoan_search!!.requestFocus()
            showVirtualKeyboard()
        }

        btn_top_bangtuanhoan_turn_off_search!!.setOnClickListener { v ->
            bangtuanhoan_search_chem_element_view_parent!!.startAnimation(fade_out)
            hideVirtualKeyboard()
            edt_bangtuanhoan_search!!.setText("")
        }
    }

    override fun loadAnim() {
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fade_in!!.fillAfter = false

        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        fade_out!!.fillAfter = false

        fade_out!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                bangtuanhoan_search_chem_element_view_parent!!.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        fade_in!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                bangtuanhoan_search_chem_element_view_parent!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun hideVirtualKeyboard() {
        virtualKeyboardManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun showVirtualKeyboard() {
        virtualKeyboardManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun setPeriodicTableWidth() {
        ln_bth_gridview_Parent!!.layoutParams.width = 18 * UnitConverter.DpToPixel(CHILD_ELEMENT_LAYOUT_WIDTH_IN_XML_FILE) + 17 * UnitConverter.DpToPixel(3)
    }

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_bangtuanhoan_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_bangtuanhoan_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    companion object {
        val CHEM_ELEMENT = "chem_element"
    }

}
