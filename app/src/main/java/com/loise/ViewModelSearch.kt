package com.loise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class ViewModelSearch(private val user: User) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val userResult: LiveData<UserResult> = Transformations.map(queryLiveData) {
        user.search(it)
    }

    val users: LiveData<PagedList<UserModel>> = Transformations.switchMap(userResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(userResult) { it ->
        it.networkErrors
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }


    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value
}