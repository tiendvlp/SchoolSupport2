package phamf.com.chemicalapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_graph_menu.*
import phamf.com.chemicalapp.CustomView.GraphDrawer
import phamf.com.chemicalapp.SpecialGraphMenuActivity.Companion.GRAPH_INFO
import phamf.com.chemicalapp.ViewModel.GraphInfo

class GraphMenuActivity : FullScreenActivity() {

    companion object {
        val SOME_GRAPH = -3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_menu)

        addEvent()
    }

    private fun addEvent() {


        graph_menu_btn_process.setOnClickListener {
            var startX = graph_menu_edt_startX.text.toString().toFloat()
            var endX = graph_menu_edt_endX.text.toString().toFloat()
            var calc_content = graph_menu_edt_calc_content.text.toString()
            var calc_content1 = graph_menu_edt_calc_content1.text.toString()
            var calc_content2 = graph_menu_edt_calc_content2.text.toString()
            var calc_content3 = graph_menu_edt_calc_content3.text.toString()
            var calc_content4 = graph_menu_edt_calc_content4.text.toString()

            var graph_info_list = ArrayList<GraphInfo>()

            if (calc_content != "") {
                graph_info_list.add(GraphInfo(startX, endX, (graph_menu_color_graph1.background as ColorDrawable).color, calc_content, null, SOME_GRAPH))
            }

            if (calc_content1 != "") {
                graph_info_list.add(GraphInfo(startX, endX,(graph_menu_color_graph2.background as ColorDrawable).color, calc_content1, null, SOME_GRAPH))
            }

            if (calc_content2 != "") {
                graph_info_list.add(GraphInfo(startX, endX,(graph_menu_color_graph3.background as ColorDrawable).color, calc_content2, null, SOME_GRAPH))
            }

            if (calc_content3 != "") {
                graph_info_list.add(GraphInfo(startX, endX,(graph_menu_color_graph4.background as ColorDrawable).color, calc_content3, null, SOME_GRAPH))
            }

            if (calc_content4 != "") {
                graph_info_list.add(GraphInfo(startX, endX,(graph_menu_color_graph5.background as ColorDrawable).color, calc_content4, null, SOME_GRAPH))
            }

            var intent = Intent(this@GraphMenuActivity, NormalGraphDrawerActivity::class.java)
            intent.putParcelableArrayListExtra(NormalGraphDrawerActivity.GRAPH_INFO, graph_info_list)
            startActivity(intent)
        }

    }

}
