package com.example.githubissuetracker.data.repository

import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.IssueListResponse
import com.example.githubissuetracker.data.model.SearchResult


interface IssueDataSource {
    suspend fun getIssueList(user: String, repo: String): NetworkResult<IssueListResponse>

    suspend fun navigateIssues(url: String): NetworkResult<IssueListResponse>

    suspend fun searchIssues(searchTerm: String, user: String, repo: String): NetworkResult<IssueListResponse>

    suspend fun navigateSearch(url: String): NetworkResult<IssueListResponse>
}