package phamf.com.chemicalapp.Adapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import phamf.com.chemicalapp.Model.Local
import phamf.com.chemicalapp.Model.Local2
import phamf.com.chemicalapp.R
import phamf.com.chemicalapp.databinding.LocalListItemViewBinding

class LocalListAdapter : RecyclerView.Adapter<LocalListAdapter.viewHolder>, View.OnClickListener {

    private var mRecyclerView : RecyclerView
    private var mData : ArrayList<Local2>
    private var mContext : Context
    private lateinit var onChildClickListener:event
    constructor(recyclerView: RecyclerView, dataList:ArrayList<Local2>, context:Context) {
        this.mRecyclerView = recyclerView
        this.mData = dataList
        this.mContext = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var mBinding:LocalListItemViewBinding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.local_list_item_view, parent, false)
        return viewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setChildClickListener (callback:event) {
        this.onChildClickListener = callback
    }

    override fun onClick(v: View?) {
        var pos = mRecyclerView.getChildLayoutPosition(v)
        onChildClickListener.onChildClickListener(pos, mData[pos])
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.layoutBinding.root.setOnClickListener(this)
        holder.bind(mData[position])
    }

    class viewHolder : RecyclerView.ViewHolder {
        val layoutBinding:LocalListItemViewBinding
        constructor(layoutBinding: LocalListItemViewBinding) : super(layoutBinding.root) {
            this.layoutBinding = layoutBinding
        }

        fun bind (local:Local2) {
            layoutBinding.txtCreator.text = local.Creator
            layoutBinding.txtName.text = local.Name
        }
    }

    data class event (var onChildClickListener:(Int, Local2)->Unit)
}