package com.loise

import android.util.Log
import androidx.paging.LivePagedListBuilder

class User(
        private val service: GithubService,
        private val cache: LocalCache,
        private val userCallback: UserCallback
):BoundaryCondition.Callback {
    interface UserCallback{
        fun onLoadStart()
        fun onLoadFinish()
        fun onLoadError(e:String)
    }

    override fun onRequestError(e: String) {
        userCallback.onLoadError(e)
    }

    override fun onRequestStart() {
         userCallback.onLoadStart()
    }

    override fun onRequestFinished() {
         userCallback.onLoadFinish()
    }
    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): UserResult {
        Log.d("Repository", "New query: $query")

        // Get data source factory from the local cache
        val dataSourceFactory = cache.usersByName(query)

        // Construct the boundary callback
        val boundaryCallback = BoundaryCondition(query, service, cache,this)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data=LivePagedListBuilder(dataSourceFactory,DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        // Get the network errors exposed by the boundary callback
        return UserResult(data, networkErrors)
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 100
    }
}