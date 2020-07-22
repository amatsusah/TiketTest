package com.loise

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [UserModel::class],
        version = 1,
        exportSchema = false
)
abstract class UserDb : RoomDatabase() {

    abstract fun usersDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDb? = null

        fun getInstance(context: Context): UserDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        UserDb::class.java, "Github.db")
                        .build()
    }
}