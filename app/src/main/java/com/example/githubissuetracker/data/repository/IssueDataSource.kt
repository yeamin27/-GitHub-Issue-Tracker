package com.example.githubissuetracker.data.repository

import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.SearchResult


interface IssueDataSource {
    suspend fun getIssueList(user: String, repo: String): NetworkResult<List<Issue>>

    suspend fun searchIssues(searchTerm: String, user: String, repo: String): NetworkResult<SearchResult>
}