package com.example.tyme.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessRecyclerOnScrollListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    // The total number of items in the dataset after the last load
    private var previousTotal = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // The minimum amount of items to have below your current scroll position before loading more.
    private val visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        if (totalItemCount == 0) return
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        /*if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }*/

        if (loading) {
            if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                currentPage++
                onLoadMore(currentPage)
                loading = true
            }
        }
    }

    abstract fun onLoadMore(currentPage: Int)
}