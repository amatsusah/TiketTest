package com.loise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(posts: List<UserModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: UserModel)
    // Do a similar query as the search API:
    // Look for users that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM users WHERE (login LIKE :queryString) ORDER BY updateTime ASC")
    fun reposByName(queryString: String): androidx.paging.DataSource.Factory<Int, UserModel>
}