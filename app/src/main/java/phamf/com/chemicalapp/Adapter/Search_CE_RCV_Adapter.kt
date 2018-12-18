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
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.Manager.FontManager


@Suppress("DEPRECATION")
class Search_CE_RCV_Adapter(internal var context: Context) : RecyclerView.Adapter<Search_CE_RCV_Adapter.DataViewHolder>(), Filterable {

    private var filter: Searcher

    var list: ArrayList<RO_ChemicalEquation>? = null
        internal set

    internal var defaultList: ArrayList<RO_ChemicalEquation>

    internal var itemClickListener: OnItemClickListener? = null

    private var rcv: RecyclerView? = null

    private val onClickListener = View.OnClickListener { v ->
        if (itemClickListener != null) {
            val position = rcv!!.indexOfChild(v)

            if (position >= 0) {
                val equation = getItem(position)
                itemClickListener!!.OnItemClickListener(v, equation, position)
            }
        }
    }

    init {
        list = ArrayList()
        defaultList = ArrayList()
        filter = Searcher()
    }


    private fun process(startPos: Int, s: String): String {
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
                                s = process(j + 11, s_builder.toString())
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
     * after be setted to Search List Adapter
     */
    fun setData(ro_chemicalEquations: Collection<RO_ChemicalEquation>) {
        list = ArrayList()
        defaultList = ArrayList()
        for (equation in ro_chemicalEquations) {
            val ro_equation = RO_ChemicalEquation()

            ro_equation.condition = equation.condition
            ro_equation.id = equation.id
            ro_equation.equation = equation.equation
            ro_equation.phenonema = equation.phenonema
            ro_equation.how_to_do = equation.how_to_do
            ro_equation.cleanedEquation = equation.cleanedEquation

            list!!.add(ro_equation)
            defaultList.add(ro_equation)
        }
        notifyDataSetChanged()
    }


    private fun cache() {

    }

    private fun clearCache() {

    }


    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    fun isSearching(isSearching: Boolean) {

    }


    fun getItem(position: Int): RO_ChemicalEquation {
        return list!![position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_CE_RCV_Adapter.DataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.child_search_chemical_equation, parent, false)
        view.setOnClickListener(onClickListener)
        return DataViewHolder(view)

    }


    override fun onBindViewHolder(holder: Search_CE_RCV_Adapter.DataViewHolder, position: Int) {
        val ro_ce = list!![position]
        Log.e("List size ", list!!.size.toString())
        if (ro_ce.equation != null) {
            val edited_equation : CharSequence = Html.fromHtml(ro_ce.equation)
            holder.txt_view.text = edited_equation
        }
        holder.txt_view.typeface = FontManager.arial
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

            val newList = ArrayList<RO_ChemicalEquation>()

            for (item in defaultList) {
                var isMatched = true
                for (sub_key in sub_keys) {
                    if (!(item.cleanedEquation).toUpperCase().contains(sub_key)) {
                        Log.e("Item is ", item.cleanedEquation + " doesn't contain " + sub_key)
                        isMatched = false
                        break
                    }
                }
                if (isMatched) newList.add(item)
            }

            result.values = newList

            return result
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults?) {

            if (results != null && results.values != null) {
                list = results.values as ArrayList<RO_ChemicalEquation>
            } else {
                list = defaultList
            }

            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun OnItemClickListener(view: View, equation: RO_ChemicalEquation, position: Int)
    }

}

