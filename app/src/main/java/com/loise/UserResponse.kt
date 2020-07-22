package com.loise

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("total_count") val total: Int = 0,
        @SerializedName("incomplete_results") val incompleteResults: Boolean = false,
        @SerializedName("items") val items: List<UserModel> = emptyList()
)