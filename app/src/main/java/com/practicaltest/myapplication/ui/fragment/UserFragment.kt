package com.practicaltest.myapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import com.practicaltest.myapplication.data.entities.UserDataItem
import com.practicaltest.myapplication.databinding.FragmentUserlistBinding
import com.practicaltest.myapplication.utils.RecyclerViewLoadMoreScroll
import com.practicaltest.myapplication.utils.Resource
import com.practicaltest.myapplication.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
@AndroidEntryPoint
class UserFragment : Fragment() {

    private var binding: FragmentUserlistBinding by autoCleared()
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var loadMoreItemsCells: ArrayList<UserDataItem?>
    lateinit var itemsCells: ArrayList<UserDataItem?>
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private var PAGE_START = 1
    private var per_page = "5"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsCells = ArrayList()
        setupObservers()
        setAdapter()
        setRVLayoutManager()
        setRVScrollListener()
        setSwipeRefreshLayout()
    }

    private fun setAdapter() {
        adapter = UserListAdapter(itemsCells)
        adapter.notifyDataSetChanged()
        binding.rvRestaurantlist.adapter = adapter
    }

    private fun setRVLayoutManager() {
        linearLayoutManager = LinearLayoutManager(context)
        binding.rvRestaurantlist.layoutManager = linearLayoutManager
        binding.rvRestaurantlist.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        linearLayoutManager = LinearLayoutManager(context)
        scrollListener = RecyclerViewLoadMoreScroll(linearLayoutManager as LinearLayoutManager)
        binding.rvRestaurantlist.setHasFixedSize(true)

        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        binding.rvRestaurantlist.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        adapter.addLoadingView()
        loadMoreItemsCells = ArrayList()
        PAGE_START++
        Handler().postDelayed({
            setupObservers1()
        }, 3000)

    }


    private fun setupObservers() {
        getActivity()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        viewModel.getUserDetails(PAGE_START.toString(), per_page).observe(
            viewLifecycleOwner, {
                binding.swipeRefresh.isRefreshing = false
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    }
                    Resource.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING ->
                        binding.progressBar.visibility = View.VISIBLE
                }
            })

    }

    fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                binding.swipeRefresh.isRefreshing = true
                PAGE_START = 1
                setupObservers()
            }
        })
    }

    private fun setupObservers1() {
        viewModel.getUserDetails(PAGE_START.toString(), per_page).observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        if (!it.data.isNullOrEmpty())
                            loadMoreItemsCells.addAll(it.data)
                        adapter.removeLoadingView()
                        adapter.addData(loadMoreItemsCells)
                        scrollListener.setLoaded()
                        binding.rvRestaurantlist.post {
                            adapter.notifyDataSetChanged()
                        }
                    }
                    Resource.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING ->
                        binding.progressBar.visibility = View.VISIBLE
                }
            })
    }


}
