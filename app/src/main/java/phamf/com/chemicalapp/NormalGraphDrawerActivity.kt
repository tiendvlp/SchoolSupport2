package phamf.com.chemicalapp

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_normal_graph_drawer.*
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.Function
import phamf.com.chemicalapp.CustomView.AddMoreGraphDrawer
import phamf.com.chemicalapp.CustomView.GraphDrawer
import phamf.com.chemicalapp.Supporter.*
import phamf.com.chemicalapp.ViewModel.GraphInfo
import phamf.com.chemicalapp.ViewModel.MVVM_NormalGraphDrawerActivityPresenter
import java.math.BigDecimal

/**
 * @see phamf.com.chemicalapp.ViewModel.MVVM_NormalGraphDrawerActivityPresenter
 * @see phamf.com.chemicalapp.GraphMenu
 */
class NormalGraphDrawerActivity : FullScreenActivity() {

    companion object {
        val GRAPH_INFO = "normal_graph_info"
    }

    lateinit var viewModel : MVVM_NormalGraphDrawerActivityPresenter

    lateinit var suft_right : Animation

    lateinit var suft_left : Animation

    var isTurnOnGraphList = false

    var chooseColor : Int = Color.BLACK

    var graph_list = ArrayList<AddMoreGraphDrawer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_graph_drawer)

        setUpGraphDrawer()

        setUpViewModel()

        addEvent()

        viewModel.loadData(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpGraphDrawer() {
        normal_graph.step = BigDecimal("0.05")
        normal_graph.zoom_index = 100
    }

    fun setUpViewModel () {

        viewModel = MVVM_NormalGraphDrawerActivityPresenter(application)

        viewModel.ldt_graph_info.observe(this, object : Observer<ArrayList<GraphInfo>> {
            override fun onChanged(gr: ArrayList<GraphInfo>?) {

                var txt_graph_list= ArrayList<TextView>()
                txt_graph_list.add(normal_graph_txt_graph_content)
                txt_graph_list.add(normal_graph_txt_graph_content1)
                txt_graph_list.add(normal_graph_txt_graph_content2)
                txt_graph_list.add(normal_graph_txt_graph_content3)
                txt_graph_list.add(normal_graph_txt_graph_content4)

                if ((gr != null) and (gr!!.size > 0)) {
                    draw_graph(gr[0])
                    txt_graph_list[0].text = gr[0].calc_content
                    for (i in 1..gr.size - 1) {
                        addGraph(gr[i])
                        txt_graph_list[i].text = gr[i].calc_content
                    }
                }
            }
        })

    }


    fun addEvent () {

        normal_graph_btn_back.setOnClickListener {
            finish()
        }

        normal_graph_btn_setting.setOnClickListener {
            switchSettingMode()
        }

        normal_graph_btn_plus_zoom.setOnClickListener {
            plusZoomIndex()
        }

        normal_graph_btn_minus_zoom.setOnClickListener {
            decreaseZoomIndex()
        }

        normal_graph_btn_plus_start_x.setOnClickListener {
            increaseXValue()
        }

        normal_graph_btn_minus_start_x.setOnClickListener {
            decreaseStartXValue()
        }

        normal_graph_btn_plus_end_x.setOnClickListener {
            plusEndXValue()
        }

        normal_graph_btn_minus_end_x.setOnClickListener {
            decreaseEndXValue()
        }

        normal_graph_btn_info.setOnClickListener {
            switchInfoMode()
        }
    }

    fun draw_graph (normal_graph_info: GraphInfo) {

        var function = Function("At(x) = " + normal_graph_info.calc_content.processX())

        normal_graph.processer = object : GraphDrawer.ValueProcesser {
            override fun process(x: Float): Float {
                try {
                    var expression = Expression("At($x)", function)
                    return expression.calculate().toFloat()
                } finally {

                }
            }
        }


        normal_graph.start_X_value = normal_graph_info.startX
        normal_graph.end_X_value = normal_graph_info.endX

        normal_graph.graph_paint.color = normal_graph_info.color
        normal_graph.graph_paint.strokeWidth = UnitConverter.DpToPixel(3).toFloat()

        normal_graph.draw()


    }

    fun addGraph (grInfo : GraphInfo) {

        var calc_content = grInfo.calc_content
        var color = grInfo.color

        var new_graph = AddMoreGraphDrawer(this)

        new_graph.zoom_index = normal_graph.zoom_index
        new_graph.start_X_value = normal_graph.start_X_value
        new_graph.end_X_value = normal_graph.end_X_value
        new_graph.calc_content = calc_content
        new_graph.graph_paint.color = color
        var function = Function("At(x) = " + calc_content.processX())
        new_graph.processer = object : AddMoreGraphDrawer.ValueProcesser {
            override fun process(x: Float): Float {
                return Expression("At($x)", function).calculate().toFloat()
            }
        }

        new_graph.layoutParams = normal_graph.layoutParams
        normal_graph_parent.addView(new_graph)
        graph_list.add(new_graph)
    }

    private fun decreaseEndXValue() {
        var start_x_val = normal_graph_txt_start_x_value.text.toString().toInt()
        var end_x_val = normal_graph_txt_end_x_value.text.toString().toInt()
        if ((end_x_val - 5) > start_x_val) {
            end_x_val -= 5
            normal_graph_txt_end_x_value.text = end_x_val.toString()
        } else {
            Toast.makeText(this, "Khoảng giá trị của x phải lớn hơn 5", Toast.LENGTH_LONG).show()
        }
    }

    private fun plusEndXValue() {
        var end_x_val= normal_graph_txt_end_x_value.text.toString().toInt()
        normal_graph_txt_end_x_value.text = (end_x_val + 5).toString()
    }

    private fun decreaseStartXValue() {
        var start_x_val = normal_graph_txt_start_x_value.text.toString().toInt()
        normal_graph_txt_start_x_value.text = (start_x_val - 5).toString()
    }

    private fun increaseXValue() {
        var start_x_val= normal_graph_txt_start_x_value.text.toString().toInt()
        var end_x_val = normal_graph_txt_end_x_value.text.toString().toInt()
        if (start_x_val + 5 < end_x_val) {
            start_x_val += 5
            normal_graph_txt_start_x_value.text = start_x_val.toString()
        } else {
            Toast.makeText(this, "Khoảng giá trị của x phải lớn hơn 5", Toast.LENGTH_LONG).show()
        }
    }

    private fun decreaseZoomIndex() {
        var zoom_index = normal_graph_txt_zoom_index.text.toString().toInt()
        if (zoom_index > 1) zoom_index--
        normal_graph_txt_zoom_index.text = zoom_index.toString()
    }

    fun plusZoomIndex () {
        var zoom_index = normal_graph_txt_zoom_index.text.toString().toInt()
        if (zoom_index < 10) zoom_index++
        normal_graph_txt_zoom_index.text = zoom_index.toString()
    }

    fun switchSettingMode () {
        normal_graph_setting_form.visibility = if (normal_graph_setting_form.visibility == View.VISIBLE) {

            var start_x_val = normal_graph_txt_start_x_value.text.toString().toFloat()
            var end_x_val= normal_graph_txt_end_x_value.text.toString().toFloat()
            var zoom_index = normal_graph_txt_zoom_index.text.toString().toInt()

            if ((start_x_val != normal_graph.start_X_value) or (end_x_val != normal_graph.end_X_value) or (zoom_index != normal_graph.zoom_index)) {
                normal_graph.start_X_value = start_x_val
                normal_graph.end_X_value = end_x_val
                normal_graph.zoom_index = 50 + zoom_index * 10
                normal_graph.draw()

                normal_graph_extreme_n_solution.draw()

                for (graphs in graph_list) {

                    graphs.start_X_value = normal_graph.start_X_value
                    graphs.end_X_value = normal_graph.end_X_value
                    graphs.zoom_index = normal_graph.zoom_index

                    graphs.draw()
                }
            }

            // return
            View.GONE
        } else View.VISIBLE
    }

    fun switchInfoMode () {
        normal_graph_info_form.visibility = if (normal_graph_info_form.visibility == VISIBLE) GONE else VISIBLE
    }
}


