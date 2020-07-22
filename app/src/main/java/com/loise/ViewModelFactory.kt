package com.loise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val user: User) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelSearch::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelSearch(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}