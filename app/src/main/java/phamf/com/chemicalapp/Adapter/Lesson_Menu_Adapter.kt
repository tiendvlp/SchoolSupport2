package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.RO_Model.RO_Lesson
import phamf.com.chemicalapp.Manager.FontManager

class Lesson_Menu_Adapter(context: Context) : RCV_Menu_Adapter<RO_Lesson>(context) {

    override fun create_ViewHolder(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.child_menu_view, parent, false)
    }

    override fun bind_ViewHolder(itemView: View, item: RO_Lesson) {
        val txt_index = itemView.findViewById<TextView>(R.id.txt_item_index)
        val txt_name = itemView.findViewById<TextView>(R.id.txt_item_name)

        txt_index.typeface = FontManager.arial
        txt_name.typeface = FontManager.arial

        txt_index.text = item.id.toString()
        txt_name.text = item.name
    }
}
