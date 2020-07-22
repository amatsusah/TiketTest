package com.loise

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class UserResult(
        val data: LiveData<PagedList<UserModel>>,
        val networkErrors: LiveData<String>
)