package com.example.githubissuetracker.data.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("url") var url: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
    @SerializedName("diff_url") var diffUrl: String? = null,
    @SerializedName("patch_url") var patchUrl: String? = null,
    @SerializedName("merged_at") var mergedAt: String? = null
)