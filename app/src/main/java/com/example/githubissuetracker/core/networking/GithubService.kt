package com.example.githubissuetracker.core.networking

import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET(EndPoints.ISSUES)
    suspend fun getIssues(
        @Path("user") user: String,
        @Path("repo") repo: String,
        @Query("type") type: String = "issue",
        @Query("state") state: String = "all",
    ): Response<List<Issue>>

    @GET(EndPoints.SEARCH)
    suspend fun searchIssues(
        @Query("q", encoded = true) query: String,
    ): Response<SearchResult>
}