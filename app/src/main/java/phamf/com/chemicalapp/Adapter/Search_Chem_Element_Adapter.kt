package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import java.util.ArrayList

import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Element

class Search_Chem_Element_Adapter(internal var context: Context) : RecyclerView.Adapter<Search_Chem_Element_Adapter.DataViewHolder>(), Filterable {

    internal var filter: Search_Chem_Element_Adapter.Searcher

    internal lateinit var list: ArrayList<RO_Chemical_Element>

    internal lateinit var defaultList: ArrayList<RO_Chemical_Element>

    internal var onCustomItemClickListener: Search_Chem_Element_Adapter.OnItemClickListener? = null

    private var rcv: RecyclerView? = null

    private val onClickListener = View.OnClickListener { v ->
        if (onCustomItemClickListener != null) {
            val position = rcv!!.indexOfChild(v)
            onCustomItemClickListener!!.onItemClick(list[position])
        }
    }


    init {
        filter = Searcher()
    }

    fun adaptFor(rcv: RecyclerView) {
        this.rcv = rcv
        rcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv.adapter = this
    }

    fun setData(datas: Collection<RO_Chemical_Element>) {
        list = ArrayList()
        defaultList = ArrayList()
        list.addAll(datas)
        defaultList.addAll(list)
        notifyDataSetChanged()
    }

    fun observe(virtualKeyBoardSensor: VirtualKeyBoardSensor) {
        var onTextChange : VirtualKeyBoardSensor.OnTextChangeLite = object : VirtualKeyBoardSensor.OnTextChangeLite {
            override fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
                getFilter().filter(s)
            }
        }
        virtualKeyBoardSensor.addLiteTextChangeListener(onTextChange)
    }

    fun setOnItemClickListener(onCustomItemClickListener: OnItemClickListener) {
        this.onCustomItemClickListener = onCustomItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_Chem_Element_Adapter.DataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.child_search_chemical_element, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: Search_Chem_Element_Adapter.DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener(onClickListener)
        val element = list[position]
        holder.txt_chem_element_symbol.text = element.symbol
        holder.txt_chem_element_name.text = if (element.name == null) element.name + "" else element.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txt_chem_element_symbol: TextView
        internal var txt_chem_element_name: TextView

        init {
            txt_chem_element_name = itemView.findViewById(R.id.child_txt_chem_element_name)
            txt_chem_element_symbol = itemView.findViewById(R.id.child_txt_chem_element_symbol)
        }
    }

    inner class Searcher : Filter() {


        override fun performFiltering(key: CharSequence): Filter.FilterResults? {

            if (key.length == 0) return null

            val result = Filter.FilterResults()

            val newList = ArrayList<RO_Chemical_Element>()

            for (element in defaultList) {
                if ((element.symbol + element.name).toUpperCase().contains(key.toString().toUpperCase())) {
                    newList.add(element)
                }
            }


            result.values = newList

            return result
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults?) {

            if (results != null && results.values != null) {
                list = results.values as ArrayList<RO_Chemical_Element>
            } else {
                list = defaultList
            }

            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(element: RO_Chemical_Element)
    }
}
