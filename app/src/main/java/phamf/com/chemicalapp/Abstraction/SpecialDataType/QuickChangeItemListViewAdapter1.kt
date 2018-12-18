package phamf.com.chemicalapp.Abstraction.SpecialDataType

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import java.util.ArrayList

import phamf.com.chemicalapp.Abstraction.Interface.QuickChangeableItem
import phamf.com.chemicalapp.R

open class QuickChangeItemListViewAdapter<T : QuickChangeableItem>(protected var context: Context) : BaseAdapter() {

    protected var list: ArrayList<T>

    protected var onItemClickListener: OnItemClickListener<T>? = null

    init {
        this.list = ArrayList()
    }

    fun setData(list: Collection<T>) {
        for (t in list) {
            this.list.add(t)

        }

        notifyDataSetChanged()
    }

    fun adaptFor(listView: ListView) {
        listView.adapter = this
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> if (onItemClickListener != null) onItemClickListener!!.OnItemClickListener(list[position], view) }
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.child_quick_change_item, parent, false)
        val txt_name = view.findViewById<TextView>(R.id.txt_quick_change_item_name)
        txt_name.text = list[position].name
        return view
    }

    interface OnItemClickListener<T : QuickChangeableItem> {

        fun OnItemClickListener(item: T, view: View)

    }

    fun setOnItemClickListener_ (itemClickListener : OnItemClickListener<T>) {
        this.onItemClickListener= itemClickListener;
    }
}
