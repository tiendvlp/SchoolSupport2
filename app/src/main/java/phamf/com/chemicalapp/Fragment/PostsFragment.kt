package com.schoolsupport.app.dmt91.schoolsupport.View.Fragment

import android.databinding.DataBindingUtil
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import phamf.com.chemicalapp.Adapter.MainPostListViewAdapter
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.Model.PostRead
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.FragmentPostsBinding
import android.support.v7.widget.DividerItemDecoration
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.common.util.ArrayUtils
import phamf.com.chemicalapp.Abstraction.Interface.ILoadMore
import phamf.com.chemicalapp.Model.AppDataSingleton
import java.util.*


class PostsFragment : Fragment() {
    private var mPostRead = PostRead()
    private var mListData : ArrayList<Post?> = ArrayList()
    private lateinit var mAdapter : MainPostListViewAdapter
    private lateinit var mBinding:FragmentPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_posts, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControls()
        addEvents()
        setup()
        loadHotData()
    }

    private fun setup() {

    }

    fun addControls() {
        var manager : LinearLayoutManager = LinearLayoutManager(context)
        mBinding.lvPost.layoutManager = manager
        mAdapter = MainPostListViewAdapter(context!!, mListData, mBinding.lvPost)
        mBinding.lvPost.adapter = mAdapter
        mAdapter.setLoadMore(object : ILoadMore {
            override fun onLoadMore() {
                mListData.add(null)
                mAdapter.notifyItemInserted(mListData.size-1)
                loadHotData()
            }
        })
    }

    fun addEvents() {
    }

    private fun loadHotData () {
        val callback : PostRead.MultipleValueEvent = PostRead.MultipleValueEvent(::onReadHotSucc,::onFailed )
        mPostRead.getFastestGrowthPost(AppDataSingleton.WhereNewPostWrite, startAt.toInt(), callback)
    }


    private var startAt:Long = 99999999999999999
    fun onReadHotSucc (data: ArrayList<Post>) {
        var pos = mListData.indexOf(null)
        mListData.remove(null)
        mAdapter.notifyItemRemoved(pos)
        if (data.size<1) {
            return
        }
            startAt = data[0].Growth.toLong() + 1
        if (mListData.size > 0) {
            data.remove(data[data.size-1])
        }
        if (data.size<1) {
            return
        }
        reverseArray(data)
        mListData.addAll(data)
        mAdapter.notifyDataSetChanged()
        mAdapter.setLoaded()
    }

    private fun <T> reverseArray(data: ArrayList<T>){
        for (i in 0 until data.size / 2) {
            val temp = data[i]
            data[i] = data[data.size - i - 1]
            data[data.size - i - 1] = temp
        }
    }

    fun onFailed (e:java.lang.Exception?) {

    }
    companion object {
        @JvmStatic
        fun newInstance() : PostsFragment {
            return PostsFragment()
        }
    }
}
