package com.loise

import android.content.Context
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object Injection {

    fun provideUserDataSource(context: Context, callback : User.UserCallback): User {
        val database = UserDb.getInstance(context)
        return User(GithubService.create(), LocalCache(database.usersDao(), provideExecutor()), callback)
    }

    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context, callback : User.UserCallback): ViewModelFactory {
        return ViewModelFactory(provideUserDataSource(context, callback))
    }
}