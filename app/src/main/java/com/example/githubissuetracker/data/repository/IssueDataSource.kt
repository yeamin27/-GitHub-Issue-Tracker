package com.example.githubissuetracker.data.repository

import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue


interface IssueDataSource {
    suspend fun getIssueList(user: String, repo: String): NetworkResult<List<Issue>>
}