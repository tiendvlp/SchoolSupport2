package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import phamf.com.chemicalapp.CustomView.AddMoreGraphDrawer
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.NormalGraphDrawerActivity

/**
 * @see NormalGraphDrawerActivity
 */
class LV_Normal_Graph_List (
        var list_view : ListView,

        var context : Context) : BaseAdapter() {

        var graph_list : ArrayList<AddMoreGraphDrawer> = ArrayList()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.child_normal_graph_calc_content_option, parent, false)
        var graph = graph_list[position]

        view.findViewById<ImageView>(R.id.child_normal_graph_color)
                .setBackgroundColor(graph.graph_paint.color)

        view.findViewById<EditText>(R.id.child_normal_graph_edt_calc_content)
                .setText(graph.calc_content)

        view.findViewById<Switch>(R.id.child_normal_graph_sw_visible).setOnCheckedChangeListener {
            view , isChecked ->
            if (isChecked) {
                graph.visibility = VISIBLE
            } else {
                graph.visibility = GONE
            }
        }

        return view
    }

    fun addGraph (graph : AddMoreGraphDrawer) {
        this.graph_list.add(graph)
    }

    override fun getItem(position: Int): AddMoreGraphDrawer{
        return graph_list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return graph_list.size
    }

    fun update_graph () {
        for ((pos, graph) in graph_list.withIndex()) {

            var present_calc_content = list_view.getChildAt(pos).findViewById<EditText>(R.id.child_normal_graph_edt_calc_content)
                                      .text.toString()

            if (present_calc_content  != graph.calc_content) {
                graph.calc_content = present_calc_content
                graph.draw()
            }

        }
    }

}