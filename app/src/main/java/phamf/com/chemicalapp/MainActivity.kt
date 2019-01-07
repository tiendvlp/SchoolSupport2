package phamf.com.chemicalapp

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth
import com.schoolsupport.app.dmt91.schoolsupport.View.Activity.TextEditorActivity
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.FavoriteFragement
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.MainFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.NotificationFragment
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.UserFragment
import com.schoolsupport.app.dmt91.schoolsupport.model.MemberModel
import phamf.com.chemicalapp.Adapter.pgMainAdapter
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.MemberRead
import phamf.com.chemicalapp.databinding.ActivityMainBinding
import phamf.com.chemicalapp.supportClass.getViewModel
import java.lang.Exception

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){
    lateinit var rbtngrMain : RadioGroup
    lateinit var rbtnUser : RadioButton
    lateinit var rbtnMain : RadioButton
    lateinit var rbtnFavorite : RadioButton
    lateinit var rbtnNotification : RadioButton
    lateinit var btnCreatePosts : ImageButton
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mPGMainAdapter : pgMainAdapter
    private lateinit var mModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addControls()
        addEvents()
        setup()
    }

    fun addControls () {
        mModel = getViewModel()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.mProgressBar.visibility = View.GONE
        rbtnUser =  mBinding.rbtnUser
        rbtnFavorite = mBinding.rbtnFavorite
        rbtnMain = mBinding.rbtnMain
        rbtngrMain = mBinding.rbtnGroup
        btnCreatePosts = mBinding.btnCreatePost
    }

    fun setup () {
        mPGMainAdapter = pgMainAdapter(supportFragmentManager!!)
        val callback : MemberRead.Event = MemberRead.Event(::getUserSuccess, ::getUserFailed)
        mModel.getMember(FirebaseAuth.getInstance().currentUser!!.uid, callback)
    }

    fun getUserSuccess (user:MemberModel) {
        AppDataSingleton.getInstance().currentUser = user
        Log.d("getuserfromdb", "Ten: " + AppDataSingleton.getInstance().currentUser.Name)
        Log.d("getuserfromdb", "Id: " + AppDataSingleton.getInstance().currentUser.Id)
    }

    fun getUserFailed (e:Exception?) {

    }

    fun addEvents () {
        btnCreatePosts.setOnClickListener({
            when (AppDataSingleton.btnCreateAction) {
                AppDataSingleton.ACTION_CREATE_NEW_WORLD_POST -> {
                    mBinding.mProgressBar.visibility = View.VISIBLE
                    AppDataSingleton.WhereNewPostWrite = "WorldPost"
                    startActivity(Intent(this, TextEditorActivity::class.java))
                }
                AppDataSingleton.ACTION_CREATE_NEW_LOCAL_POST -> {
                    mBinding.mProgressBar.visibility = View.VISIBLE
                    startActivity(Intent(this, TextEditorActivity::class.java))
                }

            }
            mBinding.mProgressBar.visibility = View.VISIBLE

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

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}