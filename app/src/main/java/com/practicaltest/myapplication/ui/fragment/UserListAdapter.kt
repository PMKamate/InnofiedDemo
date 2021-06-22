package com.practicaltest.myapplication.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.practicaltest.myapplication.R
import com.practicaltest.myapplication.data.entities.UserDataItem
import com.practicaltest.myapplication.databinding.AdapterUserlistItemBinding

class UserListAdapter(var mContext: Context) :
    RecyclerView.Adapter<UserListViewHolder>() {
    private val items = ArrayList<UserDataItem>()
    private val LOADING = 0
    private val ITEM = 1
    private val isLoadingAdded = false
    fun setItems(items: ArrayList<UserDataItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding: AdapterUserlistItemBinding =
            AdapterUserlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding, mContext)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) =
        holder.bind(items[position])
}

class UserListViewHolder(
    private val itemBinding: AdapterUserlistItemBinding,
    private val mContext: Context
) : RecyclerView.ViewHolder(itemBinding.root){
    private lateinit var restaurantItem: UserDataItem
    @SuppressLint("SetTextI18n", "CheckResult", "UseCompatLoadingForDrawables")
    fun bind(item: UserDataItem) {
        this.restaurantItem = item
        itemBinding.name.text = item.firstName + " "+ item.lastName
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

