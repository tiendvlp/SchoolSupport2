package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import java.util.ArrayList

import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element

/**
 * @see phamf.com.chemicalapp.BangTuanHoangActivity
 */
class BangTuanHoang_GridView_Adapter(internal var context: Context) : BaseAdapter() {

    var list: ArrayList<RO_Chemical_Element>

    lateinit var onCustomItemClickListener: OnItemClickListener

    lateinit var gridView: GridView

    init {
        list = ArrayList()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        // The empty chemical element present for a empty position
        if (list[position].name == "") {
            view = layoutInflater.inflate(R.layout.child_empty_chemical_element, parent, false)
            if (view == null) {
                Log.e("FUCK YOU", "What the hell ?")
            }
            return view
        }

        view = layoutInflater.inflate(R.layout.child_chemical_element, parent, false)

        val txt_electron_structure = view.findViewById<TextView>(R.id.child_txt_electron_structure)
        val txt_proton = view.findViewById<TextView>(R.id.child_txt_ele_proton)
        val txt_symbol = view.findViewById<TextView>(R.id.child_txt_ele_symbol)
        val txt_name = view.findViewById<TextView>(R.id.child_txt_ele_name)
        val txt_mass = view.findViewById<TextView>(R.id.child_txt_ele_mass)

        val element = list[position]
        txt_proton.text = element.proton_count.toString()
        txt_symbol.text = element.symbol
        txt_name.text = element.name
        val mass = if ((element.mass.toString() + "").endsWith(".0")) element.mass.toInt().toString() + "" else element.mass.toString() + ""
        txt_mass.text = mass
        txt_electron_structure.setBackgroundColor(element.background_color)
        view.setBackgroundColor(element.background_color)

        if (view == null) {
            Log.e("FUCK YOU", "What the fuck?")
        }

        return view
    }

    fun adaptFor(gridView: GridView) {
        this.gridView = gridView
        gridView.adapter = this
    }

    fun setData(elements: Collection<RO_Chemical_Element>) {
        for (element in elements) {
            list.add(element)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onCustomItemClickListener: OnItemClickListener) {
        this.onCustomItemClickListener = onCustomItemClickListener
        gridView.setOnItemClickListener { parent, view, position, id -> onCustomItemClickListener.onItemClickListener(list[position]) }
    }

    interface OnItemClickListener {
        fun onItemClickListener(chem_element: RO_Chemical_Element)
    }
}
