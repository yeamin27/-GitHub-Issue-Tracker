package com.example.githubissuetracker.data.model

import com.google.gson.annotations.SerializedName

data class IssueListResponse(
    @SerializedName("first_url")
    var firstUrl: String? = null,

    @SerializedName("previous_url")
    var previousUrl: String? = null,

    @SerializedName("next_url")
    var nextUrl: String? = null,

    @SerializedName("last_url")
    var lastUrl: String? = null,

    @SerializedName("total_count")
    var totalCount: Int? = null,

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,

    @SerializedName("issues")
    var issues: List<Issue>,
)
