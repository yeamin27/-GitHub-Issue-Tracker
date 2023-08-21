package com.example.githubissuetracker.data.repository

import com.example.githubissuetracker.core.networking.NetworkResult
import com.example.githubissuetracker.data.model.Issue
import com.example.githubissuetracker.data.model.IssueListResponse
import com.example.githubissuetracker.data.model.SearchResult


class IssueRepositoryImpl private constructor(
    private val remoteDataSource: IssueDataSource
) : IssueDataSource {

    companion object {
        private var INSTANCE: IssueRepositoryImpl? = null
        fun getInstance(
            remoteDataSource: IssueDataSource
        ): IssueRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = IssueRepositoryImpl(remoteDataSource)
            }
            return INSTANCE!!
        }
    }

    override suspend fun getIssueList(user: String, repo: String): NetworkResult<IssueListResponse> {
        return remoteDataSource.getIssueList(user, repo)
    }

    override suspend fun navigateIssues(url: String): NetworkResult<IssueListResponse> {
        return remoteDataSource.navigateIssues(url)
    }

    override suspend fun searchIssues(
        searchTerm: String,
        user: String,
        repo: String
    ): NetworkResult<IssueListResponse> {
        return remoteDataSource.searchIssues(searchTerm, user, repo)
    }

    override suspend fun navigateSearch(url: String): NetworkResult<IssueListResponse> {
        return remoteDataSource.navigateSearch(url)
    }
}