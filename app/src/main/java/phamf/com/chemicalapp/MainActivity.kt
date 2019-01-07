package phamf.com.chemicalapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import com.schoolsupport.app.dmt91.schoolsupport.View.Activity.TextEditorActivity
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.FavoriteFragement
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.MainFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.NotificationFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.UserFragment
import phamf.com.chemicalapp.Adapter.pgMainAdapter
import phamf.com.chemicalapp.Database.OfflineDatabaseManager
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation
import phamf.com.chemicalapp.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class
MainActivity : FullScreenActivity() {
    lateinit var rbtngrMain : RadioGroup
    lateinit var rbtnUser : RadioButton
    lateinit var rbtnMain : RadioButton
    lateinit var rbtnFavorite : RadioButton
    lateinit var rbtnNotification : RadioButton
    lateinit var btnCreatePosts : ImageButton
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mPGMainAdapter : pgMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addControls()
        addEvents()
        setup()
    }

    fun addControls () {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        rbtnUser =  mBinding.rbtnUser
        rbtnFavorite = mBinding.rbtnFavorite
        rbtnMain = mBinding.rbtnMain
        rbtngrMain = mBinding.rbtnGroup
        btnCreatePosts = mBinding.btnCreatePost
    }

    fun setup () {
        mPGMainAdapter = pgMainAdapter(supportFragmentManager!!)
    }

    fun addEvents () {
        btnCreatePosts.setOnClickListener({
            startActivity(Intent(this, TextEditorActivity::class.java))
        })
        rbtngrMain.setOnCheckedChangeListener(::onGroupCheckedChanged)
    }

    private fun onGroupCheckedChanged (btn: RadioGroup, checkedID : Int) {
        when (checkedID) {
            R.id.rbtnMain -> switchFragment(MainFragment.newInstance())
            R.id.rbtnFavorite -> switchFragment(FavoriteFragement.newInstance())
            R.id.rbtnNotification -> switchFragment(NotificationFragment.newInstance())
            R.id.rbtnUser -> switchFragment(UserFragment.newInstance())
        }
    }

    private fun switchFragment (fragment:Fragment) {
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.contentView, fragment)
        ft.commit()
    }
}