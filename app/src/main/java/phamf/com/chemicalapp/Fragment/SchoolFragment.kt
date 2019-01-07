package com.schoolsupport.app.dmt91.schoolsupport.View.Fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import phamf.com.chemicalapp.Fragment.LocalListFragment
import phamf.com.chemicalapp.Fragment.LocalPostFragment
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.FragmentSchoolBinding


class SchoolFragment : Fragment() {
    private lateinit var mBinding : FragmentSchoolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_school, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
        var localList : LocalListFragment = LocalListFragment.newInstance()
        switchToFragmentList()
    }

    fun addControls() {

    }

    fun addEvents() {
    }

    private fun switchToFragmentList () {
        var fm = fragmentManager
        var ft = fm!!.beginTransaction()
        ft.replace(R.id.localParent, LocalListFragment.newInstance(), "abcxyz")
        ft.addToBackStack("Post2")
        ft.commit()
    }

    companion object {
        private val instance : SchoolFragment = SchoolFragment()
        @JvmStatic
        fun newInstance() : SchoolFragment {
            return SchoolFragment()
        }
    }
}
