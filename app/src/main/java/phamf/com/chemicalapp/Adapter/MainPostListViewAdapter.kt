package phamf.com.chemicalapp.Adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.squareup.picasso.Picasso
import phamf.com.chemicalapp.Abstraction.Interface.ILoadMore
import phamf.com.chemicalapp.Model.Post
import phamf.com.chemicalapp.PostViewModel
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.ReadPageActivity
import phamf.com.chemicalapp.databinding.MainListpostViewBinding
import phamf.com.chemicalapp.supportClass.getViewModel

class MainPostListViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, View.OnClickListener {

    private val VIEW_TYPE_ITEM =0
    private val VIEW_TYPE_LOADING = 1
    private var postList : ArrayList<Post?>
    private var context : Context
    private var recyclerView : RecyclerView
    private  var loadmore:ILoadMore = object : ILoadMore {
        override fun onLoadMore() {

        }

    }
    private var isLoading:Boolean = false;
    private var visibleThreshold=5
    private var lastVisibleItem : Int = -1
    private var totalItemCount:Int = -1

    constructor( context : Context, dataList : ArrayList<Post?>, recyclerView: RecyclerView) {
        this.postList =dataList
        this.context =context
        this.recyclerView =recyclerView
        val linearLayoutManager : LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)) {
                    if (loadmore != null) {
                        loadmore.onLoadMore()
                    }
                }
                isLoading = true
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        if (postList[position] == null) {
            return VIEW_TYPE_LOADING
        } else {
            return VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if (viewType == VIEW_TYPE_ITEM) {
           var layoutBinding : MainListpostViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_listpost_view, parent, false)
           return ViewHolder(layoutBinding, context)
       } else {
           var v : View = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
           return LoadingViewHolder(v)
       }
    }

    fun setLoadMore (loadmore:ILoadMore) {
        this.loadmore = loadmore
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setLoaded () {
        isLoading = false
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(postList[position]!!)
            holder.layoutBinding.root.setOnClickListener(this)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun onClick(v: View?) {
        var pos = recyclerView.getChildLayoutPosition(v)
        ReadPageActivity.readingPost = postList[pos]!!
        context.startActivity(Intent(context, ReadPageActivity::class.java))
    }

    class LoadingViewHolder : RecyclerView.ViewHolder {
         var progressBar:ProgressBar
        constructor (itemView:View ) : super(itemView) {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var layoutBinding : MainListpostViewBinding
        constructor(layoutBinding:MainListpostViewBinding, context: Context) : super(layoutBinding.root) {
            this.layoutBinding = layoutBinding
            val viewModel : PostViewModel = PostViewModel()
            this.layoutBinding.mModel = viewModel
        }

        fun bind (post: Post) {
            this.layoutBinding.mModel!!.post.value = post
            Picasso.get().load(post.Avatar).into(this.layoutBinding.imgAvatar)
        }
    }
}
