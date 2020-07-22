package com.loise

import android.util.Log
import androidx.paging.DataSource
import java.util.concurrent.Executor

class LocalCache(
        private val userDao: UserDao,
        private val ioExecutor: Executor
) {

    /**
     * Insert a list of users in the database, on a background thread.
     */
    fun insert(user: UserModel, insertFinished: () -> Unit) {
        ioExecutor.execute {
            userDao.insert(user)
            insertFinished()
        }
    }

    fun insert(user: List<UserModel>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            userDao.insert(user)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<UserModel>> from the Dao, based on a user name.
     */
    fun usersByName(name: String): DataSource.Factory<Int, UserModel> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${name.replace(' ', '%')}%"
        return userDao.reposByName(query)
    }
}