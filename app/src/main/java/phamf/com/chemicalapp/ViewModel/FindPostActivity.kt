package phamf.com.chemicalapp.ViewModel

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import phamf.com.chemicalapp.Abstraction.Interface.ILoadMore
import phamf.com.chemicalapp.Adapter.MainPostListViewAdapter
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.ActivityFindPostBinding

class FindPostActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    private lateinit var mLayoutBinding:ActivityFindPostBinding
    private lateinit var mAdapter : MainPostListViewAdapter
    private lateinit var mResultList : ArrayList<Post?>
    private lateinit var mDatabase : DatabaseReference
    private var searchBy : String = FindPostActivity.BY_TITLE
    companion object {
        val BY_TAG = "tag"
        val BY_TITLE = "title"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_post)
        addControls()
        addEvents()
    }

    private fun addEvents() {
        mLayoutBinding.edtKeyWord.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                search()
                return false
            }

        })
    }

    private fun addControls() {
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_find_post)
        mDatabase = FirebaseDatabase.getInstance().reference
        mResultList = ArrayList()
        val layoutManager : LinearLayoutManager = LinearLayoutManager(this)
        mLayoutBinding.lvResult.layoutManager =layoutManager
        mLayoutBinding.rbtngr.setOnCheckedChangeListener(this)
        mAdapter = MainPostListViewAdapter(this, mResultList, mLayoutBinding.lvResult)
        mLayoutBinding.lvResult.adapter = mAdapter
        mLayoutBinding.rbtngr.check(R.id.rbtnPosts)
    }

    private fun search () {
        mResultList.clear()
        mAdapter.notifyDataSetChanged()
        var tag  = mLayoutBinding.edtKeyWord.text.toString()
        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show()
        mDatabase.child(AppDataSingleton.WhereNewPostWrite)
                .orderByChild("title")
                .startAt(tag.toString())
                .endAt(tag.toString()+"\uf8ff")
                .addListenerForSingleValueEvent(object:ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.children.forEach {
                            var data: Post = it.getValue(Post::class.java)!!
                            mResultList.add(data)
                        }
                        mAdapter.notifyDataSetChanged()
                    }    })
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
//        if (checkedId == R.id.rbtnTag) {
//            searchBy = FindPostActivity.BY_TAG
//        }
//        else if (checkedId == R.id.rbtnPosts) {
//            searchBy = FindPostActivity.BY_TITLE
//        }
//        mResultList.clear()
//        Log.d("clearrr", ""+mResultList.size )
//        mAdapter.notifyDataSetChanged()
    }


    private fun searchByTag () {

    }

    private fun searchByTitle () {

    }


}
