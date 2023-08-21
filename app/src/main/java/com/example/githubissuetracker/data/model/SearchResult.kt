package com.example.githubissuetracker.data.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("total_count") var totalCoun: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: List<Issue>? = null,
)