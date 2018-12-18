package phamf.com.chemicalapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Script
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_dpdp_menu.*
import kotlinx.android.synthetic.main.activity_main.*
import phamf.com.chemicalapp.Abstraction.AbstractClass.RCV_Menu_Adapter
import phamf.com.chemicalapp.Abstraction.Interface.IDPDPMenuActivity
import phamf.com.chemicalapp.Adapter.DPDP_Menu_Adapter
import phamf.com.chemicalapp.Manager.AppThemeManager
//import phamf.com.chemicalapp.Presenter.DPDPMenuActivityPresenter
import phamf.com.chemicalapp.RO_Model.RO_DPDP
import phamf.com.chemicalapp.Manager.FullScreenManager
import phamf.com.chemicalapp.ViewModel.MVVM_DPDPMenuActivityPresenter

/**
 * Presenter
 * @see DPDPMenuActivityPresenter
 */
class DPDPMenuActivity : FullScreenActivity(), IDPDPMenuActivity.View {

    lateinit var rcv_dpdp_adapter: DPDP_Menu_Adapter

    private var viewModel: MVVM_DPDPMenuActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dpdp_menu)

        ButterKnife.bind(this)

        setUpViewModel()

        setTheme()

        addControl()

        addEvent()

        viewModel?.loadData()
    }

    fun setUpViewModel () {

        viewModel = ViewModelProviders.of(this).get(MVVM_DPDPMenuActivityPresenter::class.java)

        var dpdp_observer = Observer<ArrayList<RO_DPDP>?> { ro_dpdps -> rcv_dpdp_adapter.setData(ro_dpdps!!) }

        viewModel!!.ldt_ro_dpdp.observe(this, dpdp_observer)
    }

    override fun addControl() {
        rcv_dpdp_adapter = DPDP_Menu_Adapter(this)
        rcv_dpdp_adapter.adaptFor(rcv_dpdp_menu!!)

        bg_night_mode_dpdp_menu!!.visibility = if (AppThemeManager.isOnNightMode) View.VISIBLE
                                               else View.INVISIBLE
    }

    override fun addEvent() {

        var onItemClickListener = object : RCV_Menu_Adapter.OnItemClickListener<RO_DPDP> {
            override fun onItemClickListener(item: RO_DPDP) {
                val intent = Intent(this@DPDPMenuActivity, DPDPActivity::class.java)
                intent.putExtra(DPDP_NAME, item)
                startActivity(intent)
            }
        }
        rcv_dpdp_adapter.setOnItemClickListener(onItemClickListener)

        btn_dpdp_menu_back!!.setOnClickListener { v -> finish() }
    }

    override fun setTheme() {
        if (AppThemeManager.isCustomingTheme) {
            if (AppThemeManager.isUsingColorBackground)
                parent_dpdp_menu_activity!!.setBackgroundColor(AppThemeManager.getBackgroundColor_())
            else
                parent_dpdp_menu_activity!!.background = AppThemeManager.getBackgroundDrawable_()
        }
    }

    companion object {

        val DPDP_NAME = "dpdp_name"

        val DPDP_CONTENT = "lesson_content"
    }

}
