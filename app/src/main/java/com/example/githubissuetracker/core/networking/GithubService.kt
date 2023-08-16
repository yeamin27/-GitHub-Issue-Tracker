package com.example.githubissuetracker.core.networking

import com.example.githubissuetracker.data.model.Issue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET(EndPoints.ISSUES)
    suspend fun getIssues(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Response<List<Issue>>
}