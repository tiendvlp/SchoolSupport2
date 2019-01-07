package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import java.util.ArrayList

import phamf.com.chemicalapp.CustomView.VirtualKeyBoardSensor
import phamf.com.chemicalapp.RO_Model.RO_Chemical_Composition
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.Manager.FontManager


@Suppress("DEPRECATION")
class Search_CCo_RCV_Adapter(internal var context: Context) : RecyclerView.Adapter<Search_CCo_RCV_Adapter.DataViewHolder>(), Filterable {

    private var filter: Searcher

    var list: ArrayList<RO_Chemical_Composition>? = null
        internal set

    internal var defaultList: ArrayList<RO_Chemical_Composition>

    internal var itemClickListener: OnItemClickListener? = null

    private var rcv: RecyclerView? = null

    private val onClickListener = View.OnClickListener { v ->
        if (itemClickListener != null) {
            val position = rcv!!.indexOfChild(v)

            if (position >= 0) {
                val composition = getItem(position)
                itemClickListener!!.OnItemClickListener(v, composition, position)
            }
        }
    }

    init {
        list = ArrayList()
        defaultList = ArrayList()
        filter = Searcher()
    }


    // Not using now
    private fun processAddSubToChemicalcomposition(startPos: Int, s: String): String {
        var s = s
        val s_builder = StringBuilder()
        var continue_run = true
        for (i in startPos until s.length) {
            if (continue_run)
                if (isNumeric(s[i]) && i != 0 && !isNumeric(s.substring(0, i + 1))) {
                    if (i < s.length - 1) {
                        var j = i + 1
                        while (i < s.length) {
                            if (!isNumeric(s[j])) {
                                s_builder.insert(j, "</sub>")
                                s_builder.insert(i, "<sub>")
                                s = processAddSubToChemicalcomposition(j + 11, s_builder.toString())
                                continue_run = false
                            } else if (j == s.length - 1 && isNumeric(s[j])) {
                                s_builder.insert(i, "</sub>")
                                s_builder.append("<sub>")
                                s = s_builder.toString()
                                continue_run = false
                            }
                            j++
                        }
                    } else {
                        s_builder.insert(i, "</sub>")
                        s_builder.append("<sub>")
                        s = s_builder.toString()
                        continue_run = false
                    }
                }
        }
        return s
    }

    private fun isNumeric(s: String): Boolean {

        try {
            Integer.valueOf(s)
            return true
        } catch (ex: NumberFormatException) {
            return false
        }

    }

    private fun isNumeric(s: Char): Boolean {

        try {
            Integer.valueOf(s.toInt())
            return true
        } catch (ex: NumberFormatException) {
            return false
        }

    }

    fun adaptFor(rcv: RecyclerView) {
        this.rcv = rcv
        this.rcv!!.adapter = this
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.rcv!!.layoutManager = manager
    }

    fun observe(input: EditText) {
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                getFilter().filter(s)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    fun observe(input: VirtualKeyBoardSensor) {
        input.addLiteTextChangeListener(object : VirtualKeyBoardSensor.OnTextChangeLite {
            override fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
                getFilter().filter(s)
            }

        })
    }

    /**
     * I don't uses List.addAll(Collection collection) because that Realm Object can't be called by any thread except
     * thread creating it. If i use addAll, then in Filter class beneath, Realm Object will be called by thread of Filter,
     * that's not allowed.
     * So i have to clone their properties to new Realm Object and then add to the list
     * Thanks to Garbage Collection of Java, data that i get from db will be deleted because there's no reference to them anymore
     * ,after be set to Search List Adapter
     */

    fun setData(RO_Chemical_Compositions: Collection<RO_Chemical_Composition>) {
        list = ArrayList()
        defaultList = ArrayList()
        for (composition in RO_Chemical_Compositions) {
            val ro_composition = RO_Chemical_Composition()
            ro_composition.id = composition.id
            ro_composition.name = composition.name
            ro_composition.formula = composition.formula
            ro_composition.formula = composition.formula
            ro_composition.unique_mass = composition.unique_mass
            ro_composition.inReality = composition.inReality

            list!!.add(ro_composition)
            defaultList.add(ro_composition)
        }
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    fun getItem(position: Int): RO_Chemical_Composition {
        return list!![position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_CCo_RCV_Adapter.DataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.child_search_chemical_equation, parent, false)
        view.setOnClickListener(onClickListener)
        return DataViewHolder(view)

    }


    override fun onBindViewHolder(holder: Search_CCo_RCV_Adapter.DataViewHolder, position: Int) {
        val ro_cco = list!![position]
        Log.e("List size ", list!!.size.toString())
        if ((ro_cco.name != null) and (ro_cco.formula != null)) {
            val edited_composition : CharSequence = Html.fromHtml(ro_cco.formula)
            holder.txt_view.text = edited_composition
        }
        holder.txt_view.typeface = FontManager.comic_sans
    }


    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }


    override fun getFilter(): Filter {
        return this.filter
    }


    inner class DataViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txt_view: TextView

        init {
            txt_view = itemView.findViewById(R.id.txt_equation)
        }
    }


    private inner class Searcher : Filter() {

        override fun performFiltering(key: CharSequence): Filter.FilterResults? {

            if (key.length == 0) return null

            val sub_keys : Array<String> = key.toString().toUpperCase().split("\\+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val result = Filter.FilterResults()

            val newList = ArrayList<RO_Chemical_Composition>()

//            for (item in defaultList) {
//                var isMatched = true
//                for (sub_key in sub_keys) {
//                    if (!(item.cleanedcomposition).toUpperCase().contains(sub_key)) {
//                        Log.e("Item is ", item.cleanedcomposition + " doesn't contain " + sub_key)
//                        isMatched = false
//                        break
//                    }
//                }
//                if (isMatched) newList.add(item)
//            }

            result.values = newList

            return result
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults?) {

            if (results != null && results.values != null) {
                list = results.values as ArrayList<RO_Chemical_Composition>
            } else {
                list = defaultList
            }

            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun OnItemClickListener(view: View, composition: RO_Chemical_Composition, position: Int)
    }

}

