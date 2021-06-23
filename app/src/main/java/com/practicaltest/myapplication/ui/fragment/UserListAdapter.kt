package com.practicaltest.myapplication.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.practicaltest.myapplication.R
import com.practicaltest.myapplication.data.entities.UserDataItem
import com.practicaltest.myapplication.databinding.AdapterUserlistItemBinding
import kotlinx.android.synthetic.main.progress_loading.view.*


@Suppress("DEPRECATION")
class UserListAdapter(private var itemsCells: ArrayList<UserDataItem?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var mcontext: Context

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<UserDataItem?>) {
        this.itemsCells.clear()
        this.itemsCells.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): String {
        return itemsCells.get(position)?.id.toString()
    }
    fun setItems(items: ArrayList<UserDataItem>) {
        this.itemsCells.clear()
        this.itemsCells.addAll(items)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            itemsCells.add(null)
            notifyItemInserted(itemsCells.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (itemsCells.size != 0) {
            itemsCells.removeAt(itemsCells.size - 1)
            notifyItemRemoved(itemsCells.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val binding: AdapterUserlistItemBinding =
                AdapterUserlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserListViewHolder(binding, mcontext)
        } else {
            val view =
                LayoutInflater.from(mcontext).inflate(R.layout.progress_loading, parent, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.progressbar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.GREEN, BlendMode.SRC_ATOP)
            } else {
                view.progressbar.indeterminateDrawable.setColorFilter(
                    Color.GREEN,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsCells.get(position) == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            itemsCells.get(position)?.let { (holder as UserListViewHolder).bind(it) }
        }
    }

    class UserListViewHolder(
        private val itemBinding: AdapterUserlistItemBinding,
        private val mContext: Context
    ) : RecyclerView.ViewHolder(itemBinding.root){
        private lateinit var restaurantItem: UserDataItem
        @SuppressLint("SetTextI18n", "CheckResult", "UseCompatLoadingForDrawables")
        fun bind(item: UserDataItem) {
            this.restaurantItem = item
            itemBinding.name.text = item.id.toString()+" " +item.firstName + " "+ item.lastName
            itemBinding.email.text = item.email
            val requestOptions = RequestOptions()
            requestOptions.placeholder(mContext.resources.getDrawable(R.drawable.user_error))
            requestOptions.error(mContext.resources.getDrawable(R.drawable.user_error))
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            requestOptions.centerCrop()
            Glide.with(itemBinding.root)
                .load(item.avatar)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemBinding.image)

        }


    }
}