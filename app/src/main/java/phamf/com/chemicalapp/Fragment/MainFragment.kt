package com.schoolsupport.app.dmt91.schoolsupport.View.Fragment

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup

import com.schoolsupport.app.dmt91.schoolsupport.View.Activity.TextEditorActivity
import phamf.com.chemicalapp.Adapter.pgMainAdapter
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.FragmentMainBinding


class MainFragment : Fragment(), ViewPager.OnPageChangeListener {
    lateinit var rbtngrMain : RadioGroup
    lateinit var pgMain : ViewPager
    lateinit var btnSearch : ImageButton
    lateinit var rbtnTools : RadioButton
    lateinit var rbtnPosts : RadioButton
    lateinit var rbtnSchool : RadioButton
    private lateinit var mBinding : FragmentMainBinding
    private lateinit var mPGMainAdapter : pgMainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_main, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addControls()
        setup()
        addEvents()
    }

    companion object {
        private var instance : MainFragment? = null
        @JvmStatic
        fun newInstance() : MainFragment {
            if (instance == null) {
                this.instance = MainFragment()
            }
            return this.instance!!
        }
    }

    fun addControls () {
        pgMain = mBinding.pgMain
        rbtnPosts =  mBinding.rbtnPosts
        rbtnTools = mBinding.rbtnTools
        rbtnSchool = mBinding.rbtnSchool
        rbtngrMain = mBinding.rbtngrMain
    }

    fun setup () {
        mPGMainAdapter = pgMainAdapter(childFragmentManager!!)
        pgMain.adapter = mPGMainAdapter
        rbtnSchool.setChecked(true)
    }

    fun addEvents () {
        pgMain.setOnPageChangeListener(this)
        rbtngrMain.setOnCheckedChangeListener(::onRadioButtonCheckedChanged)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> rbtnTools.isChecked = true
            1 -> rbtnPosts.isChecked = true
            2 -> rbtnSchool.isChecked = true
        }
    }

    private fun onRadioButtonCheckedChanged (radioGroup: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.rbtnTools -> pgMain.setCurrentItem(0)
            R.id.rbtnPosts -> pgMain.setCurrentItem(1)
            R.id.rbtnSchool -> pgMain.setCurrentItem(2)
        }
    }


}