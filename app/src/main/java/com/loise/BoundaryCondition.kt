package com.loise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

class BoundaryCondition(
        private val query: String,
        private val service: GithubService,
        private val cache: LocalCache,
        private val callback: Callback
) : PagedList.BoundaryCallback<UserModel>() {
    interface Callback{
        fun onRequestStart()
        fun onRequestFinished()
        fun onRequestError(e:String)
    }

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserModel) {
        requestAndSaveData(query)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        callback.onRequestStart()
        searchUsers(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            repos.forEach{it.updateTime = System.currentTimeMillis()}
            cache.insert(repos) {
                callback.onRequestFinished()
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, {
            callback.onRequestError(it)
            isRequestInProgress = false
            callback.onRequestFinished()
        })
    }
}