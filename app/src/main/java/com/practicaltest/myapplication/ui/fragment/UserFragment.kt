package com.practicaltest.myapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.practicaltest.myapplication.R
import com.practicaltest.myapplication.databinding.FragmentUserlistBinding
import com.practicaltest.myapplication.utils.CommonUtils
import com.practicaltest.myapplication.utils.Resource
import com.practicaltest.myapplication.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : Fragment() {

    private var binding: FragmentUserlistBinding by autoCleared()
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    var page = "1"
    var per_page = "5"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setSwipeRefreshLayout()
        setRecyclerViewScrollListener()

    }
    private fun setRecyclerViewScrollListener() {
       /* binding.rvRestaurantlist.addOnScrollListener()
        binding.rvRestaurantlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                Log.d("Test: cnt: ",""+totalItemCount)
                *//*if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
                    requestPhoto()
                }*//*
            }
        })*/
    }


    private fun setupRecyclerView() {
        adapter = context?.let { UserListAdapter(it) }!!
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRestaurantlist.layoutManager = linearLayoutManager
        binding.rvRestaurantlist.adapter = adapter

    }

    fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                binding.swipeRefresh.isRefreshing = true
                page = "1"
                per_page = "5"
                if (CommonUtils.isNetworkAvailable(activity as Context)) {
                    setupObservers()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_internet_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


    }

    private fun setupObservers() {
        getActivity()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        viewModel.getUserDetails(page, per_page).observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = false
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


}
