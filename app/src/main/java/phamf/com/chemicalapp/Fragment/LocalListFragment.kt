package phamf.com.chemicalapp.Fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.schoolsupport.app.dmt91.schoolsupport.View.Fragment.PostsFragment
import phamf.com.chemicalapp.Adapter.LocalListAdapter
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.Local
import phamf.com.chemicalapp.Model.Local2

import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.FragmentLocalListBinding

class LocalListFragment : Fragment() {

    private lateinit var mAdapter : LocalListAdapter
    private var listLocal : ArrayList<Local2> = ArrayList()
    private lateinit var mBinding : FragmentLocalListBinding
    private lateinit var mStorage:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun addEvents() {

    }

    private fun addControls() {
        listLocal=ArrayList()
        mStorage = FirebaseDatabase.getInstance().reference
        var layoutManager : LinearLayoutManager = LinearLayoutManager(this.context)
        mBinding.lvListLocal.layoutManager = layoutManager
        mAdapter = LocalListAdapter(mBinding.lvListLocal, listLocal, context!!)
        mBinding.lvListLocal.adapter = mAdapter
        mAdapter.setChildClickListener(LocalListAdapter.event(::onChildClickedListender))
        getLocal()
    }

    private fun getLocal () {
        mStorage.child("LocalPost").addChildEventListener(object:ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var data : Local2 = p0.getValue(Local2::class.java)!!
                listLocal.add(data)
                mAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }


        })
    }

    private fun onChildClickedListender (pos:Int, local:Local2) {
        Toast.makeText(context, "" + pos, Toast.LENGTH_SHORT).show()
        AppDataSingleton.WhereNewPostWrite = "LocalPost/"+local.id+"/posts"
        var fm = parentFragment!!.fragmentManager
        var ft = fm!!.beginTransaction()
        ft.replace(R.id.localParent, PostsFragment.newInstance(), "Postaa")
        ft.addToBackStack("Posttt23")
        ft.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_local_list, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addControls()
        addEvents()
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
    }

    companion object {
        private val instance : LocalListFragment = LocalListFragment()
        @JvmStatic
        fun newInstance() = LocalListFragment()
    }
}
