package com.example.githubissuetracker.core.networking

import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubService {
    @GET(EndPoints.ISSUES)
    suspend fun getIssues(
        @Path("user") user: String,
        @Path("repo") repo: String,
    ): Response<List<Issue>>

    @GET(EndPoints.SEARCH)
    suspend fun searchIssues(
        @Query("q", encoded = true) query: String,
    ): Response<SearchResult>

    @GET
    suspend fun navigateIssues(
        @Url url: String,
    ): Response<List<Issue>>

    @GET
    suspend fun navigateSearch(
        @Url url: String,
    ): Response<SearchResult>
}