package phamf.com.chemicalapp.Abstraction.AbstractClass

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import phamf.com.chemicalapp.Abstraction.Interface.Menuable
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.Manager.FontManager

abstract class RCV_Menu_Adapter<T : Menuable>(protected var context: Context) : RecyclerView.Adapter<RCV_Menu_Adapter.DataViewHolder>() {


    protected lateinit var rcv_menu: RecyclerView


    protected var list: List<T>? = null


    protected lateinit var itemClickListener: OnItemClickListener<T>


    private val onClickListener = View.OnClickListener { v ->
        val index = rcv_menu.indexOfChild(v)
        itemClickListener.onItemClickListener(list!![index])
    }


    init {
        list = ArrayList()
    }

    abstract fun create_ViewHolder(parent: ViewGroup, viewType: Int): View

    abstract fun bind_ViewHolder(itemView: View, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RCV_Menu_Adapter.DataViewHolder {
        //        View view = LayoutInflater.from(context).inflate(R.layout.child_menu_view, parent, false);
        //        view.setOnClickListener(onClickListener);
        val view = create_ViewHolder(parent, viewType)
        view.setOnClickListener(onClickListener)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: RCV_Menu_Adapter.DataViewHolder, position: Int) {

        //        T chapter = list.get(position);
        //        holder.txt_index.setText(String.valueOf(chapter.getId()));
        //        holder.txt_name.setText(chapter.getName());
        bind_ViewHolder(holder.itemView, list!![position])
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }


    fun adaptFor(rcv_menu: RecyclerView) {
        this.rcv_menu = rcv_menu
        rcv_menu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_menu.adapter = this
    }


    fun setData(items: Collection<T>) {
        list = items as List<T>
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>) {
        this.itemClickListener = itemClickListener
    }


    interface OnItemClickListener<T : Menuable> {

        fun onItemClickListener(item: T)

    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var txt_name: TextView
        lateinit var txt_index: TextView

        init {
            txt_index = itemView.findViewById(R.id.txt_item_index)
            txt_name = itemView.findViewById(R.id.txt_item_name)
            txt_index.typeface = FontManager.arial
            txt_name.typeface = FontManager.arial
        }
    }
}
